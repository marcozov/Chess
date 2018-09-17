package pieces;
import main.Board;
import main.Box;
import main.Color;
import main.Position;

public class Bishop extends LongPath {

	public Bishop(Color color, Board board) {
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
		
		if (Math.abs(newCol - currentCol) == Math.abs(newRow - currentRow)) {
			return super.checkDiagonalLine(currentCol, newCol, currentRow, newRow, board);
		}
		
		return false;
	}

}
