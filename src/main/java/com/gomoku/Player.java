package com.gomoku;

/**
 * 玩家类 - 表示五子棋游戏中的玩家
 */
public class Player {
    private String name;
    private char symbol;  // 'X' 或 'O'

    public Player(String name, char symbol) {
        this.name = name;
        this.symbol = symbol;
    }

    public String getName() {
        return name;
    }

    public char getSymbol() {
        return symbol;
    }

    @Override
    public String toString() {
        return name + " (" + symbol + ")";
    }
}