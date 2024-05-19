package evan.connect4.controls;

import evan.connect4.logic.GameController;
import evan.connect4.main.Settings;

public class ControlHandler {

	private MouseControls mouseControls;
	private KeyboardControls keyboardControls;
	private GameController controller;
	
	public ControlHandler(GameController controller) {
		this.controller = controller;
		this.mouseControls = new MouseControls(this);
		this.keyboardControls = new KeyboardControls(this);
	}
	
	//The user used the mouse or keyboard to click the board at the row on a given position
	private void inputAtBoardPosition(int row) {
		controller.playerAttemptPlaceAt(row);
	}
	
	private void resetGameButtonPressed() {
		controller.resetGamePressed();
	}
	
	public void playerPressedKeyboard(char key) {
		
		int rowPressed = 0;
		
		if (Character.isDigit(key)) {
			rowPressed = Integer.parseInt(String.valueOf(key)) - 1;
			
			if (rowPressed >= 0 && rowPressed < Settings.BOARD_WIDTH) {
				inputAtBoardPosition(rowPressed);
			}
			
		}else if (key == Settings.RESET_GAME_CONTROL) {
			System.out.println("RESET");
			resetGameButtonPressed();
		}
	}
	
	public void playerPressedMouse(int xPosition, int yPosition) {
		
		//determine which row the player clicked
		int rowClicked = xPosition / Settings.WINDOW_SIZE_PER_SLOT;
		inputAtBoardPosition(rowClicked);
	}
	
	
	public MouseControls getMouseControls() {
		return mouseControls;
	}
	
	public KeyboardControls getKeyboardControls() {
		return keyboardControls;
	}
	
}
