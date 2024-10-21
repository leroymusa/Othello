package ca.yorku.eecs3311.a1;

import java.util.ArrayList;
import java.util.Random;

public class PlayerRandom {
    private Othello othello;
    private char player;
    private Random rand = new Random();

    /**
     * Constructor initializes the PlayerRandom with a reference to the game and player token.
     *
     * @param othello the current Othello game instance
     * @param player  the player token ('X' or 'O')
     */
    public PlayerRandom(Othello othello, char player) {
        this.othello = othello;
        this.player = player;
    }

    /**
     * Selects a random valid move from the available options.
     * It checks every cell on the board to see if a move is valid, then picks one at random.
     *
     * @return a valid Move object or null if no valid moves are available
     */
    public Move getMove() {
        ArrayList<Move> validMoves = new ArrayList<>();                     // Store valid moves for random selection

        // Loop through the board and check for valid moves
        
        for (int row = 0; row < Othello.DIMENSION; row++) {
            for (int col = 0; col < Othello.DIMENSION; col++) {
                if (othello.game_board.get(row, col) == OthelloBoard.EMPTY) // If the cell is empty
                { 
                    OthelloBoard tempBoard 
                    = new OthelloBoard(othello.game_board.getDimension());  // Create temp board
                    
                    copyBoard(othello.game_board, tempBoard);               // Copy current board state to temp
                    
                    if (tempBoard.move(row, col, player)) {                 // Check if the move is valid
                        validMoves.add(new Move(row, col));                 // Add valid move to the list
                    }
                }
            }
        }

        // Select and return a random valid move if available
        
        if (!validMoves.isEmpty()) {
            return validMoves.get(rand.nextInt(validMoves.size()));         // Return a randomly selected move
        }
        return null;                                                        // No valid moves, return null
    }

    /**
     * Copies the state of the source board into the destination board.
     *
     * @param src      	the board to copy from
     * @param dest 		the board to copy to
     */
    private void copyBoard(OthelloBoard src, OthelloBoard dest) {
        for (int i = 0; i < src.getDimension(); i++) {                   	// Loop through each row
            for (int j = 0; j < src.getDimension(); j++) {               	// Loop through each column
                dest.board[i][j] = src.get(i, j);							// Copy each cell value from source to destination
            }
        }
    }
}