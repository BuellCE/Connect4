package evan.connect4.logic;

import java.util.ArrayList;

public class GameAI {

	private GameController controller;
	private int[][] board;
	private int outcomeCount;
	
	public GameAI(GameController controller, int[][] board) {
		this.controller = controller;
		this.board = board;
	}

	public void chooseNextMove() {
		
		int[][] copy = BoardUtility.copyBoard(board);
		
		outcomeCount = 0;
		
		long time = System.nanoTime();
		
		Position move = findBestOutcome(copy, 6, true, Integer.MIN_VALUE, Integer.MAX_VALUE);
		
		System.out.println("Calculated " + outcomeCount + " outcomes in " + ((System.nanoTime() - time)/1000000f) + "ms");
		
		System.out.println("RESULT: " + move.y);
		
		int bestMove = move.x;
		
		controller.cpuAttemptPlaceAt(bestMove);
		
	}
	
	public Position findBestOutcome(int[][] board, int depth, boolean maximize, int alpha, int beta) {
		
		ArrayList<Integer> moves = BoardUtility.possibleBoardMoves(board);
		
		if (depth <= 0 || moves.isEmpty()) {
			outcomeCount++;
			return new Position(0, BoardUtility.getScoreFromBoard(board) + depth);
		}
		
		if (BoardUtility.checkBoardForWinner(board) != null) {
			outcomeCount++;
			int score = BoardUtility.getScoreFromBoard(board);
			return new Position(0, score + depth);
		}

		depth--;
		if (maximize) {
			int value = Integer.MIN_VALUE;
			int bestMove = 0;
			for (int x : moves) {
				
				int y = BoardUtility.getNextEmptyPieceInRow(board, x);
				int[][] copyBoard = BoardUtility.copyBoard(board);
				BoardUtility.placePieceAtPosition(copyBoard, Piece.CPU, new Position(x, y));
				
				int val = findBestOutcome(copyBoard, depth, false, alpha, beta).y;

				if (val >= value) {
					bestMove = x;
					value = val;
				}
				
				alpha = Math.max(alpha, val);
				if (alpha > beta) {
					break;
				}
				
			}
			return new Position(bestMove, value);
		}else {
			int value = Integer.MAX_VALUE;
			for (int x : moves) {
				
				int y = BoardUtility.getNextEmptyPieceInRow(board, x);
				int[][] copyBoard = BoardUtility.copyBoard(board);
				BoardUtility.placePieceAtPosition(copyBoard, Piece.PLAYER, new Position(x, y));
				
				int val = findBestOutcome(copyBoard, depth, true, alpha, beta).y;
				
				if (val <= value) {
					value = val;
				}
				
				beta = Math.min(beta, val);
				if (alpha > beta) {
					break;
				}
			}
			return new Position(0, value);
		}
	}
	
	
}




