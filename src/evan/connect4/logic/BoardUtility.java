package evan.connect4.logic;

import java.util.ArrayList;

import evan.connect4.main.Settings;

//Utilities that focus on getting or adding data to the board
public interface BoardUtility {

	public static void placePieceAtPosition(int[][] board, Piece piece, Position p) {
		board[p.x][p.y] = piece.id();
	}
	
	public static int getNextEmptyPieceInRow(int[][] board, int row) {
		for (int i = 0; i < Settings.BOARD_HEIGHT; i++) {
			if (board[row][i] == Piece.EMPTY.id()) {
				return i;
			}
		}
		return -1;
	}
	
	public static void setNewBoard(int[][] board) {
		int id = Piece.EMPTY.id();
		
		for (int x = 0; x < Settings.BOARD_WIDTH; x++) {
			for (int y = 0; y < Settings.BOARD_HEIGHT; y++) {
				board[x][y] = id;
			}
		}
	}
	
	public static ArrayList<Integer> possibleBoardMoves(int[][] board) {
		ArrayList<Integer> moves = new ArrayList<Integer>();
		
		for (int i = 0; i < Settings.BOARD_WIDTH; i++) {
			if (getNextEmptyPieceInRow(board, i) != -1) {
				moves.add(i);
			}
		}
		
		return moves;
	}
	
	public static int[][] copyBoard(int[][] initial) {
		int[][] boardCopy = new int[Settings.BOARD_WIDTH][Settings.BOARD_HEIGHT];
		
		for (int i = 0; i < initial.length; i++) {
			for (int x = 0; x < initial[i].length; x++) {
				boardCopy[i][x] = initial[i][x];
			}
		}
		
		return boardCopy;
	}
	
	//checks the board for a win
	//returns the four positions that caused the win if they exist
	public static Position[] checkBoardForWinner(int[][] board) {
		
	    final int emptyPieceId = Piece.EMPTY.id();
	    
	    for (int x = 0; x < Settings.BOARD_WIDTH; x++) {
	        for (int y = 0; y < Settings.BOARD_HEIGHT; y++) {
	            int player = board[x][y];
	            if (player == emptyPieceId) {
	                continue;
	            }
	            
	            if (x + 3 < Settings.BOARD_WIDTH && player == board[x+1][y] && player == board[x+2][y] && x + 3 < Settings.BOARD_WIDTH && player == board[x+3][y]) {
	            	return new Position[]{
	            		new Position(x,y),
	            		new Position(x+1,y),
	            		new Position(x+2,y),
	            		new Position(x+3,y),
	            	}; //4 in a row horizontally
	            }
	            if (y + 3 < Settings.BOARD_HEIGHT) {
	            	if (player == board[x][y+1] && player == board[x][y+2] && player == board[x][y+3]) {
	            		return new Position[]{
	            			new Position(x,y),
	            			new Position(x,y+1),
	            			new Position(x,y+2),
	            			new Position(x,y+3),
	            		}; //4 in a row horizontally //4 in a row vertically
	            	}
	                if (x + 3 < Settings.BOARD_WIDTH && player == board[x+1][y+1] && player == board[x+2][y+2] && player == board[x+3][y+3]) {
		            	return new Position[]{
			            	new Position(x,y),
			            	new Position(x+1,y+1),
			            	new Position(x+2,y+2),
			            	new Position(x+3,y+3),
			            }; //4 in a row horizontally //4 in a row up-right diagonally
	                }
	                if (x - 3 >= 0 && player == board[x-1][y+1] && player == board[x-2][y+2] && player == board[x-3][y+3]) {
		            	return new Position[]{
			            	new Position(x,y),
			            	new Position(x-1,y+1),
			            	new Position(x-2,y+2),
			            	new Position(x-3,y+3),
			            }; //4 in a row horizontally //4 in a row up-left diagonally
	                }
	            }
	        }
	    }

		return null;
	}
	
