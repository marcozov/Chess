package pieces;
import main.Board;
import main.Box;
import main.Color;
import main.Position;

public class Knight extends Piece {

	public Knight(Color color, Board board) {
		super(color, board);
	}

	@Override
	public boolean isNewPositionValid(Box newBox) {
		Position position = this.getCurrentPosition();
		int currentCol = position.getCol();
		int currentRow = position.getRow();
		
		Position newPosition = newBox.getPosition();
		int newRow = newPosition.getCol();
		int newCol = newPosition.getRow();
		
		if ((Math.abs(newRow - currentCol) == 1 && Math.abs(newCol - currentRow) == 2) || 
			(Math.abs(newRow - currentCol) == 2 && Math.abs(newCol - currentRow) == 1) ) {
			return true;
		}
		
		return false;
	}

}
