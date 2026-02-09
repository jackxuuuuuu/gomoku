// Game.java

import java.util.Scanner;

public class Game {
    private char[][] board;
    private char currentPlayer;
    private boolean gameWon;

    public Game() {
        board = new char[15][15]; // 15x15 board
        currentPlayer = 'X'; // Player 1 starts
        gameWon = false;
        initializeBoard();
    }

    private void initializeBoard() {
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                board[i][j] = '.'; // Empty cell
            }
        }
    }

    public void play() {
        Scanner scanner = new Scanner(System.in);
        while (!gameWon) {
            printBoard();
            System.out.println("Current player: " + currentPlayer);
            System.out.print("Enter your move (row and column): ");
            int row = scanner.nextInt();
            int col = scanner.nextInt();

            if (isMoveValid(row, col)) {
                board[row][col] = currentPlayer;
                if (checkWin(row, col)) {
                    gameWon = true;
                    printBoard();
                    System.out.println("Player " + currentPlayer + " wins!");
                }
                switchPlayer();
            } else {
                System.out.println("Invalid move. Try again.");
            }
        }
        scanner.close();
    }

    private boolean isMoveValid(int row, int col) {
        return row >= 0 && row < 15 && col >= 0 && col < 15 && board[row][col] == '.';
    }

    private void switchPlayer() {
        currentPlayer = (currentPlayer == 'X') ? 'O' : 'X'; // Switch player
    }

    private boolean checkWin(int row, int col) {
        return checkDirection(row, col, 1, 0) ||  // Horizontal
               checkDirection(row, col, 0, 1) ||  // Vertical
               checkDirection(row, col, 1, 1) ||  // Diagonal down-right
               checkDirection(row, col, 1, -1);  // Diagonal down-left
    }

    private boolean checkDirection(int row, int col, int deltaRow, int deltaCol) {
        int count = 1;
        count += countInDirection(row, col, deltaRow, deltaCol);
        count += countInDirection(row, col, -deltaRow, -deltaCol);
        return count >= 5;
    }

    private int countInDirection(int row, int col, int deltaRow, int deltaCol) {
        int count = 0;
        int newRow = row + deltaRow;
        int newCol = col + deltaCol;
        while (newRow >= 0 && newRow < 15 && newCol >= 0 && newCol < 15 && board[newRow][newCol] == currentPlayer) {
            count++;
            newRow += deltaRow;
            newCol += deltaCol;
        }
        return count;
    }

    private void printBoard() {
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        Game game = new Game();
        game.play();
    }
}