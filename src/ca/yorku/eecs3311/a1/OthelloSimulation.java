package ca.yorku.eecs3311.a1;

import java.util.Random;

public class OthelloSimulation {
	
	/*
	Null Hypothesis (H0): 
	Player 1 (P1 - Random) and Player 2 (P2 - Random) have equal chances of winning.

	Alternative Hypothesis (Ha): 
	Player 1 (P1 - Random) and Player 2 (P2 - Random) do not have equal chances of winning.

	Step 1: Direct Simulation
	- A large number of Othello games will be simulated where both P1 and P2 play randomly, 
	similar to repeatedly flipping a coin.
	- The number of wins for each player will be calculated, and the observed difference 
	between P1 and P2's wins will be analyzed to determine if it is due to chance.

	Step 2: Compare with Shuffling/Randomization
	- After collecting the win/loss data, shuffling can be applied by randomly assigning 
	outcomes to players to generate a null distribution.
	- This process helps evaluate whether the observed difference in wins could have 
	occurred by chance.

	Step 3: Calculate p-value
	- The p-value will be calculated by simulating random samples.
	- It represents how often a difference in win rates as extreme as the observed 
	one occurs by chance.
	*/

    public static void main(String[] args) {
        int p1Wins = 0;                                             // Track wins for Player 1 (Random)
        int p2Wins = 0;                                             // Track wins for Player 2 (Random)
        int numGames = 10000;                                       // Total number of games to simulate
        Random random = new Random();                               // Random instance for shuffling

        // Simulate the games
        
        for (int i = 0; i < numGames; i++) {
            Othello game = new Othello();                           			// New game instance
            PlayerRandom player1 = new PlayerRandom(game, OthelloBoard.P1);  	// Random P1
            PlayerRandom player2 = new PlayerRandom(game, OthelloBoard.P2);  	// Random P2

            // Play the game until it's over
            
            while (!game.isGameOver()) {
                Move move;                                           // Move instance for current move
                char whosTurn = game.getWhosTurn();                  // Check whose turn it is

                if (whosTurn == OthelloBoard.P1)
                    move = player1.getMove();                        // P1 makes a move
                else
                    move = player2.getMove();                        // P2 makes a move

                if (move != null)
                    game.move(move.getRow(), move.getCol());         // Make the move
                else
                    game.passTurn();                                 // If no valid move, pass the turn
            }

            // Determine the winner
            
            char winner = game.getWinner();                          // Get the game's winner
            if (winner == OthelloBoard.P1) {
                p1Wins++;                                            // Increment P1 wins if P1 wins
            } else if (winner == OthelloBoard.P2) {
                p2Wins++;                                            // Increment P2 wins if P2 wins
            }
        }

        // Calculate win probabilities
        
        float p1WinRate = (float) p1Wins / numGames;                 // Calculate P1 win probability
        float p2WinRate = (float) p2Wins / numGames;                 // Calculate P2 win probability

        System.out.println("P1 (Random) Win Probability: " + p1WinRate);
        System.out.println("P2 (Random) Win Probability: " + p2WinRate);

        // Simulate random shuffling to calculate p-value
        int shuffleCount = 100000;                                   // Number of shuffle simulations
        int extremeCases = 0;                                        // Count of extreme cases

        for (int i = 0; i < shuffleCount; i++) {
            int shuffledP1Wins = 0;                                  // Track wins for shuffled P1
            for (int j = 0; j < numGames; j++) {
                if (random.nextBoolean()) {
                    shuffledP1Wins++;                                // Increment if P1 wins in shuffle
                }
            }

            float shuffledP1WinRate = (float) shuffledP1Wins / numGames;    // Shuffled P1 win rate
            float shuffledP2WinRate = 1.0f - shuffledP1WinRate;            // Shuffled P2 win rate

            // Calculate the difference in win rates
            
            float shuffledDifference = Math.abs(shuffledP1WinRate - shuffledP2WinRate); // Shuffled difference
            float actualDifference = Math.abs(p1WinRate - p2WinRate);                  	// Actual difference

            // Check if the shuffled difference is more extreme than the actual difference
            
            if (shuffledDifference >= actualDifference) {
                extremeCases++;                                    // Count extreme cases where shuffle diff >= actual diff
            }
        }

        // Calculate p-value
        
        float pValue = (float) extremeCases / shuffleCount;          // p-value based on extreme cases
        System.out.println("p-value: " + pValue);

        // Conclusion based on p-value
        
        if (pValue < 0.05) {                                         // If p-value < 0.05, reject null hypothesis
            System.out.println("Reject H0: The difference is statistically significant.");
        } else {                                                     // If p-value >= 0.05, fail to reject null hypothesis
            System.out.println("Fail to reject H0: No significant difference between P1 and P2.");
        }
    }
}
