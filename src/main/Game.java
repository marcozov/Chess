package main;

import gui.Main;
import javafx.application.Application;

public class Game {
	protected Board board;
	protected Player p1;
	protected Player p2;
	protected boolean finished;
//	Table gui;
	
	public Game() {
		this.p1 = new Player(Color.WHITE);
		this.p2 = new Player(Color.BLACK);
		this.board = new Board(p1, p2);
//		this.board = new Board();
		this.finished = false;
//		this.gui = new Table();
	}
	
	public Game(Player p1, Player p2) {
		this.p1 = p1;
		this.p2 = p2;
		this.board = new Board(p1, p2);
		this.finished = false;
	}
	
	public boolean isFinished() {
		return this.finished;
	}
	
	public Board getBoard() {
		return this.board;
	}
	
	public Player getP1() {
		return this.p1;
	}
	
	public Player getP2() {
		return this.p2;
	}
	
	public void start() throws Exception {
		Application.launch(Main.class);
	}
}
