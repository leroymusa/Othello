package ca.yorku.eecs3311.a1;

/**
 * This controller uses the Model classes to allow the Human player P1 to play
 * the computer P2. The computer, P2 uses a greedy strategy. 
 * 
 * @author ilir & leroy
 *
 */
public class OthelloControllerHumanVSGreedy {
    private Othello othello;                                               // The Othello game instance
    private PlayerHuman player1;                                            // Human player (P1)
    private PlayerGreedy player2;                                           // Greedy AI player (P2)

    /**
     * Constructor initializes the Othello game and the two players (human vs greedy AI).
     */
    public OthelloControllerHumanVSGreedy() {
        this.othello = new Othello();                                       // Initialize the Othello game
        this.player1 = new PlayerHuman(this.othello, OthelloBoard.P1);      // P1 is the human player
        this.player2 = new PlayerGreedy(this.othello, OthelloBoard.P2);     // P2 is the greedy AI player
    }

    /**
     * Main game loop. Alternates between the human player (P1) and the greedy AI (P2)
     * until the game is over. Moves are made, and the board is updated after each turn.
     */
    public void play() {
        while (!othello.isGameOver()) {                                     // Continue until the game is over
            this.report();                                                  // Report current board status

            Move move = null;                                               // Initialize the move
            char whosTurn = othello.getWhosTurn();                          // Determine whose turn it is

            if (whosTurn == OthelloBoard.P1) {                              // If it's the human player's turn
                move = player1.getMove();                                   // Get move from human player
            } else {
                move = player2.getMove();                                   // Get move from greedy AI player
            }

            this.reportMove(whosTurn, move);                                // Report the move that was made

            if (move != null) {
                othello.move(move.getRow(), move.getCol());                 // Apply the move if valid
            } else {
                othello.passTurn();                                         // Pass the turn if no valid move
            }
        }
        this.reportFinal();                                                 // Report final game results
    }

    /**
     * Reports the move made by the current player.
     * 
     * @param whosTurn the current player making the move (P1 or P2)
     * @param move     the move made by the player
     */
    private void reportMove(char whosTurn, Move move) {
        System.out.println(whosTurn + " makes move " + move + "\n");         // Print the move made by the player
    }

    /**
     * Reports the current state of the board, showing both players' token counts and whose turn is next.
     */
    private void report() {
        String s = othello.getBoardString()                                 	// Get the board state as a string
           + OthelloBoard.P1 + ":" + othello.getCount(OthelloBoard.P1)  		// Show Player 1's token count
           + " " + OthelloBoard.P2 + ":" + othello.getCount(OthelloBoard.P2) 	// Show Player 2's token count
           + "  " + othello.getWhosTurn() + " moves next";             			// Indicate who moves next
        System.out.println(s);                                              	// Output the current board state
    }

    /**
     * Reports the final game state, including the final token counts and the winner.
     */
    private void reportFinal() {
        String s = othello.getBoardString()                                 // Get the final board state
                + OthelloBoard.P1 + ":" + othello.getCount(OthelloBoard.P1)  // Show Player 1's final token count
                + " " + OthelloBoard.P2 + ":" + othello.getCount(OthelloBoard.P2) // Show Player 2's final token count
                + "  " + othello.getWinner() + " won\n";                    // Announce the winner
        System.out.println(s);                                              // Output the final game results
    }

	/**
	 * Run main to play a Human (P1) against the computer P2. 
	 * The computer uses a greedy strategy, that is, it picks the first
	 * move which maximizes its number of token on the board.
	 * The output should be almost identical to that of OthelloControllerHumanVSHuman.
	 * @param args
	 */
	public static void main(String[] args) {
		OthelloControllerHumanVSGreedy oc = new OthelloControllerHumanVSGreedy();
		oc.play(); // this should work
	}
}