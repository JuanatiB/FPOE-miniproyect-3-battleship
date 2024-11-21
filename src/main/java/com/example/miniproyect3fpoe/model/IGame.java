package com.example.miniproyect3fpoe.model;

public interface IGame {
    /**
     * Processes the human player's shot at the specified coordinates.
     * @param row The row of the shot.
     * @param col The column of the shot.
     * @return The result of the shot ("miss", "hit", "sunk", etc.).
     */
    String processHumanShot(int row, int col);

    /**
     * Processes the machine's shot at the specified coordinates.
     * @param row The row of the shot.
     * @param col The column of the shot.
     * @return The result of the machine's shot ("miss", "hit", "sunk", etc.).
     */
    String processMachineShot(int row, int col);

    /**
     * Checks if the game has a winner by verifying if all ships of a player are sunk.
     * @return true if there is a winner, false otherwise.
     */
    boolean checkVictory();

    /**
     * Gets the winner of the game if there is one.
     * @return The name or type of the winning player ("Human" or "Machine").
     */
    String getWinner();

}