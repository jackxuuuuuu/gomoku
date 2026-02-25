package com.gomoku;

import java.util.Scanner;

/**
 * 游戏核心类 - 控制五子棋对局的完整流程
 *
 * <p>该类包含棋盘状态管理、玩家轮换、落子验证、胜负判断以及棋盘显示等功能。
 * 游戏流程如下：
 * <ol>
 *   <li>初始化 15×15 棋盘，所有格子置为空（'-'）</li>
 *   <li>玩家 'X' 先行，双方轮流输入落子坐标</li>
 *   <li>每次落子后检查是否形成连续 5 子，若是则该玩家获胜</li>
 *   <li>若棋盘填满仍无胜者，则判为平局</li>
 * </ol>
 * </p>
 */
public class Game {

    /** 棋盘边长，标准五子棋为 15×15 */
    private static final int SIZE = 15; // Size of the board

    /** 存储棋盘状态的二维字符数组，'-' 表示空位，'X'/'O' 表示各玩家的棋子 */
    private char[][] board;

    /** 当前执棋方的棋子符号，取值为 'X' 或 'O' */
    private char currentPlayer;

    /**
     * 构造一个新的游戏实例，初始化棋盘并设置玩家 'X' 先行。
     */
    public Game() {
        board = new char[SIZE][SIZE];
        currentPlayer = 'X'; // Player X starts（玩家 X 先手）
        initializeBoard();
    }

    /**
     * 初始化棋盘，将所有格子设置为空位字符 '-'。
     */
    private void initializeBoard() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                board[i][j] = '-'; // Empty space（空位）
            }
        }
    }

    /**
     * 启动并运行游戏主循环，直到某方获胜或棋盘填满为止。
     *
     * <p>每轮循环中：
     * <ol>
     *   <li>打印当前棋盘</li>
     *   <li>提示当前玩家输入落子坐标（行和列，从 0 开始）</li>
     *   <li>验证落子合法性，合法则落子并检查胜负</li>
     *   <li>切换到下一位玩家</li>
     * </ol>
     * </p>
     */
    public void play() {
        Scanner scanner = new Scanner(System.in);
        int x, y;
        boolean gameWon = false;
        // 游戏主循环：未分胜负且棋盘未满时持续进行
        while (!gameWon && !isBoardFull()) {
            System.out.println("Current board:");
            printBoard();
            // 提示当前玩家输入坐标
            System.out.printf("Player %c, enter your move (row and column): ", currentPlayer);
            x = scanner.nextInt();
            y = scanner.nextInt();
            if (isValidMove(x, y)) {
                // 在指定位置落子
                board[x][y] = currentPlayer;
                // 检查当前玩家是否获胜
                gameWon = checkWin(x, y);
                // 切换到下一位玩家（'X' ↔ 'O'）
                currentPlayer = (currentPlayer == 'X') ? 'O' : 'X'; // Switch players
            } else {
                System.out.println("Invalid move, try again.");
            }
        }
        // 游戏结束：打印最终棋盘并宣布结果
        if (gameWon) {
            printBoard();
            // 注意：gameWon 为 true 时 currentPlayer 已切换，需还原上一位玩家
            System.out.printf("Player %c wins!", (currentPlayer == 'X') ? 'O' : 'X');
        } else {
            System.out.println("The game is a draw!");
        }
        scanner.close();
    }

    /**
     * 检查在指定坐标落子是否合法。
     *
     * <p>合法条件：坐标在棋盘范围内，且该位置为空位（'-'）。</p>
     *
     * @param x 行索引（0 到 SIZE-1）
     * @param y 列索引（0 到 SIZE-1）
     * @return 合法返回 {@code true}，否则返回 {@code false}
     */
    private boolean isValidMove(int x, int y) {
        return (x >= 0 && x < SIZE && y >= 0 && y < SIZE && board[x][y] == '-');
    }

    /**
     * 检查棋盘是否已填满（无空位）。
     *
     * @return 棋盘已满返回 {@code true}，否则返回 {@code false}
     */
    private boolean isBoardFull() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (board[i][j] == '-') {
                    return false; // 找到空位，棋盘未满
                }
            }
        }
        return true;
    }

    /**
     * 检查在 (x, y) 落子后当前玩家是否获胜。
     *
     * <p>同时检查水平、垂直、主对角线和副对角线四个方向，
     * 任意方向连续 5 子即判定获胜。</p>
     *
     * @param x 刚落子的行索引
     * @param y 刚落子的列索引
     * @return 获胜返回 {@code true}，否则返回 {@code false}
     */
    private boolean checkWin(int x, int y) {
        // 同时检查水平、垂直和两个对角线方向
        // Check horizontal, vertical, and both diagonal directions
        return (checkDirection(x, y, 1, 0) ||  // Horizontal（水平方向）
                checkDirection(x, y, 0, 1) ||  // Vertical（垂直方向）
                checkDirection(x, y, 1, 1) ||  // Diagonal \（主对角线）
                checkDirection(x, y, 1, -1));  // Diagonal /（副对角线）
    }

    /**
     * 检查某一方向（及其反方向）上是否有连续 5 个当前玩家的棋子。
     *
     * @param x  起始行索引
     * @param y  起始列索引
     * @param dx 行方向增量（-1、0 或 1）
     * @param dy 列方向增量（-1、0 或 1）
     * @return 若该方向连续棋子数 ≥ 5 返回 {@code true}，否则返回 {@code false}
     */
    private boolean checkDirection(int x, int y, int dx, int dy) {
        int count = 1; // 起始点本身算 1 个
        // 沿正方向累计同色棋子数
        count += countInDirection(x, y, dx, dy);
        // 沿反方向累计同色棋子数
        count += countInDirection(x, y, -dx, -dy);
        return count >= 5;
    }

    /**
     * 从指定位置沿给定方向统计连续同色棋子的数量（不含起始点）。
     *
     * <p>注意：该方法直接从 (x, y) 出发（含起始点本身），因此调用方需确保
     * 起始点已是当前玩家的棋子。</p>
     *
     * @param x  起始行索引
     * @param y  起始列索引
     * @param dx 行方向增量
     * @param dy 列方向增量
     * @return 沿该方向（含起始点）连续同色棋子的数量
     */
    private int countInDirection(int x, int y, int dx, int dy) {
        int count = 0;
        // 在棋盘范围内且棋子属于当前玩家时持续计数
        while (x >= 0 && x < SIZE && y >= 0 && y < SIZE && board[x][y] == currentPlayer) {
            count++;
            x += dx;
            y += dy;
        }
        return count;
    }

    /**
     * 将当前棋盘状态打印到标准输出。
     *
     * <p>每个格子以空格分隔，'-' 表示空位，'X'/'O' 表示各玩家棋子。</p>
     */
    private void printBoard() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
    }

    /**
     * 程序入口方法，创建游戏实例并启动对局。
     *
     * @param args 命令行参数（未使用）
     */
    public static void main(String[] args) {
        Game game = new Game();
        game.play();
    }
}