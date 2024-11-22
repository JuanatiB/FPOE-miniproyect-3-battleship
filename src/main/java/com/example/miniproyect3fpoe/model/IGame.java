package com.example.miniproyect3fpoe.model;

/**
 * Interface defining the core methods for the game logic.
 * Provides methods to process shots, check victory conditions, and determine the winner.
 */
public interface IGame {

    /**
     * Processes the human player's shot at the specified coordinates.
     *
     * @param row the row of the shot
     * @param col the column of the shot
     * @return the result of the shot ("miss", "hit", "sunk", etc.)
     */
    String processHumanShot(int row, int col);

    /**
     * Processes the machine's shot at the specified coordinates.
     *
     * @param row the row of the shot
     * @param col the column of the shot
     * @return the result of the machine's shot ("miss", "hit", "sunk", etc.)
     */
    String processMachineShot(int row, int col);

    /**
     * Checks if the game has a winner by verifying if all ships of a player are sunk.
     *
     * @return true if there is a winner, false otherwise
     */
    boolean checkVictory();

    /**
     * Gets the winner of the game if there is one.
     *
     * @return the name or type of the winning player ("Human" or "Machine")
     */
    String getWinner();
}

