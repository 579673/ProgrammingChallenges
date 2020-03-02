package com.tictactoe;

import java.util.InputMismatchException;
import java.util.Scanner;

public class TicTacToeCLI {
    Scanner in;

    public TicTacToeCLI() {
         this.in = new Scanner(System.in);
    }

    public void displayBoard(Board board) {
        System.out.println(board);
    }

    public int promptMove() {
        int move = -1;
        while (move == -1) {
            System.out.println("Enter your move (1-9): ");
            try {
                move = in.nextInt();
            } catch (InputMismatchException e) {
                alertIllegalMove();
            }
            in.nextLine();
        }
        return move;
    }

    public void alertIllegalMove() {
        System.out.println("Illegal move.");
    }

    public boolean promptPlayAgain() {
        String reply;
        do {
            System.out.println("Would you like to play again? Y/N: ");
            reply = in.nextLine();
        } while (!(reply.equalsIgnoreCase("y") || reply.equalsIgnoreCase("n")));
        return reply.equalsIgnoreCase("y");
    }

    public void alertGoodbye() {
        System.out.println("Goodbye! Thanks for playing.");
    }

    public void alertWinner(char winner) {
        if (winner == '0') {
            System.out.println("Draw!");
        } else {
            System.out.println("The winner is: " + winner);
        }
    }
}
