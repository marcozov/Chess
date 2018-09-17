package pieces;
import main.Board;
import main.Box;
import main.Color;
import main.Position;

public class Pawn extends Piece {

	public Pawn(Color color, Board board) {
		super(color, board);
	}
	
	@Override
	public boolean isNewPositionValid(Box newBox) {
		Position position = this.getCurrentPosition();
		int currentCol = position.getCol();
		int currentRow = position.getRow();
		
		Position newPosition = newBox.getPosition();
		int newCol = newPosition.getCol();
		int newRow = newPosition.getRow();
		
		Piece pieceOnNewBox = newBox.getPiece();
		
		if (this.color == Color.WHITE) {
			// first move: can move of 2 positions
			if (currentRow == 1 && (newRow - currentRow != 1 && newRow - currentRow != 2)) {
				return false;
			}
			
			// other moves: can move of 1 position
			if (currentRow != 1 && newRow - currentRow != 1) {
				return false;
			}
		} else {
			// first move
			if (currentRow == 6 && (currentRow - newRow != 1 && currentRow - newRow != 2)) {
				return false;
			}
			
			// other moves
			if (currentRow != 6 && currentRow - newRow != 1) {
				return false;
			}
		}
		
		if (pieceOnNewBox == null) {
			return newCol == currentCol;
		} else {
			// if the Pawn is trying to kill another Piece, then it must be on the same diagonal
			return Math.abs(newCol - currentCol) == 1 && Math.abs(newRow - currentRow) == 1;
		}
	}

}
