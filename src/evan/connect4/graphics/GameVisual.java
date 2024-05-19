package evan.connect4.graphics;

import java.awt.Graphics;

import evan.connect4.logic.GameController;
import evan.connect4.logic.Piece;
import evan.connect4.logic.Position;
import evan.connect4.main.Settings;

public class GameVisual {

	private int circleSize = 0;
	private int windowHeight = Settings.BOARD_HEIGHT * Settings.WINDOW_SIZE_PER_SLOT;
	private final int slotSpacing = 10;
	
	public GameVisual() {
		circleSize = (int) (Settings.WINDOW_SIZE_PER_SLOT / 1.2f);
	}
	
	public void paintBackGround(Graphics graphics) {
		graphics.setColor(Settings.BACKGROUND_COLOR);
		graphics.fillRect(0, 0, Settings.WINDOW_SIZE_PER_SLOT * Settings.BOARD_WIDTH, Settings.WINDOW_SIZE_PER_SLOT * Settings.BOARD_HEIGHT);
	}

	public void paintGameBoard(Graphics graphics, GameController controller) {
		for (int x = 0; x < Settings.BOARD_WIDTH; x++) {
			for (int y = 0; y < Settings.BOARD_HEIGHT; y++) {
				int val = controller.getBoard()[x][y];
				
				Piece p = Piece.getPieceFromId(val);
				
				graphics.setColor(p.getColor());
				
				int startX = x * Settings.WINDOW_SIZE_PER_SLOT;
				
				//y pieces need to be drawn from bottom to top (reversed)
				int startY = y * Settings.WINDOW_SIZE_PER_SLOT + circleSize;
				startY = windowHeight - startY + (y - Settings.BOARD_HEIGHT) * slotSpacing;
				
				graphics.fillOval(startX, startY, circleSize, circleSize);
			}
		}
	}
	
	public void paintWinningLine(Graphics graphics, GameController controller) {
		Position[] winningPositions = controller.getWinningPositions();
		if (winningPositions != null) {
			graphics.setColor(Settings.WINNING_COLOR);
			
			for (int i = 0; i < winningPositions.length; i++) {
				int startX = winningPositions[i].x * Settings.WINDOW_SIZE_PER_SLOT;
				
				//y pieces need to be drawn from bottom to top (reversed)
				int startY = winningPositions[i].y * Settings.WINDOW_SIZE_PER_SLOT + circleSize;
				startY = windowHeight - startY + (winningPositions[i].y - Settings.BOARD_HEIGHT) * slotSpacing;
				
				graphics.fillOval(startX, startY, circleSize, circleSize);
			}
		}
	}
	
	
	
}
