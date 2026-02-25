package com.gomoku;

/**
 * 玩家类 - 表示五子棋游戏中的玩家
 *
 * <p>每个玩家拥有一个名称和一个棋子符号（'X' 或 'O'），
 * 用于在棋盘上标识该玩家的落子位置。</p>
 */
public class Player {

    /** 玩家的名称，例如 "Player1" 或用户自定义名称 */
    private String name;

    /** 玩家使用的棋子符号，取值为 'X' 或 'O' */
    private char symbol;

    /**
     * 构造一个新的玩家对象。
     *
     * @param name   玩家的名称，不能为 null
     * @param symbol 玩家使用的棋子符号，通常为 'X' 或 'O'
     */
    public Player(String name, char symbol) {
        this.name = name;
        this.symbol = symbol;
    }

    /**
     * 返回玩家的名称。
     *
     * @return 玩家名称字符串
     */
    public String getName() {
        return name;
    }

    /**
     * 返回玩家使用的棋子符号。
     *
     * @return 棋子符号字符（'X' 或 'O'）
     */
    public char getSymbol() {
        return symbol;
    }

    /**
     * 返回玩家的字符串表示形式，格式为 "名称 (符号)"。
     *
     * @return 玩家信息字符串，例如 "Alice (X)"
     */
    @Override
    public String toString() {
        return name + " (" + symbol + ")";
    }
}