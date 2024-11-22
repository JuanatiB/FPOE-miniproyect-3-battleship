package com.example.miniproyect3fpoe.model;

import java.util.List;

/**
 * Represents a human player in the game. Provides methods to handle the player's actions.
 */
public class HumanAdapter extends PlayerAdapter {

    /**
     * Constructs a HumanAdapter with the specified board.
     *
     * @param board the board associated with the human player
     */
    public HumanAdapter(Board board) {
        super(board);
    }

    /**
     * Executes the human player's turn by attacking the specified cell on the opponent's board.
     *
     * @param row           the row to attack
     * @param col           the column to attack
     * @param opponentBoard the opponent's board to attack
     * @return a message indicating the result of the attack ("miss", "hit", or "sunk")
     */
    @Override
    public String playTurn(int row, int col, Board opponentBoard) {
        return opponentBoard.registerShot(row, col);
    }

    /**
     * Retrieves the list of ships belonging to the human player.
     *
     * @return the list of ships
     */
    public List<Ship> getShips() {
        return ships;
    }
}
