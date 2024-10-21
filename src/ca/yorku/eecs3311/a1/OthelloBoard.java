package ca.yorku.eecs3311.a1;

/**
 * Keep track of all of the tokens on the board. This understands some
 * interesting things about an Othello board, what the board looks like at the
 * start of the game, what the players tokens look like ('X' and 'O'), whether
 * given coordinates are on the board, whether either of the players have a move
 * somewhere on the board, what happens when a player makes a move at a specific
 * location (the opposite players tokens are flipped).
 * 
 * Othello makes use of the OthelloBoard.
 * 
 * @author Ilir & Leroy
 *
 */
public class OthelloBoard {
	
	public static final char EMPTY = ' ', P1 = 'X', P2 = 'O', BOTH = 'B';
	private int dim = 8;
	private char[][] board;

	public OthelloBoard(int dim) {
		this.dim = dim;
		setBoard(new char[this.dim][this.dim]);
		for (int row = 0; row < this.dim; row++) {
			for (int col = 0; col < this.dim; col++) {
				this.getBoard()[row][col] = EMPTY;
			}
		}
		int mid = this.dim / 2;
		this.getBoard()[mid - 1][mid - 1] = this.getBoard()[mid][mid] = P1;
		this.getBoard()[mid][mid - 1] = this.getBoard()[mid - 1][mid] = P2;
	}

	/**
     * Returns the dimension of the Othello board.
     * 
     * @return the board dimension.
     */
    public int getDimension() {
        return this.dim;                                                   // Return board dimension
    }

	/**
	 * 
	 * @param player either P1 or P2
	 * @return P2 or P1, the opposite of player
	 */
    public static char otherPlayer(char player) {
        if (player == P1) {                                                // If player is 'X', return 'O'
            return P2;
        } else if (player == P2) {                                         // If player is 'O', return 'X'
            return P1;
        } else {
            return EMPTY;                                                  // Invalid player case
        }
	}

	/**
	 * 
	 * @param row starting row, in {0,...,dim-1} (typically {0,...,7})
	 * @param col starting col, in {0,...,dim-1} (typically {0,...,7})
	 * @return P1,P2 or EMPTY, EMPTY is returned for an invalid (row,col)
	 */
	public char get(int row, int col) {
	    if (validCoordinate(row, col)) {
	        return this.getBoard()[row][col];
	    }
	    return EMPTY;
	}

	/**
	 * 
	 * @param row starting row, in {0,...,dim-1} (typically {0,...,7})
	 * @param col starting col, in {0,...,dim-1} (typically {0,...,7})
	 * @return whether (row,col) is a position on the board. Example: (6,12) is not
	 *         a position on the board.
	 */
	private boolean validCoordinate(int row, int col) {
	    return row >= 0 && row < this.dim && col >= 0 && col < this.dim;
	}

	/**
	 * Check if there is an alternation of P1 next to P2, starting at (row,col) in
	 * direction (drow,dcol). That is, starting at (row,col) and heading in
	 * direction (drow,dcol), you encounter a sequence of at least one P1 followed
	 * by a P2, or at least one P2 followed by a P1. The board is not modified by
	 * this method. Why is this method important? If
	 * alternation(row,col,drow,dcol)==P1, then placing P1 right before (row,col),
	 * assuming that square is EMPTY, is a valid move, resulting in a collection of
	 * P2 being flipped.
	 * 
	 * @param row  starting row, in {0,...,dim-1} (typically {0,...,7})
	 * @param col  starting col, in {0,...,dim-1} (typically {0,...,7})
	 * @param drow the row direction, in {-1,0,1}
	 * @param dcol the col direction, in {-1,0,1}
	 * @return P1, if there is an alternation P2 ...P2 P1, or P2 if there is an
	 *         alternation P1 ... P1 P2 in direction (dx,dy), EMPTY if there is no
	 *         alternation
	 */
	private char alternation(int row, int col, int drow, int dcol) {
		
		// Check if the starting position is EMPTY, because alternation can't start from a non-empty square.
		
	    if (get(row, col) != EMPTY) {                                          // If the current square is not empty
	        return EMPTY;                                                      // Return EMPTY, no valid alternation
	    }

	    // Move to the next position in the direction (drow, dcol)
	    
	    row += drow;                                                           // Update row to the next in the direction
	    col += dcol;                                                           // Update column to the next in the direction

	    // Check if we're still within the board and if the first token we encounter is the opponent's
	    
	    char opponent = otherPlayer(get(row - drow, col - dcol));              // Identify the opponent of the current player
	    if (!validCoordinate(row, col) || get(row, col) != opponent) {         // If out of bounds or no opponent token
	        return EMPTY;                                                      // Return EMPTY, no valid alternation
	    }

	    // Keep moving in the direction (drow, dcol) until we find the current player's token or hit the edge
	    
	    while (validCoordinate(row, col)) {                                    // While still within valid board coordinates
	        if (get(row, col) == opponent) {                                   // If current square has opponent's token
	            row += drow;                                                   // Continue moving in the same direction
	            col += dcol;
	        } else if (get(row, col) == otherPlayer(opponent)) {               // If we find the current player's token
	            return otherPlayer(opponent);                                  // Valid alternation, return current player
	        } else {                                                           // If it's not opponent or player's token
	            break;                                                         // Break out of loop, invalid alternation
	        }
	    }

	    return EMPTY;                                                          // No valid alternation found, return EMPTY
	}


	
	

