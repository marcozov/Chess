package main;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.control.Button;
import javafx.scene.effect.BlendMode;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import pieces.Piece;

public class Box extends Button {
	private Piece piece;
	private Position position;
	
	public Box(int col, int row, boolean white) {
		super();
		
		this.position = new Position(col, row);
		
		this.getStyleClass().add("chess-space");
		
		if(white) {
			this.getStyleClass().add("chess-space-light");
		} else {
			this.getStyleClass().add("chess-space-dark");
		}
	}
	
	public ArrayList<Box> getSurroundingBoxes(Board board) {
		ArrayList<Box> boxes = new ArrayList<Box>();
		ArrayList<Box> toReturn = new ArrayList<Box>();
		
		Position boxPosition = this.getPosition();
		boxes.add(board.getBox(boxPosition.getCol(), boxPosition.getRow()+1));
		boxes.add(board.getBox(boxPosition.getCol(), boxPosition.getRow()-1));
		boxes.add(board.getBox(boxPosition.getCol()+1, boxPosition.getRow()));
		boxes.add(board.getBox(boxPosition.getCol()-1, boxPosition.getRow()));
		
		boxes.add(board.getBox(boxPosition.getCol()+1, boxPosition.getRow()+1));
		boxes.add(board.getBox(boxPosition.getCol()-1, boxPosition.getRow()+1));
		boxes.add(board.getBox(boxPosition.getCol()+1, boxPosition.getRow()-1));
		boxes.add(board.getBox(boxPosition.getCol()-1, boxPosition.getRow()-1));
		
		for (Box box : boxes) {
			if (box != null) {
				toReturn.add(box);
			}
		}
		
		return toReturn;
	}
	
	public Piece getPiece() {
		return this.piece;
	}
	
	public Position getPosition() {
		return this.position;
	}
	
	public void setPiece(Piece piece) {
		this.piece = piece;
		this.piece.setBox(this);
		this.setGraphic( new ImageView(piece.getImage()));
	}
	
	public void removePiece() {
//		this.piece.setBox(null);
		this.piece = null;
		this.setGraphic(new ImageView());
	}
	
	public void addSelection(Color color) {
		if (color == Color.WHITE) {
			this.getStyleClass().add("chess-space-white-selected");
		} else {
			this.getStyleClass().add("chess-space-black-selected");
		}
	}
	
	public void removeSelection(Color color) {
		if (color == Color.WHITE) {
			this.getStyleClass().remove("chess-space-white-selected");
		} else {
			this.getStyleClass().remove("chess-space-black-selected");
		}
	}
}
