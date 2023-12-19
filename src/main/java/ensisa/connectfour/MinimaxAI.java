package ensisa.connectfour;

import ensisa.connectfour.model.Grid;

import java.util.Random;

public class MinimaxAI {
    private int searchDepth;
    public static final int MAX_WINNING_SCORE = 999999;
    public static final int MIN_WINNING_SCORE = -999999;

    public MinimaxAI() {
        searchDepth = 8;
    }

    public void makeMove(Grid grid) {
        int[] decision = maxPlay(grid.copyGrid(grid), 0, Integer.MIN_VALUE, Integer.MAX_VALUE);
        if(decision[0] != -1) {
            grid.play(decision[0]);
        }
    }

    public boolean isDone(int depth, Grid board, int score) {
        return depth >= searchDepth || board.isFull() || score >= MAX_WINNING_SCORE || score <= MIN_WINNING_SCORE;
    }

    private int[] maxPlay(Grid board, int depth, int alpha, int beta) {
        int score = evaluate(board);

        if (isDone(depth, board, score))
            return new int[]{-1, score};

        int[] max = new int[]{-1, Integer.MIN_VALUE};

        for (int column = 0; column < 7; column++) {
            Grid new_board = board.copyGrid(board);
            if (new_board.canPlay(column)) {
                new_board.play(column);
                int[] next = minPlay(new_board, depth + 1, alpha, beta);

                if (next[1] > max[1]) {
                    max[0] = column;
                    max[1] = next[1];
                    alpha = next[1];
                }

                if(beta <= alpha)
                    break;
            }
        }

        return max;
    }

    private int[] minPlay(Grid board, int depth, int alpha, int beta) {
        int score = evaluate(board);

        if (isDone(depth, board, score))
            return new int[]{-1, score};

        int[] min = new int[]{-1, Integer.MAX_VALUE};

        for (int column = 0; column < 7; column++) {
            Grid new_board = board.copyGrid(board);
            if (new_board.canPlay(column)) {
                new_board.play(column);
                int[] next = maxPlay(new_board, depth + 1, alpha, beta);

                if (next[1] < min[1]) {
                    min[0] = column;
                    min[1] = next[1];
                    beta = next[1];
                }

                if(beta <= alpha)
                    break;
            }
        }

        return min;
    }

    private int evaluate(Grid grid) {
        int score = 0;

        // Check rows
        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < 4; col++) {
                score += evaluateLine(grid, row, col, 0, 1);
            }
        }

        // Check columns
        for (int col = 0; col < 7; col++) {
            for (int row = 0; row < 3; row++) {
                score += evaluateLine(grid, row, col, 1, 0);
            }
        }

        // Check diagonals (bottom left to top right)
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 4; col++) {
                score += evaluateLine(grid, row, col, 1, 1);
            }
        }

        // Check diagonals (top left to bottom right)
        for (int row = 3; row < 6; row++) {
            for (int col = 0; col < 4; col++) {
                score += evaluateLine(grid, row, col, -1, 1);
            }
        }

        return score;
    }

    private int evaluateLine(Grid grid, int row, int col, int dRow, int dCol) {
        int player1Count = 0;
        int player2Count = 0;

        for (int i = 0; i < 4; i++) {
            if (grid.getCell(row + i * dRow, col + i * dCol) == Grid.PLAYER_RED) {
                player1Count++;
            } else if (grid.getCell(row + i * dRow, col + i * dCol) == Grid.PLAYER_YELLOW) {
                player2Count++;
            }
        }

        if (player1Count > 0 && player2Count > 0) {
            return 0; // Both players have discs in this line
        } else if (player1Count > 0) {
            return player1Count; // Only player 1 has discs in this line
        } else if (player2Count > 0) {
            return -player2Count; // Only player 2 has discs in this line
        } else {
            return 0; // No discs in this line
        }
    }
}