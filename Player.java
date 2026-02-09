package com.gomoku;

public class Player {
    private String name;
    private char symbol; // 'X' or 'O'

    public Player(String name, char symbol) {
        this.name = name;
        if(symbol == 'X' || symbol == 'O') {
            this.symbol = symbol;
        } else {
            throw new IllegalArgumentException("Symbol must be either 'X' or 'O'.");
        }
    }

    public String getName() {
        return name;
    }

    public char getSymbol() {
        return symbol;
    }
}