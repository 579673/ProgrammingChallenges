package com.tictactoe;

public class TicTacToe {
    public static void main(String[] args) {
        new TicTacToe(new Board(), new TicTacToeCLI()).playRound();
    }

    private Board board;
    private TicTacToeCLI cli;

    public TicTacToe(Board board, TicTacToeCLI cli) {
        this.board = board;
        this.cli = cli;
    }

    public void playRound() {
        while (board.noWinner() && board.notFull()) {
            cli.displayBoard(board);
            boolean legalMove;
            do {
                int move = cli.promptMove();
                legalMove = board.makeMove(move);
                if (!legalMove) {
                    cli.alertIllegalMove();
                }
            } while (!legalMove);
        }
        cli.displayBoard(board);
        char winner = board.getWinner();
        cli.alertWinner(winner);
        if (cli.promptPlayAgain()) {
            board.resetBoard();
            playRound();
        } else {
            cli.alertGoodbye();
        }
    }
}
