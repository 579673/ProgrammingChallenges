package com.tictactoe;

public class Board {
    private char[] board;
    private boolean nextIsX;
    private char winner;
    private int movesMade;
    private final char X = 'X';
    private final char O = 'O';

    public Board() {
        this.board = new char[9];
        resetBoard();
    }

    public void resetBoard() {
        for (int i = 1; i <= 9; i++) {
            board[i - 1] = (char) (i + '0');
        }
        this.winner = '0';
        this.movesMade = 0;
        this.nextIsX = true;
    }

    public boolean makeMove(int position) {
        if (position < 1 || position > 9) {
            return false;
        }
        if (board[position - 1] == X || board[position - 1] == O) {
            return false;
        }
        board[position - 1] = nextIsX ? X : O;
        nextIsX = !nextIsX;
        movesMade++;
        return true;
    }

    public boolean notFull() {
        return movesMade < 9;
    }

    public boolean noWinner() {
        return !(fullCol() || fullRow() || fullDiagonal());
    }

    private boolean fullCol() {
        for (int i = 0; i < 3; i++) {
            if (board[i] == board[3 + i] && board[3 + i] == board[6 + i]) {
                winner = board[i];
                return true;
            }
        }
        return false;
    }

    private boolean fullRow() {
        for (int i = 0; i <= 6; i += 3) {
            if (board[i] == board[1 + i] && board[1 + i] == board[2 + i]) {
                winner = board[i];
               return true;
            }
        }
        return false;
    }

    private boolean fullDiagonal() {
        if (board[0] == board[4] && board[4] == board[8]) {
            winner = board[0];
            return true;
        }
        if (board[2] == board[4] && board[4] == board[6]) {
            winner = board[2];
            return true;
        }
        return false;
    }

    public char getWinner() {
        return winner;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < 9; i++) {
            if (i != 0 && i % 3 == 0) {
                sb.append("\n");
            }
            sb.append(board[i]);
            sb.append(" ");
        }
        return sb.toString();
    }
}