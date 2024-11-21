package com.example.miniproyect3fpoe.model;

import java.util.ArrayList;
import java.util.List;

public class PlayerAdapter implements IPlayer {
    protected Board board;
    protected List<Ship> ships;

    /**
     * Initializes the list of ships for the player.
     * @return A list of ships with predefined sizes and names.
     */
    private List<Ship> initializeShips() {
        List<Ship> ships = new ArrayList<>();
        ships.add(new Ship("Aircraft Carrier", 4, true)); // Portaaviones
        ships.add(new Ship("Submarine 1", 3, true)); // Submarino 1
        ships.add(new Ship("Submarine 2", 3, true)); // Submarino 2
        ships.add(new Ship("Destroyer 1", 2, true)); // Destructor 1
        ships.add(new Ship("Destroyer 2", 2, true)); // Destructor 2
        ships.add(new Ship("Destroyer 3", 2, true)); // Destructor 3
        ships.add(new Ship("Frigate 1", 1, true)); // Fragata 1
        ships.add(new Ship("Frigate 2", 1, true)); // Fragata 2
        ships.add(new Ship("Frigate 3", 1, true)); // Fragata 3
        ships.add(new Ship("Frigate 4", 1, true)); // Fragata 4
        return ships;
    }

    public PlayerAdapter(Board board) {
        this.board = board;
        this.ships = board.getShips();
        this.ships = initializeShips();
    }

    @Override
    public String playTurn(int row, int col, Board opponentBoard) {
        // Default implementation (to be overridden by subclasses)
        return "Invalid action";
    }

    @Override
    public boolean hasLost() {
        return board.isGameOver();
    }

    @Override
    public Board getBoard() {
        return board;
    }
}