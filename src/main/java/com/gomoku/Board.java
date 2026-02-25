package com.gomoku;
import java.util.Arrays;

/**
 * 棋盘类 - 表示五子棋的 15×15 棋盘
 *
 * <p>棋盘使用二维字符数组存储每个格子的状态：
 * <ul>
 *   <li>{@code '\u0000'}（空字符）表示空位</li>
 *   <li>{@code 'X'} 表示玩家 1 的棋子</li>
 *   <li>{@code 'O'} 表示玩家 2 的棋子</li>
 * </ul>
 * </p>
 *
 * <p>胜负判断支持水平、垂直和两个对角线方向，任意方向连续 5 子即获胜。</p>
 */
public class Board {

    /** 棋盘的边长，标准五子棋为 15×15 */
    private final int SIZE = 15;

    /** 存储棋盘状态的二维字符数组，空位为空字符 '\u0000' */
    private final char[][] board;

    /**
     * 构造一个新的空棋盘，所有格子初始化为空字符。
     */
    public Board() {
        board = new char[SIZE][SIZE];
        // 将所有格子初始化为空字符（表示空位）
        for (char[] row : board) {
            Arrays.fill(row, '\u0000'); // Initialize with null character
        }
    }

    /**
     * 在指定位置落子。
     *
     * <p>若位置超出棋盘范围则抛出异常；若位置已被占用则返回 {@code false}。</p>
     *
     * @param row   行索引（0 到 SIZE-1）
     * @param col   列索引（0 到 SIZE-1）
     * @param stone 棋子符号（'X' 或 'O'）
     * @return 落子成功返回 {@code true}，位置已被占用返回 {@code false}
     * @throws IllegalArgumentException 若行列索引超出棋盘范围
     */
    public boolean placeStone(int row, int col, char stone) {
        // 检查位置是否在棋盘范围内
        if (row < 0 || row >= SIZE || col < 0 || col >= SIZE) {
            throw new IllegalArgumentException("Invalid position");
        }
        // 若该位置已有棋子，则落子失败
        if (board[row][col] != '\u0000') {
            return false; // Position already occupied
        }
        // 落子成功，将棋子符号写入棋盘
        board[row][col] = stone;
        return true;
    }

    /**
     * 检查在指定位置落子后，该棋子所属玩家是否获胜。
     *
     * <p>同时检查水平、垂直、主对角线和副对角线四个方向，
     * 任意方向连续 5 子即判定获胜。</p>
     *
     * @param row 刚落子的行索引
     * @param col 刚落子的列索引
     * @return 若该玩家获胜返回 {@code true}，否则返回 {@code false}
     */
    public boolean checkWin(int row, int col) {
        char stone = board[row][col]; // 获取当前落子的棋子符号
        return (checkDirection(row, col, stone, 1, 0) ||   // Horizontal（水平方向）
                checkDirection(row, col, stone, 0, 1) ||   // Vertical（垂直方向）
                checkDirection(row, col, stone, 1, 1) ||   // Diagonal: top-left to bottom-right（主对角线）
                checkDirection(row, col, stone, 1, -1));   // Diagonal: bottom-left to top-right（副对角线）
    }

    /**
     * 检查某一方向（及其反方向）上是否有连续 5 个相同棋子。
     *
     * <p>从 (row, col) 出发，分别沿 (deltaRow, deltaCol) 和
     * (-deltaRow, -deltaCol) 两个方向累计同色棋子数量（包含起始点本身）。</p>
     *
     * @param row      起始行索引
     * @param col      起始列索引
     * @param stone    要检查的棋子符号
     * @param deltaRow 行方向增量（-1、0 或 1）
     * @param deltaCol 列方向增量（-1、0 或 1）
     * @return 若该方向连续棋子数 ≥ 5 返回 {@code true}，否则返回 {@code false}
     */
    private boolean checkDirection(int row, int col, char stone, int deltaRow, int deltaCol) {
        int count = 1; // 起始点本身算 1 个

        // 沿正方向计数
        count += countStones(row, col, stone, deltaRow, deltaCol);
        // 沿反方向计数
        count += countStones(row, col, stone, -deltaRow, -deltaCol);

        // 连续 5 子及以上即获胜
        return count >= 5;
    }

    /**
     * 从指定位置沿给定方向统计连续同色棋子的数量（不含起始点）。
     *
     * @param row      起始行索引
     * @param col      起始列索引
     * @param stone    要统计的棋子符号
     * @param deltaRow 行方向增量
     * @param deltaCol 列方向增量
     * @return 沿该方向连续同色棋子的数量
     */
    private int countStones(int row, int col, char stone, int deltaRow, int deltaCol) {
        int count = 0;
        int r = row + deltaRow; // 从起始点的下一个位置开始
        int c = col + deltaCol;

        // 在棋盘范围内且棋子相同时持续计数
        while (r >= 0 && r < SIZE && c >= 0 && c < SIZE && board[r][c] == stone) {
            count++;
            r += deltaRow;
            c += deltaCol;
        }

        return count;
    }

    /**
     * 将当前棋盘状态打印到标准输出。
     *
     * <p>空位显示为 {@code '.'}, 有棋子的位置显示对应的棋子符号。</p>
     */
    public void printBoard() {
        for (char[] row : board) {
            for (char cell : row) {
                // 空字符显示为 '.'，有棋子则显示棋子符号
                System.out.print((cell == '\u0000' ? '.' : cell) + " ");
            }
            System.out.println();
        }
    }
}