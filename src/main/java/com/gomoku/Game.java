package com.gomoku;

import java.util.Scanner;

public class Game {
    private static final int SIZE = 15; // Size of the board
    private char[][] board;
    private char currentPlayer;

    public Game() {
        board = new char[SIZE][SIZE];
        currentPlayer = 'X'; // Player X starts
        initializeBoard();
    }

    private void initializeBoard() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                board[i][j] = '-'; // Empty space
            }
        }
    }

    public void play() {
        Scanner scanner = new Scanner(System.in);
        int x, y;
        boolean gameWon = false;
        while (!gameWon && !isBoardFull()) {
            System.out.println("Current board:");
            printBoard();
            System.out.printf("Player %c, enter your move (row and column): ", currentPlayer);
            x = scanner.nextInt();
            y = scanner.nextInt();
            if (isValidMove(x, y)) {
                board[x][y] = currentPlayer;
                gameWon = checkWin(x, y);
                currentPlayer = (currentPlayer == 'X') ? 'O' : 'X'; // Switch players
            } else {
                System.out.println("Invalid move, try again.");
            }
        }
        if (gameWon) {
            printBoard();
            System.out.printf("Player %c wins!", (currentPlayer == 'X') ? 'O' : 'X');
        } else {
            System.out.println("The game is a draw!");
        }
        scanner.close();
    }

    private boolean isValidMove(int x, int y) {
        return (x >= 0 && x < SIZE && y >= 0 && y < SIZE && board[x][y] == '-');
    }

    private boolean isBoardFull() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (board[i][j] == '-') {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean checkWin(int x, int y) {
        // Check horizontal, vertical, and both diagonal directions
        return (checkDirection(x, y, 1, 0) ||  // Horizontal
                checkDirection(x, y, 0, 1) ||  // Vertical
                checkDirection(x, y, 1, 1) ||  // Diagonal \
                checkDirection(x, y, 1, -1));  // Diagonal /
    }

    private boolean checkDirection(int x, int y, int dx, int dy) {
        int count = 1;
        count += countInDirection(x, y, dx, dy);
        count += countInDirection(x, y, -dx, -dy);
        return count >= 5;
    }

    private int countInDirection(int x, int y, int dx, int dy) {
        int count = 0;
        while (x >= 0 && x < SIZE && y >= 0 && y < SIZE && board[x][y] == currentPlayer) {
            count++;
            x += dx;
            y += dy;
        }
        return count;
    }

    private void printBoard() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
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