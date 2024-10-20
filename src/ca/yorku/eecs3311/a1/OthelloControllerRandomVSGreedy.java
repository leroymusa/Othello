package ca.yorku.eecs3311.a1;

/**
 * The goal here is to print out the probability that Random wins and Greedy
 * wins as a result of playing 10000 games against each other with P1=Random and
 * P2=Greedy. What is your conclusion, which is the better strategy?
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
	        int p1wins = 0, p2wins = 0, draws = 0, numGames = 10000;
	        
	        for (int i = 0; i < numGames; i++) {
	            Othello game = new Othello();
	            PlayerRandom player1 = new PlayerRandom(game, OthelloBoard.P1); // P1 is the random player
	            PlayerGreedy player2 = new PlayerGreedy(game, OthelloBoard.P2); // P2 is the greedy player
	       
	            while (!game.isGameOver()) {
	            	 Move move = null;
	            	 char whosTurn = game.getWhosTurn();
	                if (whosTurn == OthelloBoard.P1)
	                  move = player1.getMove();
	                  else 
	                   move = player2.getMove();
	                if (move != null)
                        game.move(move.getRow(), move.getCol());
                    else { 
                        game.passTurn();
                    }
	            }

	            char winner = game.getWinner();
	            if (winner == OthelloBoard.P1) {
	                p1wins++;
	            } else if (winner == OthelloBoard.P2) {
	                p2wins++;
	            } else {
	                draws++;
	            }
	        }

	        System.out.println("Probability P1 (Random) wins: " + (float) p1wins / numGames);
	        System.out.println("Probability P2 (Greedy) wins: " + (float) p2wins / numGames);
	        System.out.println("Probability of a draw: " + (float) draws / numGames);
	    }
	}