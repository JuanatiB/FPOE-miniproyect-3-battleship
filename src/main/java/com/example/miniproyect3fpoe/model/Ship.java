package com.example.miniproyect3fpoe.model;

import java.util.ArrayList;
import java.util.List;

public class Ship {
    private final String name; // Name of the ship
    private final int size; // Size of the ship (number of cells it occupies)
    private boolean isHorizontal; // Orientation: true = horizontal, false = vertical
    private final List<int[]> coordinates; // List of occupied coordinates (row, column)
    private int hits; // Number of hits received
    private boolean isPlaced; // Indicates if the ship has been placed on the board

    /**
     * Constructor for the Ship class.
     * @param name Name of the ship.
     * @param size Size of the ship.
     * @param isHorizontal Initial orientation of the ship (true = horizontal, false = vertical).
     */
    public Ship(String name, int size, boolean isHorizontal) {
        this.name = name;
        this.size = size;
        this.isHorizontal = isHorizontal;
        this.coordinates = new ArrayList<>();
        this.hits = 0;
        this.isPlaced = false; // Initially, the ship is not placed
    }

    /**
     * Sets the coordinates of the ship and marks it as placed.
     * @param startRow Starting row.
     * @param startCol Starting column.
     * @return true if the coordinates are successfully set.
     */
    public boolean setCoordinates(int startRow, int startCol) {
        if (isPlaced) {
            return false; // Cannot set coordinates if the ship is already placed
        }

        coordinates.clear(); // Clear any previous coordinates
        for (int i = 0; i < size; i++) {
            int row = isHorizontal ? startRow : startRow + i;
            int col = isHorizontal ? startCol + i : startCol;
            coordinates.add(new int[]{row, col});
        }
        isPlaced = true; // Mark the ship as placed
        return true;
    }

    /**
     * Toggles the orientation of the ship between horizontal and vertical.
     * This can only be done if the ship has not been placed.
     * @return true if the orientation was successfully toggled, false otherwise.
     */
    public boolean toggleOrientation() {
        if (isPlaced) {
            return false; // Cannot toggle orientation if the ship is already placed
        }
        this.isHorizontal = !this.isHorizontal;
        return true;
    }

    /**
     * Checks if the ship occupies a specific cell.
     * @param row Row to check.
     * @param col Column to check.
     * @return true if the ship occupies the cell.
     */
    public boolean occupiesCell(int row, int col) {
        for (int[] coord : coordinates) {
            if (coord[0] == row && coord[1] == col) {
                return true;
            }
        }
        return false;
    }


    /**
     * Registers a hit on the ship at the specified cell.
     * @param row Row of the hit.
     * @param col Column of the hit.
     * @return true if the hit is successful, false otherwise.
     */
    public boolean registerHit(int row, int col) {
        if (occupiesCell(row, col)) {
            hits++;
            return true;
        }
        return false;
    }

    /**
     * Checks if the ship is sunk (all cells are hit).
     * @return true if the ship is sunk.
     */
    public boolean isSunk() {
        return hits >= size;
    }

    // Getters
    public String getName() {
        return name;
    }

    public int getSize() {
        return size;
    }

    public boolean isHorizontal() {
        return isHorizontal;
    }

    public boolean isPlaced() {
        return isPlaced;
    }

    public List<int[]> getCoordinates() {
        return coordinates;
    }

    public int getHits() {
        return hits;
    }
}
