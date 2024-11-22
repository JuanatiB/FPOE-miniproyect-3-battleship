package com.example.miniproyect3fpoe.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a ship in the game. Tracks its name, size, orientation,
 * coordinates, and the number of hits it has received.
 */
public class Ship {
    private final String name;
    private final int size;
    private boolean isHorizontal;
    private final List<int[]> coordinates;
    private int hits;

    /**
     * Constructs a Ship instance with the specified name, size, and orientation.
     *
     * @param name         the name of the ship
     * @param size         the size of the ship (number of cells it occupies)
     * @param isHorizontal the orientation of the ship (true = horizontal, false = vertical)
     */
    public Ship(String name, int size, boolean isHorizontal) {
        this.name = name;
        this.size = size;
        this.isHorizontal = isHorizontal;
        this.coordinates = new ArrayList<>();
        this.hits = 0;
    }

    /**
     * Sets the coordinates of the ship based on its starting position and orientation.
     *
     * @param startRow the starting row
     * @param startCol the starting column
     * @return true if the coordinates are successfully set
     */
    public boolean setCoordinates(int startRow, int startCol) {
        coordinates.clear();
        for (int i = 0; i < size; i++) {
            int row = isHorizontal ? startRow : startRow + i;
            int col = isHorizontal ? startCol + i : startCol;
            coordinates.add(new int[]{row, col});
        }
        return true;
    }

    /**
     * Checks if the ship occupies a specific cell.
     *
     * @param row the row to check
     * @param col the column to check
     * @return true if the ship occupies the cell, false otherwise
     */
    public boolean occupiesCell(int row, int col) {
        return coordinates.stream().anyMatch(coord -> coord[0] == row && coord[1] == col);
    }

    /**
     * Registers a hit on the ship at the specified cell.
     *
     * @param row the row of the hit
     * @param col the column of the hit
     * @return true if the hit is successful, false otherwise
     */
    public boolean registerHit(int row, int col) {
        if (occupiesCell(row, col)) {
            hits++;
            return true;
        }
        return false;
    }

    /**
     * Sets the orientation of the ship.
     *
     * @param horizontal true if the ship is horizontal, false if it is vertical
     */
    public void setHorizontal(boolean horizontal) {
        this.isHorizontal = horizontal;
    }

    /**
     * Checks if the ship is sunk (all its cells have been hit).
     *
     * @return true if the ship is sunk, false otherwise
     */
    public boolean isSunk() {
        return hits >= size;
    }

    /**
     * Gets the name of the ship.
     *
     * @return the name of the ship
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the size of the ship.
     *
     * @return the size of the ship
     */
    public int getSize() {
        return size;
    }

    /**
     * Gets the orientation of the ship.
     *
     * @return true if the ship is horizontal, false if it is vertical
     */
    public boolean isHorizontal() {
        return isHorizontal;
    }

    /**
     * Gets the coordinates occupied by the ship.
     *
     * @return a list of coordinates (row, column) occupied by the ship
     */
    public List<int[]> getCoordinates() {
        return coordinates;
    }

    /**
     * Gets the number of hits the ship has received.
     *
     * @return the number of hits
     */
    public int getHits() {
        return hits;
    }
}
