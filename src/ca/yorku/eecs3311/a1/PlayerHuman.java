package ca.yorku.eecs3311.a1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * PlayerHuman is responsible for allowing the human player (P1) to interact
 * with the Othello game. It takes input from the player via the command line,
 * prompting for row and column positions for their moves.
 * 
 * This class ensures that the player's input is validated and that valid moves
 * are returned to the game. In case of invalid input, appropriate error
 * messages are displayed.
 * 
 * @author ilir & leroy
 */
public class PlayerHuman {

    private static final String INVALID_INPUT_MESSAGE = "Invalid number, please enter 1-8"; // Error message for invalid input
    private static final String IO_ERROR_MESSAGE = "I/O Error";                             // Error message for I/O issues
    private static BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in)); // BufferedReader for user input

    private Othello othello;                                                                // Othello game instance
    private char player;                                                                    // Player token ('X' or 'O')

    /**
     * Constructs a PlayerHuman object with a reference to the Othello game and the player token.
     *
     * @param othello the current Othello game instance
     * @param player  the player token ('X' or 'O') for this player
     */
    public PlayerHuman(Othello othello, char player) {
        this.othello = othello;                                                             // Initialize the Othello game
        this.player = player;                                                               // Set the player token
    }

    /**
     * Gets the move from the player by prompting for row and column input.
     * 
     * @return a Move object representing the player's selected row and column
     */
    public Move getMove() {
        int row = getMove("row: ");                                                         // Get the row input from the player
        int col = getMove("col: ");                                                         // Get the column input from the player
        return new Move(row, col);                                                          // Return a new Move object with the player's move
    }

    /**
     * Prompts the player for a move input (row or column), ensuring the input is valid.
     * 
     * @param message the prompt message displayed to the player
     * @return the valid integer move (row or column) within the allowed range, or -1 if input is invalid
     */
    private int getMove(String message) {
        int move, lower = 0, upper = 7;                                                     // Define the valid range for input (0-7)

        while (true) {                                                                      // Infinite loop to repeatedly prompt until valid input
            try {
                System.out.print(message);                                                  // Print the message (row: or col:)
                String line = PlayerHuman.stdin.readLine();                                 // Read user input
                move = Integer.parseInt(line);                                              // Parse input to an integer
                if (lower <= move && move <= upper) {                                       // Check if the move is within the valid range
                    return move;                                                            // Return the valid move
                } else {
                    System.out.println(INVALID_INPUT_MESSAGE);                              // Inform user of invalid input
                }
            } catch (IOException e) {                                                       // Catch I/O exceptions
                System.out.println(IO_ERROR_MESSAGE);                                       // Display I/O error message
                break;                                                                      // Break out of the loop on error
            } catch (NumberFormatException e) {                                             // Catch invalid number format exceptions
                System.out.println(INVALID_INPUT_MESSAGE);                                  // Inform user of invalid input format
            }
        }
        return -1;                                                                          // Return -1 if input is invalid
    }
}