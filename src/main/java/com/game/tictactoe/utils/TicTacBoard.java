package com.game.tictactoe.utils;

import java.util.ArrayList;
import java.util.Arrays;

//Helper class for TicTacToe game
public class TicTacBoard {

    public ArrayList<Integer> xCells;
    public ArrayList<Integer> oCells;
    public ArrayList<Integer> availCells;
    ArrayList<Integer> allCells;
    ArrayList<ArrayList<Integer>> winSequence;

    public TicTacBoard() {
        allCells = new ArrayList<>(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8));
        xCells = new ArrayList<>();
        oCells = new ArrayList<>();
        availCells = new ArrayList<>(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8));

        winSequence = new ArrayList<>();

        //All possible win Combos
        winSequence.add(new ArrayList<Integer>(Arrays.asList(0, 1, 2)));
        winSequence.add(new ArrayList<Integer>(Arrays.asList(3, 4, 5)));
        winSequence.add(new ArrayList<Integer>(Arrays.asList(6, 7, 8)));
        winSequence.add(new ArrayList<Integer>(Arrays.asList(0, 3, 6)));
        winSequence.add(new ArrayList<Integer>(Arrays.asList(1, 4, 7)));
        winSequence.add(new ArrayList<Integer>(Arrays.asList(2, 5, 8)));
        winSequence.add(new ArrayList<Integer>(Arrays.asList(0, 4, 8)));
        winSequence.add(new ArrayList<Integer>(Arrays.asList(2, 4, 6)));
    }

    //checks if the cell is available for insertion
    private boolean isEmptyCell(Integer cell) {
        return availCells.contains(cell);
    }

    //Adds cell to occupied by x
    public boolean addx(Integer cell) {

        if (isEmptyCell(cell)) {
            xCells.add(cell);
            availCells.remove(cell);
            return true;
        } else {
            return false;
        }

    }

    //Adds cell to occupied by o
    public boolean addo(Integer cell) {

        if (isEmptyCell(cell)) {
            oCells.add(cell);
            availCells.remove(cell);
            return true;
        } else {
            return false;
        }

    }

    //Compare with possible win combos
    boolean compareWinSequence(ArrayList<Integer> coll) {

        //Check if Winning
        for (ArrayList<Integer> seq : winSequence) {
            if (coll.containsAll(seq))
                return true;
        }
        return false;
    }

    //Check if someone wins
    public String checkWin(String chance) {
        if (chance.equalsIgnoreCase("X")) {
            if (compareWinSequence(xCells))
                return "Player1";
        } else if (chance.equalsIgnoreCase("O")) {
            if (compareWinSequence(oCells))
                return "Player2";
        }

        return "Draw";
    }

    public ArrayList<Integer> getWinningSequence(String winner) {
        if (winner.equalsIgnoreCase("X")) {
            for (ArrayList<Integer> seq : winSequence) {
                if (xCells.containsAll(seq))
                    return seq;
            }
        } else {
            for (ArrayList<Integer> seq : winSequence) {
                if (oCells.containsAll(seq))
                    return seq;
            }
        }
        return null;
    }

    public void reset() {
        xCells.clear();
        oCells.clear();
        availCells.clear();
        availCells.addAll(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8));
    }

}
