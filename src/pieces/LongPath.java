package pieces;

import main.Board;
import main.Box;
import main.Color;
import main.Position;

public abstract class LongPath extends Piece {

	public LongPath(Color color, Board board) {
		super(color, board);
	}
	
	public boolean checkHorizontalVerticalLine(int currentRow, int newRow, int currentCol, int newCol, Board board) {
		return 	checkHorizontalLine(currentRow, newRow, currentCol, board) &&
				checkVerticalLine(currentCol, newCol, currentRow, board);
	}
	
	public boolean checkHorizontalLine(int currentCol, int newCol, int currentRow, Board board) {
		// need to move through the Xs (through the same row)
		while (currentCol != newCol) {
			if (currentCol < newCol) {
				currentCol = currentCol + 1;
			} else {
				currentCol = currentCol - 1;
			}
			
			Piece piece = board.getPiece(currentCol, currentRow);
			// handling the case where the current position has become the new position: in this case 
			// we can accept a Piece in there (with a different color.. but the color is checked before
			// this function is called)
			if (piece != null && currentCol != newCol) {
				return false;
			}
		}
		
		return true;
	}
	
	public boolean checkVerticalLine(int currentRow, int newRow, int currentCol, Board board) {
		while (currentRow != newRow) {			
			if (currentRow < newRow) {
				currentRow = currentRow + 1;
			} else {
				currentRow = currentRow - 1;
			}
			
			Piece piece = board.getPiece(currentCol, currentRow);
			// handling the case where the current position has become the new position: in this case 
			// we can accept a Piece in there (with a different color.. but the color is checked before
			// this function is called)
			if (piece != null && currentRow != newRow) {
				return false;
			}
		}
		
		return true;
	}
	
	public boolean checkDiagonalLine(int currentRow, int newRow, int currentCol, int newCol, Board board) {
		while (currentRow != newRow && currentCol != newCol) {
			if (currentRow < newRow) {
				currentRow = currentRow + 1;
			} else {
				currentRow = currentRow - 1;
			}
			
			if (currentCol < newCol) {
				currentCol = currentCol + 1;
			} else {
				currentCol = currentCol - 1;
			}
			
			Piece piece = board.getPiece(currentRow, currentCol);
			
			if (piece != null && (currentRow != newRow || currentCol != newCol)) {
				return false;
			}
		}
		
		return true;
	}
}
