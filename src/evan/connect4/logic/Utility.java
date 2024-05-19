package evan.connect4.logic;

import java.util.Random;

public interface Utility {

	public static int getRandom(int minimum, int maximum) {
		Random ran = new Random();
		return ran.nextInt(maximum + 1 - minimum) + minimum;
	}
	
	
}
