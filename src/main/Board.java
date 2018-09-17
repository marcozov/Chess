package main;

import java.util.Optional;

import gui.Main;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import pieces.Bishop;
import pieces.King;
import pieces.Knight;
import pieces.Pawn;
import pieces.Piece;
import pieces.Queen;
import pieces.Rook;

public class Board extends GridPane {
	protected Box[][] boxes;
	private boolean pieceSelected;
	Box currentBox;
	Color player;
	Player p1;
	Player p2;
	
	Player currentPlayer;
	Player otherPlayer;
	
	Text turn;
	
	Main main;
	
	public Board(Player p1, Player p2, Main main) {
		this(p1, p2);
		this.main = main;
	}
	
	public Board(Player p1, Player p2) {
		super();
				
		this.p1 = p1;
		this.p2 = p2;
		this.currentPlayer = this.p1;
		this.otherPlayer = this.p2;
		this.currentBox = null;
		this.boxes = new Box[8][8];
		this.player = Color.WHITE;
		
		boolean white = true;
		for (int col=0; col < 8; col++) {
			for (int row=0; row < 8; row++) {
				this.boxes[col][row] = new Box(col, row, white);
				this.add(this.boxes[col][row], col, 7-row);
				white = !white;
				
				final int x = col;
				final int y = row;
				this.boxes[col][row].setOnAction( e -> boxClickCallback(x, y) );
			}
			white = !white;
		}
		
		// filling row 0
		this.boxes[0][0].setPiece(new Rook(Color.WHITE, this));
		this.boxes[7][0].setPiece(new Rook(Color.WHITE, this));
		this.boxes[1][0].setPiece(new Knight(Color.WHITE, this));
		this.boxes[6][0].setPiece(new Knight(Color.WHITE, this));
		this.boxes[2][0].setPiece(new Bishop(Color.WHITE, this));
		this.boxes[5][0].setPiece(new Bishop(Color.WHITE, this));
		this.boxes[3][0].setPiece(new Queen(Color.WHITE, this));
		this.boxes[4][0].setPiece(new King(Color.WHITE, this));
		// filling row 1
		for (int col=0; col < 8; col++) {
			this.boxes[col][1].setPiece(new Pawn(Color.WHITE, this));
		}
		
		// filling row 7
		this.boxes[0][7].setPiece(new Rook(Color.BLACK, this));
		this.boxes[7][7].setPiece(new Rook(Color.BLACK, this));
		this.boxes[1][7].setPiece(new Knight(Color.BLACK, this));
		this.boxes[6][7].setPiece(new Knight(Color.BLACK, this));
		this.boxes[2][7].setPiece(new Bishop(Color.BLACK, this));
		this.boxes[5][7].setPiece(new Bishop(Color.BLACK, this));
		this.boxes[3][7].setPiece(new Queen(Color.BLACK, this));
		this.boxes[4][7].setPiece(new King(Color.BLACK, this));
		// filling row 6
		for (int col=0; col < 8; col++) {
			this.boxes[col][6].setPiece(new Pawn(Color.BLACK, this));
		}
		
		for (int col=0; col < 8; col++) {
			// white pieces
			for (int row=0; row < 2; row++) {
				this.p1.addPiece(this.boxes[col][row].getPiece());
			}
			
			// black pieces
			for (int row=6; row < 8; row++) {
				this.p2.addPiece(this.boxes[col][row].getPiece());
			}
		}
	}
	
	public Box[][] getBoxes() {
		return this.boxes;
	}
	
	public void setTurn(Text turn) {
		this.turn = turn;
	}
	
	public boolean isPieceSelected() {
		return this.pieceSelected;
	}
	
	
	public boolean isCheckMate(Player p) {
		King king = p.getKing();
				
		return !king.isSafe(this);
	}
	
	// put game logic here?
	public void boxClickCallback(int col, int row) {
		if (this.currentBox == null) {
			// piece still needs to be selected
			Piece currentPiece = this.getPiece(col, row);
			if (currentPiece != null && currentPiece.getColor() == this.currentPlayer.getColor()) {
				this.currentBox = this.getBox(col, row);
				this.currentBox.addSelection(this.currentPlayer.getColor());
				// put something in order to show that a box is selected
			}
		} else {
			// piece already selected
			Box newBox = this.getBox(col, row);
			Piece currentPiece = this.currentBox.getPiece();
			
			if (currentPiece.checkMoveValidity(new Position(col, row))) {
				Piece existingPiece = newBox.getPiece();
				// it must be a piece of the other player
				if (existingPiece != null && existingPiece.getColor() == this.otherPlayer.color) {
					this.otherPlayer.removePiece(existingPiece);
				}
				newBox.setPiece(currentPiece);
				this.currentBox.removePiece();
				
				King king = this.currentPlayer.getKing();
				if (!king.isMyKingSafe(king.getBox())) {
					// restore previous state
					if (existingPiece != null) {
						this.otherPlayer.addPiece(existingPiece);
						newBox.setPiece(existingPiece);
					} else {
						newBox.removePiece();
					}
					
					this.currentBox.setPiece(currentPiece);
					this.currentBox.removeSelection(this.currentPlayer.getColor());
					
					return;
				}				
				
				this.currentBox.removeSelection(this.currentPlayer.getColor());
				this.currentBox = null;
				
				if (this.currentPlayer.getColor() == Color.WHITE) {
					this.turn.setText("Player: BLACK");
					this.currentPlayer = this.p2;
					this.otherPlayer = this.p1;
				} else {
					this.turn.setText("Player: WHITE");
					this.currentPlayer = this.p1;
					this.otherPlayer = this.p2;
				}
				
				if (this.isCheckMate(this.currentPlayer)) {
					Color winner = this.otherPlayer.getColor();
					Alert alert = new Alert(AlertType.NONE);
					alert.setTitle("Chessmate");
					alert.setHeaderText("Chessmate! Player " + winner + " won!");
					alert.setContentText("Choose");

					ButtonType buttonTypeOne = new ButtonType("Restart Game");
					ButtonType buttonTypeTwo = new ButtonType("Quit");

					alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeTwo);

					Optional<ButtonType> result = alert.showAndWait();
					if (result.get() == buttonTypeOne) {
						try {
							this.main.restartCallback(new Stage());
						} catch (Exception e) {
							e.printStackTrace();
						}
					} else if (result.get() == buttonTypeTwo) {
						this.main.closeCallback();
					}
				}
			} else {
				this.currentBox.removeSelection(this.currentPlayer.getColor());
				this.currentBox = null;
			}
		}
	}
	
	public Box getBox(int col, int row) {
		if (col < 0 || row < 0 || col > 7 || row > 7) {
			return null;
		}
		
		return this.boxes[col][row];
	}
	
	public Piece getPiece(int col, int row) {
		return this.getBox(col, row).getPiece();
	}
	
	public void setPiece(int col, int row, Piece piece) {
		this.getBox(col, row).setPiece(piece);
	}
	
	public void removePiece(int col, int row) {
		this.getBox(col, row).removePiece();
	}
	
	@Override
	public String toString() {
		String result = "";
		for (int y=7; y >= 0; y--) {
			for (int x=0; x < 8; x++) {
				Piece piece = this.getPiece(x, y);
				if (piece != null) {
					result = result + String.format("%8s", piece.getClass().getSimpleName());
				} else {
					result = result + String.format("%8s", "-");
				}
			}
			result = result + "\n";
		}
		
		return result;
	}
	
	public Player getP1() {
		return this.p1;
	}
	
	public Player getP2() {
		return this.p2;
	}
}
