package main;

public class EmptyBoard extends Board {
	
//	public EmptyBoard() {
	public EmptyBoard() {
		super(new Player(Color.WHITE), new Player(Color.BLACK));
		super.boxes = new Box[8][8];
		
		boolean white = true;
		
		// column i
		for (int i=0; i < 8; i++) {
			// row j
			for (int j=0; j < 8; j++) {
				super.boxes[i][j] = new Box(i, j, white);
				white = !white;
			}
		}
	}
}
