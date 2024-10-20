package ca.yorku.eecs3311.a1;

import java.util.ArrayList;
import java.util.Random;

public class PlayerRandom {
    private Othello othello;
    private char player;
    private Random rand;

    public PlayerRandom(Othello othello, char player) {
        this.othello = othello;
        this.player = player;
        this.rand = new Random();
    }

    public Move getMove() {
        ArrayList<Move> validMoves = new ArrayList<>();

        // Attempt to simulate each cell and see if a move is possible
        for (int row = 0; row < Othello.DIMENSION; row++) {
            for (int col = 0; col < Othello.DIMENSION; col++) {
                if (othello.game_board.get(row, col) == OthelloBoard.EMPTY) {
                    OthelloBoard tempBoard = new OthelloBoard(othello.game_board.getDimension());
                    copyBoard(othello.game_board, tempBoard); 
                    if (tempBoard.move(row, col, player)) {
                        validMoves.add(new Move(row, col));
                    }
                }
            }
        }

        if (!validMoves.isEmpty()) {
            return validMoves.get(rand.nextInt(validMoves.size()));
        }
        return null;  // No valid moves available
    }

    private void copyBoard(OthelloBoard source, OthelloBoard destination) {
        // Copy the board state from source to destination
        for (int i = 0; i < source.getDimension(); i++) {
            for (int j = 0; j < source.getDimension(); j++) {
                destination.board[i][j] = source.get(i, j);
            }
        }
    }
}