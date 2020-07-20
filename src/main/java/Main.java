

import java.util.Arrays;
import java.util.Scanner;

/**
 * Instructions
 * - Upon completion the program should let two players play Tic Tac Toe
 * - You can implement your own methods when you need to, or modify existing ones
 * - The isGameWon() method has some bugs, you need to find the bugs and fix them
 * - Implement the playing logic in the main function
 * - Thinking of edge cases and handling them is a plus
 * - executing input.nextLine(); in the main function will read input from the console
 */

public class Main {

    public static void main(String[] args) {
        Scanner input;
        String coordinates = null;
        boolean coordinateMarked = false;
        input = new Scanner(System.in);
        TicTacToe game = new TicTacToe();
        while (!game.isBoardFull()) {
            if (coordinateMarked) {
                game.switchPlayer();
            } else if (!TicTacToe.isEpmtyString(coordinates)) {
                System.out.println(coordinates + " coordinates you entered do not exist or already marked please mark the unselected valid coordinate");
            }
            System.out.println("Player " + game.getCurrentPlayer() + " enter your position Unmarked Position {as 0,0}");
            coordinates = input.nextLine();
            coordinateMarked = game.markCoordinates(coordinates);
            game.printBoard();
            if (game.isGameWon()) {
                System.out.println(game.getCurrentPlayer() + " is winner of game");
                return;
            }
        }
        System.out.println("Game draw");

    }

}

class TicTacToe {
    private char board[][] = {{'N', 'N', 'N'}, {'N', 'N', 'N'}, {'N', 'N', 'N'}};
    private char currentPlayer = 'X';

    static boolean isEpmtyString(String string) {
        if (string != null && string.trim().length() > 0) {
            return false;
        }
        return true;
    }

    public void printBoard() {
        for (char[] row : board) {
            System.out.println(Arrays.toString(row));
        }
    }

    public char getCurrentPlayer() {
        return this.currentPlayer;
    }

    public boolean isBoardFull() {
        for (char[] row : board) {
            if (new java.lang.String(row).contains("N")) {
                return false;
            }
        }
        return true;
    }

    public boolean markCoordinates(String line) {
        if (TicTacToe.isEpmtyString(line)) return false;
        String[] coordinateIndexes = line.split(",");
        if (coordinateIndexes.length > 1) {
            try {
                int xCoordinate = Integer.parseInt(coordinateIndexes[0]);
                int yCoordinate = Integer.parseInt(coordinateIndexes[1]);
                if (xCoordinate < 3 && yCoordinate < 3 && board[xCoordinate][yCoordinate] == 'N') {
                    board[xCoordinate][yCoordinate] = currentPlayer;
                    return true;
                }
            } catch (NumberFormatException e) {
                System.out.println("only numbers are allowed for the coordinates");
            }

        }
        return false;
    }

    public void switchPlayer() {
        if (currentPlayer == 'X') {
            currentPlayer = 'O';
        } else {
            currentPlayer = 'X';
        }
    }

    public boolean isGameWon() {
        for (int i = 0; i < board.length; i++) {
            // check rows
            if (board[0][i] == board[1][i] && board[1][i] == board[2][i]) {
                if (board[0][i] != 'N') return true;
            }

            // check cols
            if (board[i][0] == board[i][1] && board[i][1] == board[i][2]) {
                if (board[i][0] != 'N') return true;
            }
        }

        // check diag
        if (board[1][1] != 'N') {
            if (board[0][0] == board[1][1] && board[1][1] == board[2][2]) {
                return true;
            }
            if (board[0][2] == board[1][1] && board[1][1] == board[2][0]) {
                return true;
            }
        }
        // return rowsWon && colsWon && diagWon;
        return false;
    }

}


