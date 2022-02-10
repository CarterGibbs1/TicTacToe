import java.util.Scanner;

/**
 * Simple Tic-Tac-Toe game to learn about AI
 * 
 * @author carter
 *
 */
public class TicTacToe {
	
	private static char[] board; // array that represents the tictactoe board
	private static char player2, player1; // chars that represent player character
	private static boolean win, aiWin; // booleans that determine if either side has won
	private static int move, count; // int that represents move and ai move, also a count variable
	private static Scanner in; // scanner for reading in console input
	
	private static int p1Counter, p2Counter, drawCounter;
	
	private static final int NUM_OF_MOVES = 9; // constant to hold the size of array
	
	/**
	 * main method that runs the game
	 * @param args - array of Strings
	 */
	public static void main(String[] args) {
		setUpDebug();
		for (int games = 0; games < 100; games++) {
			setUpGame();
//			debugBoard();
			printBoard();
			playGame();
			displayResults();
		}
		debugResults();
	}
	
	private static void setUpDebug() {
		p1Counter = 0;
		p2Counter = 0;
		drawCounter = 0;
		
	}

	/**
	 * this method will display the results of the game
	 */
	private static void displayResults() {
		if (win) {
			System.out.println("Player 1 won");
			p1Counter++;
		}
		else if (aiWin) {
			System.out.println("Player 2 won");
			p2Counter++;
		}
		else {
			System.out.println("It's a draw");
			drawCounter++;
		}
	}

	/**
	 * initializes board
	 */
	public static void setUpGame() {
		board = new char[NUM_OF_MOVES];
		win = false;
		aiWin = false;
		count = 0;
		player1 = 'X';
		player2 = 'O';
	}
	
	/**
	 * displays board
	 */
	public static void printBoard() {
		System.out.println(" " + board[0] + " | " + board[1] + " | " + board[2]);
		System.out.println("-----------");
		System.out.println(" " + board[3] + " | " + board[4] + " | " + board[5]);
		System.out.println("-----------");
		System.out.println(" " + board[6] + " | " + board[7] + " | " + board[8]);
		System.out.println();
	}
	
