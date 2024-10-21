package ca.yorku.eecs3311.a1;

/**
 * Determine whether the first player or second player has the advantage when
 * both are playing a Random Strategy.
 * 
 * Do this by creating two players which use a random strategy and have them
 * play each other for 10000 games. What is your conclusion, does the first or
 * second player have some advantage, at least for a random strategy? 
 * State the null hypothesis H0, the alternate hypothesis Ha and 
 * about which your experimental results support. Place your short report in
 * randomVsRandomReport.txt.
 * 
 * @author ilir & leroy
 *
 */
public class OthelloControllerRandomVSRandom {
	/**
	 * Run main to execute the simulation and print out the two line results.
	 * Output looks like 
	 * Probability P1 wins=.75 
	 * Probability P2 wins=.20
	 * @param args
	 */
	public static void main(String[] args) {
	    int p1wins = 0, p2wins = 0, numGames = 10000;                          // Initialize win counters for P1, P2 and number of games

	    // Simulate 10,000 games between two Random players (P1 and P2)
	    
	    for (int i = 0; i < numGames; i++) {
	        Othello game = new Othello();                                       // Create a new Othello game instance
	        PlayerRandom player1 = new PlayerRandom(game, OthelloBoard.P1);     // P1 is the Random player
	        PlayerRandom player2 = new PlayerRandom(game, OthelloBoard.P2);     // P2 is also a Random player

	        // Play the game until it's over
	        
	        while (!game.isGameOver()) {
	            Move move = null;                                               // Initialize move variable
	            char whosTurn = game.getWhosTurn();                             // Determine whose turn it is

	            if (whosTurn == OthelloBoard.P1) {                              // If it's P1's turn (Random)
	                move = player1.getMove();                                   // Get move from Random player P1
	            } else {                                                        // Else, it's P2's turn (Random)
	                move = player2.getMove();                                   // Get move from Random player P2
	            }

	            if (move != null) {                                             // If a valid move is found
	                game.move(move.getRow(), move.getCol());                    // Apply the move
	            } else {
	                game.passTurn();                                            // Pass the turn if no valid move is found
	            }
	        }

	        // Determine the winner and update win counts
	        
	        char winner = game.getWinner();                                     // Get the winner of the game
	        if (winner == OthelloBoard.P1) {                                    // If P1 wins, increment P1's win count
	            p1wins++;
	        } else if (winner == OthelloBoard.P2) {                             // If P2 wins, increment P2's win count
	            p2wins++;
	        }
	    }
	    
	    
        System.out.println("Probability P1 wins=" + (float) p1wins / numGames);
        System.out.println("Probability P2 wins=" + (float) p2wins / numGames);
        //System.out.println(1-(float)(p1wins+p2wins)/numGames); //test for draw
    }
}
