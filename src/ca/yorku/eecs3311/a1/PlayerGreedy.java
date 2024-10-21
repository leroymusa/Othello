package ca.yorku.eecs3311.a1;

/**
 * PlayerGreedy makes a move by considering all possible moves that the player
 * can make. Each move leaves the player with a total number of tokens.
 * getMove() returns the first move which maximizes the number of
 * tokens owned by this player. In case of a tie, between two moves,
 * (row1,column1) and (row2,column2) the one with the smallest row wins. In case
 * both moves have the same row, then the smaller column wins.
 * 
 * Example: Say moves (2,7) and (3,1) result in the maximum number of tokens for
 * this player. Then (2,7) is returned since 2 is the smaller row.
 * 
 * Example: Say moves (2,7) and (2,4) result in the maximum number of tokens for
 * this player. Then (2,4) is returned, since the rows are tied, but (2,4) has
 * the smaller column.
 * 
 * See the examples supplied in the assignment handout.
 * 
 * @author ilir & leroy
 *
 */
public class PlayerGreedy {
    private Othello othello;         // Reference to the Othello game instance
    private char player;             // The player this strategy is for (e.g., 'X' or 'O')

    /**
     * Constructor to initialize the Othello game instance and the player.
     * 
     * @param othello Reference to the Othello game instance
     * @param player  The player character ('X' or 'O') using this strategy
     */
    public PlayerGreedy(Othello othello, char player) {
        this.othello = othello;      // Assign the game instance
        this.player = player;        // Assign the player
    }

    /**
     * Determines the best move for the player by evaluating all possible moves 
     * and selecting the one that maximizes the player's token count. If multiple 
     * moves yield the same number of tokens, the lexicographically smallest 
     * move (based on row and column) is returned.
     * 
     * @return The Move that maximizes the player's token count, with ties broken 
     *         by the smallest row, and then the smallest column.
     */
    public Move getMove() {
        Move bestMove = null;        // Store the best move found
        int maxCount = -1;           // Store the maximum token count after any move

        // Iterate over all board cells
        
        for (int row = 0; row < Othello.DIMENSION; row++) {          // Loop through each row
            for (int col = 0; col < Othello.DIMENSION; col++) {      // Loop through each column
                
                // Temporary board to simulate the move
            	
                OthelloBoard tempBoard = new OthelloBoard(Othello.DIMENSION);    
                copyBoard(this.othello.game_board, tempBoard);       // Copy the current board state to tempBoard

                // Check if the move is valid
                
                if (tempBoard.move(row, col, player)) {              // If move at (row, col) is valid for this player
                    int count = tempBoard.getCount(player);          // Get token count after this move

                    // If this move yields more tokens than previous best, update best move
                    
                    if (count > maxCount) {                          
                        maxCount = count;                            // Update maxCount
                        bestMove = new Move(row, col);               // Update bestMove to current move
                    } 
                    
                    // If move yields the same number of tokens as the best move, choose lexicographically smaller
                    
                    else if (count == maxCount) {                    
                        if (bestMove == null ||                      // If no bestMove is set, or
                            row < bestMove.getRow() ||               // If current row is smaller, or
                            (row == bestMove.getRow() 				 // If rows are equal
                            			&& 							 // and
                            col < bestMove.getCol())) {  		 	 // current column is smaller
                            bestMove = new Move(row, col);           // Update bestMove to current move
                        }
                    }
                }
            }
        }
        
        return bestMove;                                               // Return the best move found
    }

    /**
     * Copies the state of the original Othello board into the copy board.
     * 
     * @param original The original Othello board.
     * @param copy The copy where the original board's state is replicated.
     */
    private void copyBoard(OthelloBoard original, OthelloBoard copy) {
        for (int row = 0; row < Othello.DIMENSION; row++) {            // Loop through each row of the board
            for (int col = 0; col < Othello.DIMENSION; col++) {        // Loop through each column of the board
                copy.board[row][col] = original.get(row, col);         // Copy the value from original to copy
            }
        }
    }
}