	public static int getScoreFromBoard(int[][] board) {
		
		int score = 0;
		
	    final int emptyPieceId = Piece.EMPTY.id();
	    
	    for (int x = 0; x < Settings.BOARD_WIDTH; x++) {
	        for (int y = 0; y < Settings.BOARD_HEIGHT; y++) {
	            int player = board[x][y];
	            if (player == emptyPieceId) {
	                continue;
	            }
	            score += addScore(Settings.X_POSITIONAL_SCORES[x], player); 
	            score += addScore(Settings.Y_POSITIONAL_SCORES[y], player); 
	            
	            if (x + 1 < Settings.BOARD_WIDTH && player == board[x+1][y]) {
	            	if (x + 2 < Settings.BOARD_WIDTH && player == board[x+2][y]) {
	            		if (x + 3 < Settings.BOARD_WIDTH && player == board[x+3][y]) {
	            			score += addScore(10000, player); //4 in a row horizontally
	            		}
	            		score += addScore(20, player); //3 in a row horizontally
	            		if (y > 1 && y + 2 < Settings.BOARD_HEIGHT && player == board[x+1][y+1]) {
	            			if (player == board[x+2][y+2]) {
	            				score += addScore(500, player); //Player makes a "7"
	            			}
	            			if (player == board[x][y+2]) {
	            				score += addScore(500, player); //Player makes a "7"
	            			}
	            		}
	            	}
	            	score += addScore(2, player); //2 in a row horizontally
	            }
	            if (y + 1 < Settings.BOARD_HEIGHT) {
	                if (player == board[x][y+1]) {
	                	if (y + 2 < Settings.BOARD_HEIGHT && player == board[x][y+2]) {
	                		if (y + 3 < Settings.BOARD_HEIGHT && player == board[x][y+3]) {
	                			score += addScore(10000, player); //4 in a row vertically
	                		}
	                		score += addScore(2, player); //3 in a row vertically
	                	}
	                	score += addScore(1, player); //2 in a row vertically
	                }
	                if (x + 1 < Settings.BOARD_WIDTH && player == board[x+1][y+1]) {
	                	if (x + 2 < Settings.BOARD_WIDTH && y + 2 < Settings.BOARD_HEIGHT && player == board[x+2][y+2]) {
	                		if (x + 3 < Settings.BOARD_WIDTH && y + 3 < Settings.BOARD_HEIGHT && player == board[x+3][y+3]) {
	                			score += addScore(10000, player); //4 in a row up-right diagonally
	                		}
	                		score += addScore(20, player); //3 in a row up-right diagonally
	                		if (x - 2 > 0 && player == board[x-1][y+2] && player == board[x-2][y+2]) {
	                			score += addScore(500, player); //Player makes a "7"
	                		}
	                	}
	                	score += addScore(5, player); //2 in a row up-right diagonally
	                }
	                if (x - 1 >= 0 && player == board[x-1][y+1]) {
	                	if (x - 2 >= 0 && y + 2 < Settings.BOARD_HEIGHT && player == board[x-2][y+2]) {
	                		if (x - 3 >= 0 && y + 3 < Settings.BOARD_HEIGHT && player == board[x-3][y+3]) {
	                			score += addScore(10000, player); //4 in a row up-left diagonally
	                		}
	                		score += addScore(20, player); //3 in a row up-left diagonally
	                		if (x + 2 < Settings.BOARD_WIDTH && player == board[x+1][y+2] && player == board[x+2][y+2]) {
	                			score += addScore(500, player); //Player makes a "7"
	                		}
	                	}
	                	score += addScore(5, player); //2 in a row up-left diagonally
	                }
	            }
	        }
	    }
		return score;
	}
	
	
	private static int addScore(int amount, int playerId) {
		if (playerId == Piece.PLAYER.id()) {
			return (int) (-0.9f * (float) amount);
		}else {
			return amount;
		}
	}
	
	
}
