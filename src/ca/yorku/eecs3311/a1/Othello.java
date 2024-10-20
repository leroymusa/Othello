package ca.yorku.eecs3311.a1;

import java.util.Random;

/**
 * Capture an Othello game. This includes an OthelloBoard as well as knowledge
 * of how many moves have been made, whosTurn is next (OthelloBoard.P1 or
 * OthelloBoard.P2). It knows how to make a move using the game_board and can
 * tell you statistics about the game, such as how many tokens P1 has and how
 * many tokens P2 has. It knows who the winner of the game is, and when the game
 * is over.
 * 
 * See the following for a short, simple introduction.
 * https://www.youtube.com/watch?v=Ol3Id7xYsY4
 * 
 *
 */
public class Othello {
	public static final int DIMENSION = 8; // This is an 8x8 game
	private char whosTurn = OthelloBoard.P1; // P1 moves first!
	private int numMoves = 0;
	OthelloBoard game_board; //not private to make sure it's visible in other classes

	/**
	 * Constructor that initializes the Othello game with a new game_board.
	 */
	public Othello() {
		this.game_board = new OthelloBoard(DIMENSION); //initialize the game_board
	}

	/**
	 * return P1,P2 or EMPTY depending on who moves next.
	 * 
	 * @return P1, P2 or EMPTY
	 */
	public char getWhosTurn() {
		return this.whosTurn;
	}

	/**
	 * Attempt to make a move for P1 or P2 (depending on whos turn it is) at
	 * position row, col. A side effect of this method is modification of whos turn
	 * and the move count.
	 * 
	 * @param row
	 * @param col
	 * @return whether the move was successfully made.
	 */
	public boolean move(int row, int col) {
		boolean isMove = game_board.move(row, col, whosTurn);

		if (isMove) {
			numMoves++;
			whosTurn = OthelloBoard.otherPlayer(whosTurn);
		}
		return isMove;
	}

	/**
	 * Gets the count of tokens for the specified player on the board.
	 * 
	 * @param player P1 or P2
	 * @return the number of tokens for player on the game_board
	 */
	public int getCount(char player) {
		return game_board.getCount(player);
	}

	/**
	 * Passes the turn to the other player if the current player has no valid moves.
	 * It checks the game_board's move availability and changes the turn
	 * accordingly.
	 * 
	 * @return void
	 */
	protected void passTurn() {

		char isMoveAvailable = game_board.hasMove(); //if no valid move, pass turn
		if (
				(whosTurn == OthelloBoard.P1 && isMoveAvailable != OthelloBoard.P1) 
											||
				(whosTurn == OthelloBoard.P2 && isMoveAvailable != OthelloBoard.P2)) 
		
		{
							whosTurn = OthelloBoard.otherPlayer(whosTurn);
		}
	}

	/**
	 * Returns the winner of the game based on the number of tokens for each player
	 * 
	 * @return P1, P2 or EMPTY for no winner, or the game is not finished.
	 */
	public char getWinner() {
		
		if (!isGameOver()) {
			return OthelloBoard.EMPTY;
		}
		int p1_cnt = getCount(OthelloBoard.P1);
		int p2_cnt = getCount(OthelloBoard.P2);

		if (p1_cnt > p2_cnt) {
			return OthelloBoard.P1;
		} else if (p2_cnt > p1_cnt) {
			return OthelloBoard.P2;
		} else {
			return OthelloBoard.EMPTY; //tie or no winner
		} 
	}

	/**
	 * Returns the winner of the game.
	 * 
	 * @return P1, P2 or EMPTY for no winner, or the game is not finished.
	 */
	public boolean isGameOver() {
	    return game_board.hasMove() == OthelloBoard.EMPTY;
	}

	/**
	 * Retrieves the current Othello game board.
	 * 
	 * @return The {@link OthelloBoard} object representing the current state of the game.
	 */
	public OthelloBoard getBoard() {
	    return this.game_board;
	}

	/**
	 * Provides a string representation of the current game board.
	 * This representation will show the positions of all the pieces on the board,
	 * typically with characters representing Player 1, Player 2, and empty spaces.
	 * 
	 * @return A string representation of the {@code game_board} displaying the current state of the game.
	 */
	public String getBoardString() {
	    return game_board.toString();
	}


	/**
	 * run this to test the current class. We play a completely random game. DO NOT
	 * MODIFY THIS!! See the assignment page for sample outputs from this.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		Random rand = new Random();
		Othello o = new Othello();
		System.out.println(o.getBoardString());
		while (!o.isGameOver()) {
			int row = rand.nextInt(8);
			int col = rand.nextInt(8);

			if (o.move(row, col)) {
				System.out.println("makes move (" + row + "," + col + ")");
				System.out.println(o.getBoardString() + o.getWhosTurn() + " moves next");
			}
		}

	}
}