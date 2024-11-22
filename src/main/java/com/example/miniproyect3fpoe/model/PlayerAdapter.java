package com.example.miniproyect3fpoe.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a generic player in the game. Provides basic methods
 * and properties for handling the player's board and ships.
 */
public class PlayerAdapter implements IPlayer {
    protected Board board;
    protected List<Ship> ships;

    /**
     * Initializes the player with a board and a predefined list of ships.
     *
     * @param board the board associated with the player
     */
    public PlayerAdapter(Board board) {
        this.board = board;
        this.ships = board.getShips();
        this.ships = initializeShips();
    }

    /**
     * Initializes the list of ships for the player.
     *
     * @return a list of ships with predefined sizes and names
     */
    private List<Ship> initializeShips() {
        List<Ship> ships = new ArrayList<>();
        ships.add(new Ship("Aircraft Carrier", 4, true));
        ships.add(new Ship("Submarine 1", 3, true));
        ships.add(new Ship("Submarine 2", 3, true));
        ships.add(new Ship("Destroyer 1", 2, true));
        ships.add(new Ship("Destroyer 2", 2, true));
        ships.add(new Ship("Destroyer 3", 2, true));
        ships.add(new Ship("Frigate 1", 1, true));
        ships.add(new Ship("Frigate 2", 1, true));
        ships.add(new Ship("Frigate 3", 1, true));
        ships.add(new Ship("Frigate 4", 1, true));
        return ships;
    }

    /**
     * Executes the player's turn by attacking a specified cell on the opponent's board.
     * This method provides a default implementation and should be overridden by subclasses.
     *
     * @param row           the row to attack
     * @param col           the column to attack
     * @param opponentBoard the opponent's board to attack
     * @return a message indicating the result of the action
     */
    @Override
    public String playTurn(int row, int col, Board opponentBoard) {
        return "Invalid action";
    }

    /**
     * Checks if the player has lost the game by verifying if all their ships are sunk.
     *
     * @return true if all ships are sunk, false otherwise
     */
    @Override
    public boolean hasLost() {
        return board.isGameOver();
    }

    /**
     * Retrieves the board associated with the player.
     *
     * @return the player's board
     */
    @Override
    public Board getBoard() {
        return board;
    }
}