	/**
	 * flip all other player tokens to player, starting at (row,col) in direction
	 * (drow, dcol). Example: If (drow,dcol)=(0,1) and player==O then XXXO will
	 * result in a flip to OOOO
	 * 
	 * @param row    starting row, in {0,...,dim-1} (typically {0,...,7})
	 * @param col    starting col, in {0,...,dim-1} (typically {0,...,7})
	 * @param drow   the row direction, in {-1,0,1}
	 * @param dcol   the col direction, in {-1,0,1}
	 * @param player Either OthelloBoard.P1 or OthelloBoard.P2, the target token to
	 *               flip to.
	 * @return the number of other player tokens actually flipped, -1 if this is not
	 *         a valid move in this one direction, that is, EMPTY or the end of the
	 *         board is reached before seeing a player token.
	 */
	private int flip(int row, int col, int drow, int dcol, char player) {
	    char opponent = otherPlayer(player);                                  // Get the opponent's token
	    int flipped = 0;                                                      // Initialize flip counter

	    // Move to the next position in the direction (drow, dcol)
	    
	    row += drow;
	    col += dcol;

	    // Ensure the first token in the direction is the opponent's
	    
	    if (!validCoordinate(row, col) || get(row, col) != opponent) {        // Exit early if no opponent token found
	        return -1;                                                        // Invalid move
	    }

	    // Continue moving in the direction (drow, dcol) to find player's token
	    
	    while (validCoordinate(row, col) && get(row, col) == opponent) {      // While there are opponent's tokens
	        row += drow;                                                      // Keep moving
	        col += dcol;
	    }

	    // If the sequence doesn't end with the player's token, it's invalid
	    
	    if (!validCoordinate(row, col) || get(row, col) != player) {          // If no player token found
	        return -1;                                                        // Invalid move, no flips
	    }

	    // Move back, flipping all opponent tokens along the way
	    
	    do {
	        row -= drow;                                                      // Move back
	        col -= dcol;
	        this.getBoard()[row][col] = player;                                    // Flip the token to player's token
	        flipped++;                                                        // Increment flip count
	    } while (get(row - drow, col - dcol) == opponent);                    // Continue flipping until no opponent tokens

	    return flipped;                                                       // Return the number of flipped tokens
	}

	
	
	
	
