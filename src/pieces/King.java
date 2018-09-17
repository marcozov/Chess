package pieces;
import java.util.ArrayList;

import main.Board;
import main.Box;
import main.Color;
import main.Player;
import main.Position;

public class King extends Piece {
	Player opponent;

	public King(Color color, Board board) {
		super(color, board);
		this.opponent = null;
	}
	
	public Player getOpponent() {
		if (this.opponent != null) return this.opponent;
		
		if (this.getColor() == Color.WHITE) {
			this.opponent = board.getP2();
		} else {
			this.opponent = board.getP1();
		}
		
		return this.opponent;
	}
	
	@Override
	public boolean isNewPositionValid(Box newBox) {
		Position position = this.getCurrentPosition();
		int currentCol = position.getCol();
		int currentRow = position.getRow();
		
		Position newPosition = newBox.getPosition();
		int newRow = newPosition.getCol();
		int newCol = newPosition.getRow();
		
		return Math.abs(newRow - currentCol) <= 1 && Math.abs(newCol - currentRow) <= 1;
	}
	
	public boolean isSafe(Board board) {
		Position currentPosition = this.getCurrentPosition();
		
		Box box = board.getBox(currentPosition.getCol(), currentPosition.getRow());
		
		if (this.isMyKingSafeNotMoved(box)) {
			return true;
		}
		
		ArrayList<Box> toExamine = box.getSurroundingBoxes(board);

		for (Box currentBox : toExamine) {
			if ((currentBox.getPiece() == null || currentBox.getPiece().getColor() != this.getColor()) && this.isSafe(currentBox)) {
				return true;
			}
		}
		
		return false;
	}
	
	public boolean isSafe(Box newBox) {		
		Player opponent = this.getOpponent();
		
		Piece pieceInNewBox = newBox.getPiece();
				
		Box currentBox = this.getBox();
		currentBox.removePiece();
		newBox.setPiece(this);
		
		if (pieceInNewBox != null) {
			opponent.removePiece(pieceInNewBox);
		}
		
		boolean toReturn = this.isMyKingSafe(newBox);
		currentBox.setPiece(this);
		newBox.removePiece();
		
		if (pieceInNewBox != null) {		
			newBox.setPiece(pieceInNewBox);	
			opponent.addPiece(pieceInNewBox);
		}
		
		return toReturn;
	}
	
	public boolean isMyKingSafe(Box newBox) {	
		Player opponent = this.getOpponent();
		
		for (Piece piece : opponent.getPieces()) {
			if (piece.isNewPositionValid(newBox)) {
				return false;
			}
		}
		
		return true;
	}
	
	public boolean isMyKingSafeNotMoved(Box newBox) {
		Player opponent = this.getOpponent();
		
		for (Piece piece : opponent.getPieces()) {
			if (piece.isNewPositionValid(newBox)) {
								
				if (this.canAnyoneSaveMe(piece)) {
					return true;
				}
				
				return false;
			}
		}
		
		return true;
	}
	
	private boolean canAnyoneSaveMe(Piece dangerousPiece) {
		Player currentPlayer;
		if (this.getColor() == Color.WHITE) {
			currentPlayer = board.getP1();
		} else {
			currentPlayer = board.getP2();
		}
		
		// scan all 64 positions
		// for each reachable (EMPTY) position, try to put the piece in there
		// and see whether the opponent is "blocked" (i.e. it cannot reach the king anymore)
		for (int col=0; col < 8; col++) {
			for (int row=0; row < 8; row++) {
				Box currentBox = this.board.getBox(col, row);
				if (currentBox.getPiece() == null || currentBox.getPiece() == dangerousPiece ) {
					
					for (Piece piece : currentPlayer.getPieces()) {
						if (piece == this) continue;
						
						// for each reachable position for a potential savior, check whether
						// putting it in that position can save the king
						if (piece.isNewPositionValid(currentBox)) {
							Piece oldPiece = currentBox.getPiece();
							if (oldPiece != null) {
								// it means that there is a piece that can eliminate the dangerous piece!
								return true;
							}
							
							// box of the examined piece (a friend piece)
							Box pieceBox = piece.getBox();
							pieceBox.removePiece();
							currentBox.setPiece(piece);
							
							boolean safe = !dangerousPiece.isNewPositionValid(this.getBox());
							
							pieceBox.setPiece(piece);
							currentBox.removePiece();

							if (safe) {
								return true;
							}
						}
					}
				}
			}
		}
		
		return false;
	}

}
