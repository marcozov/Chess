package main;

public class Position {
	private int col;
	private int row;
	
	public Position(int col, int row) {
		this.col = col;
		this.row = row;
	}

	// column of the board
	public int getCol() {
		return this.col;
	}
	
	// row of the board
	public int getRow() {
		return this.row;
	}
	
	@Override
	public boolean equals(Object o) {
		Position pos2 = (Position)o;
		return this.getCol() == pos2.getCol() && this.getRow() == pos2.getRow();
	}
	
	@Override
	public String toString() {
		return "********************\nx: " + this.getCol() + "\n********************\ny: " + this.getRow() + "\n********************\n";
	}
}
