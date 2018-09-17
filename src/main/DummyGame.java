package main;

public class DummyGame extends Game {
	public DummyGame() {
		super.board = new EmptyBoard();
		super.p1 = new Player(Color.WHITE);
		super.p2 = new Player(Color.BLACK);
		super.finished = false;
	}
}