	/**
	 * Return which player has a move (row,col) in direction (drow,dcol).
	 * 
	 * @param row  starting row, in {0,...,dim-1} (typically {0,...,7})
	 * @param col  starting col, in {0,...,dim-1} (typically {0,...,7})
	 * @param drow the row direction, in {-1,0,1}
	 * @param dcol the col direction, in {-1,0,1}
	 * @return P1,P2,EMPTY
	 */
	private char hasMove(int row, int col, int drow, int dcol) {
	    if (get(row, col) != EMPTY) {                                           // Early exit if the starting square isn't empty
	        return EMPTY;
	    }

	    row += drow;                                                            // Move to the next position
	    col += dcol;

	    if (!validCoordinate(row, col)) {                                       // Early exit if out of bounds
	        return EMPTY;
	    }

	    char firstToken = get(row, col);                                        // Get token at the next position
	    if (firstToken != P1 && firstToken != P2) {                             // If not an opponent's token, no valid move
	        return EMPTY;
	    }

	    char currentPlayer = otherPlayer(firstToken);                           // Current player is opposite of the first token
	    row += drow;                                                            // Continue moving in the direction
	    col += dcol;

	    // Traverse the board until either finding the current player's token or hitting an invalid condition
	    
	    while (validCoordinate(row, col)) {                                     // Loop while within bounds
	        char currentToken = get(row, col);                                  // Get token at the current position

	        if (currentToken == currentPlayer) {                                // Found the current player's token, valid move
	            return currentPlayer;
	        }
	        if (currentToken == EMPTY) {                                        // Hit an empty square, no valid move
	            return EMPTY;
	        }

	        row += drow;                                                        // Move further in the direction
	        col += dcol;
	    }

	    return EMPTY;                                                           // No valid sequence found
	}






	/**
	 * 
	 * @return whether P1,P2 or BOTH have a move somewhere on the board, EMPTY if
	 *         neither do.
	 */
	public char hasMove() {
	    boolean p1CanMove=false, p2CanMove  = false;						   // Track if Player 1 & Player 2 can move

	    // Iterate through all empty positions on the board
	    
	    for (int row = 0; row < this.dim; row++) {
	        for (int col = 0; col < this.dim; col++) {
	            if (this.getBoard()[row][col] != EMPTY) {                           // Skip non-empty squares
	                continue;
	            }

	            // Check all 8 possible directions (ignoring (0,0) since it doesn't move)
	            
	            for (int drow = -1; drow <= 1; drow++) {
	                for (int dcol = -1; dcol <= 1; dcol++) {
	                    if (drow == 0 && dcol == 0) {                          // Skip (0,0) direction
	                        continue;
	                    }

	                    char moveResult = hasMove(row, col, drow, dcol);       // Check if there's a valid move
	                    if (moveResult == P1) {
	                        p1CanMove = true;                                  // Player 1 can move
	                    } else if (moveResult == P2) {
	                        p2CanMove = true;                                  // Player 2 can move
	                    }

	                    if (p1CanMove && p2CanMove) {                          // If both players have moves
	                        return BOTH;                                       // Exit early and return BOTH
	                    }
	                }
	            }
	        }
	    }

	    // Determine which player has moves and return the appropriate constant
	    
	    if (p1CanMove) {
	        return P1;                                                         // Player 1 has a move
	    } else if (p2CanMove) {
	        return P2;                                                         // Player 2 has a move
	    }
	    return EMPTY;                                                          // No moves for either player
	}






	/**
	 * Make a move for player at position (row,col) according to Othello rules,
	 * making appropriate modifications to the board. Nothing is changed if this is
	 * not a valid move.
	 * 
	 * @param row    starting row, in {0,...,dim-1} (typically {0,...,7})
	 * @param col    starting col, in {0,...,dim-1} (typically {0,...,7})
	 * @param player P1 or P2
	 * @return true if player moved successfully at (row,col), false otherwise
	 */
	public boolean move(int row, int col, char player) {
		// HINT: Use some of the above helper methods to get this methods
		
	    // Early exit if the position is not empty
		
	    if (get(row, col) != EMPTY) {
	        return false;                                                      // Invalid move, as the square is occupied
	    }

	    boolean validMove = false;                                             // Track if any direction results in a valid move

	    // Check all 8 possible directions for a valid move
	    
	    for (int drow = -1; drow <= 1; drow++) {
	        for (int dcol = -1; dcol <= 1; dcol++) {
	            if (drow == 0 && dcol == 0) {                                  // Skip the (0,0) direction as it doesn't move
	                continue;
	            }

	            // Attempt to flip in the given direction
	            
	            int flipped = flip(row, col, drow, dcol, player);              // Try to flip opponent's tokens
	            if (flipped > 0) {                                             // If any tokens are flipped, it's a valid move
	                validMove = true;
	            }
	        }
	    }

	    // If the move is valid, place the player's token at the specified position
	    
	    if (validMove) {
	        this.getBoard()[row][col] = player;                                      // Update the board with the player's token
	    }

	    return validMove;                                                       // Return whether the move was valid
	}
	


