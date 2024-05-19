package evan.connect4.logic;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

import evan.connect4.controls.ControlHandler;
import evan.connect4.main.Game;
import evan.connect4.main.Settings;

public class GameController {

	private int[][] gameBoard;
	
	private Game game;
	private ControlHandler controls;
	private GameAI cpu;
	private Piece nextPlayerToMove;
	private int turnCount;
	private int maxTurns = Settings.BOARD_WIDTH * Settings.BOARD_HEIGHT;
	private Position[] winningPositions;
	private boolean gameIsOver;
	private Timer timer;
	
	public GameController(Game game) {
		this.game = game;
		
		gameBoard = new int[Settings.BOARD_WIDTH][Settings.BOARD_HEIGHT];
		
		controls = new ControlHandler(this);
		cpu = new GameAI(this, gameBoard);
		
		startNewGame();
	}
	
	//Player or CPU want to place a piece at the given row
	public void inputAtBoardPosition(int row) {
		
		if (gameIsOver) {
			return;
		}
		
		int placeAt = BoardUtility.getNextEmptyPieceInRow(gameBoard, row);
		if (placeAt != -1) {
			Position p = new Position(row, placeAt);
			BoardUtility.placePieceAtPosition(gameBoard, nextPlayerToMove, p);
			turnCount++;
			if (checkIfGameIsOver()) {
				endGame();
			}else {
				swapTurns();
			}
			game.repaint();
		}
	}
	
	public void playerAttemptPlaceAt(int row) {
		if (nextPlayerToMove == Piece.PLAYER) {
			inputAtBoardPosition(row);
		}
	}

	public void cpuAttemptPlaceAt(int row) {
		if (nextPlayerToMove == Piece.CPU) {
			inputAtBoardPosition(row);
		}
	}
	
	public void startNewGame() {
		gameIsOver = false;
		winningPositions = null;
		if (timer != null) {
			timer.stop();
			timer = null;
		}
		turnCount = 0;
		BoardUtility.setNewBoard(gameBoard);
		
		if (Utility.getRandom(0, 1) == 1) {
			nextPlayerToMove = Piece.CPU; //cpu goes first
			cpu.chooseNextMove();
		}else {
			nextPlayerToMove = Piece.PLAYER; //player goes first
		}
		
		
		game.repaint();
	}
	
	public void resetGamePressed() {
		startNewGame();
	}
	
	public ControlHandler getControls() {
		return controls;
	}

	public int[][] getBoard() {
		return gameBoard;
	}
	
	public Position[] getWinningPositions() {
		return winningPositions;
	}
	
	private void endGame() {
		if (gameIsOver == false) {
			gameIsOver = true;
			
			timer = new Timer(5000, new ActionListener(){    
				@Override
	            public void actionPerformed(ActionEvent e) {
					timer.stop();
					startNewGame();
	            }
	        });
			timer.start();
			
		}
		
	}
	
	private boolean checkIfGameIsOver() {
		if (turnCount >= maxTurns) {
			return true;
		}
		
		winningPositions = BoardUtility.checkBoardForWinner(gameBoard);
		
		if (winningPositions != null) {
			return true;
		}
		return false;
	}
	
	private void swapTurns() {
		if (nextPlayerToMove == Piece.PLAYER) {
			nextPlayerToMove = Piece.CPU;
			
			timer = new Timer(200, new ActionListener(){    
				@Override
	            public void actionPerformed(ActionEvent e) {
					timer.stop();
					cpu.chooseNextMove();
	            }
	        });
			timer.start();
			
			
		}else if (nextPlayerToMove == Piece.CPU) {
			nextPlayerToMove = Piece.PLAYER;
		}
	}
	
	
}




