/*
 * The game of connect 4 made from scratch as a programming challenge in 8 hours using java.
 * The AI utilizes a min-max algorithm to predict board states 6 turns in the future.
 * Evaluating board states was tricky since I am not familiar with how connect 4 should best be played.
 * The code utilizes enumerations, simple design patterns such as singleton, and the java swing library for graphics.
 */


package evan.connect4.main;

public class Application{
	
	public static void main(String[] args) {
		Game connect4 = Game.getGameInstance();
		connect4.startGame();
	}
	
	
}