	/**
	 * 
	 * @param player P1 or P2
	 * @return the number of tokens on the board for player
	 */
	public int getCount(char player) {
	    int count = 0;
	    for (int row = 0; row < this.dim; row++) {
	        for (int col = 0; col < this.dim; col++) {
	            if (this.getBoard()[row][col] == player) {
	                count++;
	            }
	        }
	    }
	    return count;
	}

	/**
	 * @return a string representation of this, just the play area, with no
	 *         additional information. DO NOT MODIFY THIS!!
	 */
	public String toString() {
		/**
		 * See assignment web page for sample output.
		 */
		String s = "";
		s += "  ";
		for (int col = 0; col < this.dim; col++) {
			s += col + " ";
		}
		s += '\n';

		s += " +";
		for (int col = 0; col < this.dim; col++) {
			s += "-+";
		}
		s += '\n';

		for (int row = 0; row < this.dim; row++) {
			s += row + "|";
			for (int col = 0; col < this.dim; col++) {
				s += this.getBoard()[row][col] + "|";
			}
			s += row + "\n";

			s += " +";
			for (int col = 0; col < this.dim; col++) {
				s += "-+";
			}
			s += '\n';
		}
		s += "  ";
		for (int col = 0; col < this.dim; col++) {
			s += col + " ";
		}
		s += '\n';
		return s;
	}

	/**
	 * A quick test of OthelloBoard. Output is on assignment page.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		
		OthelloBoard ob = new OthelloBoard(8);
		System.out.println(ob.toString());
		System.out.println("getCount(P1)=" + ob.getCount(P1));
		System.out.println("getCount(P2)=" + ob.getCount(P2));
		for (int row = 0; row < ob.dim; row++) {
			for (int col = 0; col < ob.dim; col++) {
				ob.getBoard()[row][col] = P1;
			}
		}
		System.out.println(ob.toString());
		System.out.println("getCount(P1)=" + ob.getCount(P1));
		System.out.println("getCount(P2)=" + ob.getCount(P2));

		// Should all be blank
		for (int drow = -1; drow <= 1; drow++) {
			for (int dcol = -1; dcol <= 1; dcol++) {
				System.out.println("alternation=" + ob.alternation(4, 4, drow, dcol));
			}
		}

		for (int row = 0; row < ob.dim; row++) {
			for (int col = 0; col < ob.dim; col++) {
				if (row == 0 || col == 0) {
					ob.getBoard()[row][col] = P2;
				}
			}
		}
		System.out.println(ob.toString());

		// Should all be P2 (O) except drow=0,dcol=0
		for (int drow = -1; drow <= 1; drow++) {
			for (int dcol = -1; dcol <= 1; dcol++) {
				System.out.println("direction=(" + drow + "," + dcol + ")");
				System.out.println("alternation=" + ob.alternation(4, 4, drow, dcol));
			}
		}

		// Can't move to (4,4) since the square is not empty
		System.out.println("Trying to move to (4,4) move=" + ob.move(4, 4, P2));

		ob.getBoard()[4][4] = EMPTY;
		ob.getBoard()[2][4] = EMPTY;

		System.out.println(ob.toString());

		for (int drow = -1; drow <= 1; drow++) {
			for (int dcol = -1; dcol <= 1; dcol++) {
				System.out.println("direction=(" + drow + "," + dcol + ")");
				System.out.println("hasMove at (4,4) in above direction =" + ob.hasMove(4, 4, drow, dcol));
			}
		}
		System.out.println("who has a move=" + ob.hasMove());
		System.out.println("Trying to move to (4,4) move=" + ob.move(4, 4, P2));
		System.out.println(ob.toString());

	}

	/**
	 * @return the board
	 */
	public char[][] getBoard() {
		return board;
	}

	/**
	 * @param board the board to set
	 */
	public void setBoard(char[][] board) {
		this.board = board;
	}
}
