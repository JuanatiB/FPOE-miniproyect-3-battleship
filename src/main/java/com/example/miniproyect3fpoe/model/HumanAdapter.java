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
    public String playTurn(int row, int col, Board opponentBoard) {
        return opponentBoard.registerShot(row, col);
    }

    /**
     * Gets the list of ships of the human player.
     * @return The list of ships.
     */
    public List<Ship> getShips() {
        return ships;
    }

    /**
     * Returns the number of ships that have been sunk for the human player.
     * @return The count of sunk ships.
     */
    public int getSunkShipsCount() {
        return (int) getBoard().getShips().stream()
                .filter(Ship::isSunk)
                .count();
    }
}
