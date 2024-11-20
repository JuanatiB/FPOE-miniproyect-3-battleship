package com.example.miniproyect3fpoe.model;

import java.util.List;

public interface IPlayer {

    /**
     * Executes the player's turn by targeting the opponent's board.
     * @return A message indicating the result of the shot ("miss", "hit", or "sunk").
     */
    String playTurn(int row, int col);

    /**
     * Checks if the player has lost the game.
     * @return true if all ships are sunk, false otherwise.
     */
    boolean hasLost();

    /**
     * Gets the player's board.
     * @return The board associated with the player.
     */
    Board getBoard();
}
