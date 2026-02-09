import java.util.Arrays;

public class Board {
    private final int SIZE = 15;
    private final char[][] board;

    public Board() {
        board = new char[SIZE][SIZE];
        for (char[] row : board) {
            Arrays.fill(row, '\u0000'); // Initialize with null character
        }
    }

    public boolean placeStone(int row, int col, char stone) {
        if (row < 0 || row >= SIZE || col < 0 || col >= SIZE) {
            throw new IllegalArgumentException("Invalid position");
        }
        if (board[row][col] != '\u0000') {
            return false; // Position already occupied
        }
        board[row][col] = stone;
        return true;
    }

    public boolean checkWin(int row, int col) {
        char stone = board[row][col];
        return (checkDirection(row, col, stone, 1, 0) ||   // Horizontal
                checkDirection(row, col, stone, 0, 1) ||   // Vertical
                checkDirection(row, col, stone, 1, 1) ||   // Diagonal: top-left to bottom-right
                checkDirection(row, col, stone, 1, -1));   // Diagonal: bottom-left to top-right
    }

    private boolean checkDirection(int row, int col, char stone, int deltaRow, int deltaCol) {
        int count = 1;

        count += countStones(row, col, stone, deltaRow, deltaCol);
        count += countStones(row, col, stone, -deltaRow, -deltaCol);

        return count >= 5;
    }

    private int countStones(int row, int col, char stone, int deltaRow, int deltaCol) {
        int count = 0;
        int r = row + deltaRow;
        int c = col + deltaCol;

        while (r >= 0 && r < SIZE && c >= 0 && c < SIZE && board[r][c] == stone) {
            count++;
            r += deltaRow;
            c += deltaCol;
        }

        return count;
    }
    
    public void printBoard() {
        for (char[] row : board) {
            for (char cell : row) {
                System.out.print((cell == '\u0000' ? '.' : cell) + " ");
            }
            System.out.println();
        }
    }
}