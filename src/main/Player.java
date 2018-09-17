package main;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import pieces.King;
import pieces.Piece;

public class Player {
	List<Piece> pieces;
	List<Piece> removedPieces;
	Color color;
	Scanner reader;
	
	public Player(Color color) {
		this.pieces = new ArrayList<Piece>();
		this.removedPieces = new ArrayList<Piece>();
		this.color = color;
		
		// remember to close the reader when the match finishes .. ?
		this.reader = new Scanner(System.in);
	}
	
	public Color getColor() {
		return this.color;
	}
	
	public List<Piece> getPieces() {
		return this.pieces;
	}
	
	public void addPiece(Piece piece) {
		this.pieces.add(piece);
	}
	
	public void removePiece(Piece piece) {
		this.pieces.remove(piece);
		this.removedPieces.add(piece);
	}
	
	public void recoverPiece(Piece piece) {
		this.removedPieces.remove(piece);
		this.pieces.add(piece);
	}

	public King getKing() {
		for (Piece p : this.getPieces()) {
			if (p instanceof King) {
				return (King)p;
			}
		}
		return null;
	}
	
	public String showPieces() {
		String res = "player " + this.getColor() + ", number: " + this.getPieces().size() + ", pieces: ";
		for (Piece p : this.getPieces()) {
			res = res + p.whoami() + ", ";
		}
		
		return res;
	}
}
