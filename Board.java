// Complete implementation of Board management for Gomoku

public class Board {
    private static final int SIZE = 15;
    private final int[][] board;

    public Board() {
        board = new int[SIZE][SIZE]; // 0 - empty, 1 - black stone, 2 - white stone
    }

    public boolean placeStone(int x, int y, int player) {
        if (x < 0 || x >= SIZE || y < 0 || y >= SIZE || board[x][y] != 0) {
            return false; // Invalid placement
        }
        board[x][y] = player;
        return true;
    }

    public boolean checkWin(int player) {
        // Check all directions: horizontal, vertical, diagonal (","), diagonal (\)
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (checkDirection(i, j, player)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean checkDirection(int x, int y, int player) {
        if (board[x][y] != player) {
            return false;
        }
        return checkLine(x, y, player, 1, 0) || // Horizontal
               checkLine(x, y, player, 0, 1) || // Vertical
               checkLine(x, y, player, 1, 1) || // Diagonal (")
               checkLine(x, y, player, 1, -1);  // Diagonal (\)
    }

    private boolean checkLine(int x, int y, int player, int dx, int dy) {
        int count = 0;
        for (int i = 0; i < 5; i++) {
            int newX = x + i * dx;
            int newY = y + i * dy;
            if (newX < 0 || newX >= SIZE || newY < 0 || newY >= SIZE || board[newX][newY] != player) {
                return false;
            }
            count++;
        }
        return count == 5;
    }
}