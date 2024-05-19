package evan.connect4.main;

import java.awt.Color;

import javax.swing.JFrame;

public interface Settings {

	//Window Settings
	public static final String TITLE = "Connect 4 - By Evan Buell";
	public static final int WINDOW_SIZE_PER_SLOT = 120;
	public static final int DEFAULT_CLOSE_OPERATION = JFrame.EXIT_ON_CLOSE;
	public static final boolean ALLOW_RESIZING = false;
	
	//Game Settings
	public static final int BOARD_HEIGHT = 7;
	public static final int BOARD_WIDTH = 9;
	public static final int[] X_POSITIONAL_SCORES = {0,1,3,20,30,20,3,1,0};
	public static final int[] Y_POSITIONAL_SCORES = {1,2,3,5,3,1,0};
	public static final char RESET_GAME_CONTROL = ' ';
	public static final Color BACKGROUND_COLOR = Color.BLACK;
	public static final Color WINNING_COLOR = new Color(255,0,255); 
	
}