	public static boolean hasEmptySpaces(char[] gameBoard) {
		for (int i = 0; i < NUM_OF_MOVES; i++) {
			if (gameBoard[i] == '\u0000') {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * method to determine the AI's next move
	 */
	public static int aiMove(char[] gameBoard, boolean first) {
		Integer bestMove = 0;
		if (first) {
			Integer maxScore = Integer.MIN_VALUE;
			for (int i = 0; i < NUM_OF_MOVES; i++) {
				// if spot is available
				if (board[i] == '\u0000') {
					board[i] = player1;
					int score = minimax(board, 0, false);
					board[i] = '\u0000';
					if (maxScore < score) {
						bestMove = i;
						maxScore = score;
					}
				}	
			}
		} else {
			Integer minScore = Integer.MAX_VALUE;
			for (int i = 0; i < NUM_OF_MOVES; i++) {
				// if spot is available
				if (board[i] == '\u0000') {
					board[i] = player2;
					int score = minimax(board, 0, true);
					board[i] = '\u0000';
					if (minScore > score) {
						bestMove = i;
						minScore = score;
					}
				}	
			}
		}
		return bestMove;
	}
	
	/**
	 * returns score of a played out game using recursion
	 * @param c - game board
	 * @param depth - depth of the minimax tree
	 * @param maxPlayer - boolean that represents if it is player 1 or 2
	 * @return - int of score of index
	 */
	public static int minimax(char[] c, int depth, boolean maxPlayer) {
		Integer checkIfWon = whoWon(c);
		if (checkIfWon != null) {
			return checkIfWon;
		}
		if (maxPlayer) {
			Integer maxScore = Integer.MIN_VALUE;
			for (int i = 0; i < NUM_OF_MOVES; i++) {
				// if spot is available
				if (board[i] == '\u0000') {
					board[i] = player1;
					int score = minimax(board, depth + 1, false);
					board[i] = '\u0000';
					maxScore = Math.max(maxScore, score);
				}
			}
			return maxScore;
		} else {
			Integer minScore = Integer.MAX_VALUE;
			for (int i = 0; i < NUM_OF_MOVES; i++) {
				// if spot is available
				if (board[i] == '\u0000') {
					board[i] = player2;
					int score = minimax(board, depth + 1, true);
					board[i] = '\u0000';
					minScore = Math.min(minScore, score);
				}	
			}
			return minScore;
		}
	}
	
	/**
	 * will determine the AI's move
	 * @return the position the AI plays
	 */
	public static int randomMove() {
		int number = getRandom(9);
		// if the AI chooses an index that has not already been used
		if (board[number] == '\u0000') {
			return number;
		}
		return randomMove();
	}

	/**
	 * get random number in between 0 (inclusive) and max (exclusive).
	 * @param max
	 * @return int - random number between 0 - max
	 */
	public static int getRandom(int max) {
		return (int) (Math.random() * max);
	}
	
	/**
	 * Method to correspond to the game playing until someone wins
	 */
	private static void playGame() {
		try {
			in = new Scanner(System.in);
			while(true) {
/*
				// player one input
				if (count == 9) {
					return;
				}
				System.out.println("Please make a move: ");
				move = playerMove();
				board[move] = player1;
				count++;
				System.out.println("Player one moves");
				printBoard();
				if (win(board, player1)) {
					win = true;
					return;
				} */
				// player one
				if (count == 0) {
					move = randomMove();
					board[move] = player1;
					count++;
				} else {
					move = aiMove(board, true);
					board[move] = player1;
					count++;
					System.out.println("Player two moves");
					printBoard();
					if (win(board, player1)) {
						win = true;
						return;
					}
				}
				// player two input
				if (count == 9) {
					return;
				}
				move = aiMove(board, false);
				board[move] = player2;
				count++;
				System.out.println("Player two moves");
				printBoard();
				if (win(board, player2)) {
					aiWin = true;
					return;
				}
			}
		} finally {
			in.close();
		}
	}
	
	public static int playerMove() {
		int index = in.nextInt() - 1;
		if (board[index] != '\u0000') {
			System.out.println("Please make a valid move: ");
			return playerMove();
		}
		return index;
	}
	
	public static void debugBoard() {
		board[0] = 'O';
		board[1] = 'X';
		board[2] = '\u0000';
		board[3] = 'X';
		board[4] = 'X';
		board[5] = 'O';
		board[6] = 'O';
		board[7] = '\u0000';
		board[8] = 'X';
		
		count = 7;
	}
	
	public static Integer whoWon(char c[]) {
		if (win(c, player1)) {
			return 1;
		}
		if (win(c, player2)) {
			return -1;
		}
		if (!hasEmptySpaces(c)) {
			return 0;
		}
		return null;
	}

	/**
	 * determines who wins
	 * @param c - the player (X or O)
	 * @return if there is a win or not
	 */
	private static boolean win(char[] gameBoard, char c) {
		if (gameBoard[0] == c && gameBoard[0] == gameBoard[1] && gameBoard[0] == gameBoard[2]) {
			return true;
		}
		if (gameBoard[3] == c && gameBoard[3] == gameBoard[4] && gameBoard[4] == gameBoard[5]) {
			return true;
		}
		if (gameBoard[6] == c && gameBoard[6] == gameBoard[7] && gameBoard[7] == gameBoard[8]) {
			return true;
		}
		if (gameBoard[0] == c && gameBoard[0] == gameBoard[3] && gameBoard[3] == gameBoard[6]) {
			return true;
		}
		if (gameBoard[1] == c && gameBoard[1] == gameBoard[4] && gameBoard[4] == gameBoard[7]) {
			return true;
		}
		if (gameBoard[2] == c && gameBoard[2] == gameBoard[5] && gameBoard[5] == gameBoard[8]) {
			return true;
		}
		if (gameBoard[0] == c && gameBoard[0] == gameBoard[4] && gameBoard[4] == gameBoard[8]) {
			return true;
		}
		if (board[2] == c && board[2] == board[4] && board[4] == board[6]) {
			return true;
		}
		return false;
	}
	
	public static void debugResults() {
		System.out.println("Player one: " + p1Counter);
		System.out.println("Player two: " + p2Counter);
		System.out.println("draws: " + drawCounter);
	}
}
