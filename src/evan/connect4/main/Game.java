package evan.connect4.main;

import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;

import evan.connect4.graphics.GameVisual;
import evan.connect4.logic.GameController;

//singleton class to ensure only one instance of the game exists
@SuppressWarnings("serial")
public class Game extends JPanel{

	private static Game window;
	private GameController gameController;
	private GameVisual gameVisuals;
	private JFrame gameFrame;
	
	private Game(){
		gameFrame = new JFrame();
		gameController = new GameController(this);
		gameVisuals = new GameVisual();
		addControlListeners();
		initializeGameWindow();
	}
	
	public static Game getGameInstance() {
		if (window == null) {
			window = new Game();
		}
		return window;
	}
	
	private void initializeGameWindow() {
		gameFrame.setSize(Settings.WINDOW_SIZE_PER_SLOT * Settings.BOARD_WIDTH, Settings.WINDOW_SIZE_PER_SLOT * Settings.BOARD_HEIGHT);
		gameFrame.setTitle(Settings.TITLE);
		gameFrame.setDefaultCloseOperation(Settings.DEFAULT_CLOSE_OPERATION);
		gameFrame.setResizable(Settings.ALLOW_RESIZING);
		gameFrame.getContentPane().add(this);
	}
	
	private void addControlListeners() {
		gameFrame.addMouseListener(gameController.getControls().getMouseControls());
		gameFrame.addKeyListener(gameController.getControls().getKeyboardControls());
	}
	
	public GameController getGameController() {
		return gameController;
	}
	
	public void startGame() {
		gameFrame.setVisible(true);
	}
	
	public void exitGame() {
		gameFrame.dispose();
	}
	
	//called whenever graphics need to be refreshed
    @Override
    public void paintComponent(Graphics graphics) {
    	super.paintComponent(graphics);
    	gameVisuals.paintBackGround(graphics);
    	gameVisuals.paintGameBoard(graphics, gameController);
    	gameVisuals.paintWinningLine(graphics, gameController);
    }

	
}
