package ca.yorku.eecs3311.a1;

/**
 * Represents a move in the Othello game by storing the row and column positions.
 * This class encapsulates the row and column where a move is made, providing methods
 * to retrieve these values and a string representation of the move.
 * 
 * The class is designed to be simple, clear, and concise, as per OOP principles.
 * 
 * @author ilir & leroy
 */
public class Move {
    /**
     * The row position of the move on the board.
     */
    private int row;

    /**
     * The column position of the move on the board.
     */
    private int col;

    /**
     * Constructs a Move object with specified row and column positions.
     * 
     * @param row The row index of the move on the board.
     * @param col The column index of the move on the board.
     */
    public Move(int row, int col) {
        this.row = row;
        this.col = col;
    }

    /**
     * Retrieves the row index of the move.
     * 
     * @return The row index where the move is placed.
     */
    public int getRow() {
        return row;
    }

    /**
     * Retrieves the column index of the move.
     * 
     * @return The column index where the move is placed.
     */
    public int getCol() {
        return col;
    }

    /**
     * Provides a string representation of the move in the format (row, col).
     * 
     * @return A string representing the move's position on the board.
     */
    public String toString() {
        return "(" + this.row + "," + this.col + ")";
    }
}
