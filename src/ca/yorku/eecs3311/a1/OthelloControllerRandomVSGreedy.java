package ca.yorku.eecs3311.a1;

/**
 * The goal here is to print out the probability that Random wins and Greedy
 * wins as a result of playing 10000 games against each other with P1=Random and
 * P2=Greedy. What is your conclusion, which is the better strategy?
 * 
 ****************************************************************************
 *
 * Conclusion: ///////////
 * 
 * The Greedy strategy is more effective than the Random strategy, 
 * winning approximately 58% of the games.
 * - The Random strategy, though unpredictable, still wins about 38% 
 * of the time, showing that randomness can sometimes be beneficial.
 * 	
 * 
 *	The better strategy: ///////////
 *
 * - Between the two, the Greedy strategy is the better option, 
 * as it consistently outperforms Random.
 * 
 * ***************************************************
 * 
 * @author ilir & leroy
 *
 */

public class OthelloControllerRandomVSGreedy {
	/**
	 * Run main to execute the simulation and print out the two line results.
	 * Output looks like: 
	 * Probability P1 wins=.75 
	 * Probability P2 wins=.20
	 * @param args
	 */
	public static void main(String[] args) {
	    int p1wins = 0, p2wins = 0, numGames = 10000;                          // Initialize win counters for P1, P2 and number of games

	    // Simulate 10,000 games between Random (P1) and Greedy (P2)
	    
	    for (int i = 0; i < numGames; i++) {
	        Othello game = new Othello();                                       // Create a new Othello game instance
	        PlayerRandom player1 = new PlayerRandom(game, OthelloBoard.P1);     // P1 is the Random player
	        PlayerGreedy player2 = new PlayerGreedy(game, OthelloBoard.P2);     // P2 is the Greedy player

	        // Play the game until it's over
	        
	        while (!game.isGameOver()) {
	            Move move = null;                                               // Initialize move variable
	            char whosTurn = game.getWhosTurn();                             // Determine whose turn it is

	            if (whosTurn == OthelloBoard.P1) {                              // If it's P1's turn (Random)
	                move = player1.getMove();                                   // Get move from Random player
	            } else {                                                        // Else, it's P2's turn (Greedy)
	                move = player2.getMove();                                   // Get move from Greedy player
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

	        System.out.println("Probability P1 (Random) wins: " + (float) p1wins / numGames);
	        System.out.println("Probability P2 (Greedy) wins: " + (float) p2wins / numGames);
	      //System.out.println(1-(float)(p1wins+p2wins)/numGames); //test for draw
	    }
	}