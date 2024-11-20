package com.example.miniproyect3fpoe.model;

import java.util.List;

public class HumanAdapter extends PlayerAdapter {

    public HumanAdapter(Board board) {
        super(board);
    }

    /**
     * Executes the human player's turn. The row and column for the attack
     * are provided by the controller.
     * @param row The row to attack.
     * @param col The column to attack.
     * @return A message indicating the result of the attack ("miss", "hit", or "sunk").
     */
    @Override
    public String playTurn(int row, int col) {
        return board.registerShot(row, col);
    }
}
