package evan.connect4.logic;

import java.awt.Color;

//The playing pieces of the game
public enum Piece {

	EMPTY(0, Color.WHITE),
	PLAYER(1, Color.RED),
	CPU(2, Color.YELLOW);
	
	private int id;
	private final Color color;
	
	private Piece(int id, Color color) {
		this.id = id;
		this.color = color;
	}
	
	public Color getColor() {
		return color;
	}
	
	public int id() {
		return id;
	}

	public static Piece getPieceFromId(int id) {
		for (Piece p : values()) {
			if (p.id() == id) {
				return p;
			}
		}
		return null;
	}
	
}
