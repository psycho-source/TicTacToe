package com.game.tictactoe.utils;


import java.util.ArrayList;
import java.util.Random;

//A simple Game AI for single player mode
public class GameAI {

    TicTacBoard board;
    int role;         //0->Player1, 1->Player2

    public GameAI(TicTacBoard board, int pRole) {
        this.board = board;
        this.role = pRole;
    }

    public int makeMove() {

        //Get owned Cells
        ArrayList<Integer> ownCell = (role == 0) ? board.xCells : board.oCells;

        //Get oponent Cells
        ArrayList<Integer> eneCell = (role == 0) ? board.oCells : board.xCells;

        //Get available Cells
        ArrayList<Integer> availCell = board.availCells;

        //Check if CPU can make a winning move
        for (Integer i : availCell) {

            //Add the current value of i to ownCell
            ownCell.add(i);

            //If winning make the move
            if (board.compareWinSequence(ownCell)) {
                ownCell.remove(i);
                return i;
            }

            //else remove the added i and try again
            ownCell.remove(i);

        }

        //CPU can't win, check if we can block the win of opponent
        for (Integer i : availCell) {

            //Add the current value of i to opponent cell to determine win
            eneCell.add(i);

            //If winning block the move
            if (board.compareWinSequence(eneCell)) {
                eneCell.remove(i);
                return i;
            }

            //else remove the added i and try again
            eneCell.remove(i);

        }

        //If reached here then CPU cant make winning move, neither it cn block any, make a random move
        return availCell.get(new Random().nextInt(availCell.size()));

    }

}
