package pieces;
import java.io.File;
import java.util.Arrays;

import javafx.scene.image.Image;
import main.Board;
import main.Box;
import main.Color;
import main.Player;
import main.Position;

public abstract class Piece {
	Color color;
	Box box;
	Board board;
	private Image image;
	
	public Piece(Color color, Board board) {
		this.color = color;
		this.board = board;
		String path = "src/gui/pieces/" + this.getColor() + "_" + this.whoami() + ".png";
		this.image = new Image(new File(path).toURI().toString());
	}
	
	public Image getImage() {
		return this.image;
	}
	
	public void setBox(Box box) {
		this.box = box;
	}
	
	public Box getBox() {
		return this.box;
	}
	
	public Position getCurrentPosition() {
		return this.box.getPosition();
	}
	
	public abstract boolean isNewPositionValid(Box newBox);
	
	public boolean checkMoveValidity(Position newPosition) {
		// checking that the new position is inside the board
		Box newBox = board.getBox(newPosition.getCol(), newPosition.getRow());
		if (newBox == null) {
			return false;
		}
		
		// checking that the new position is empty or it contains a Piece of the enemy
		Piece piece = newBox.getPiece();
		if (piece != null && piece.getColor() == this.getColor() ) {
			return false;
		}
		
		// checking that the new position is different from the current one
		if (this.getCurrentPosition().equals(newPosition)) {
			return false;
		}
		
		// checking that the new position can be reached from this piece (this depends on the kind of Piece)
		return this.isNewPositionValid(newBox);
	}
	
	public Color getColor() {
		return this.color;
	}

//	 to call after validation has been performed
	public void move(Position newPosition) {
		Position currentPosition = this.getCurrentPosition();
		this.board.removePiece(currentPosition.getCol(), currentPosition.getRow());
		this.board.setPiece(newPosition.getCol(), newPosition.getRow(), this);
	}
	
	public String whoami() {
		return this.getClass().getSimpleName();
	}
	
	@Override
	public String toString() {
		return this.whoami() + "_" + this.getColor() + ". Position: " + this.getCurrentPosition();
	}
}
