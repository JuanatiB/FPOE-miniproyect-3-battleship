package com.example.miniproyect3fpoe.model;

import java.util.ArrayList;
import java.util.List;

public class Ship {
    private final String name; // Name of the ship (e.g., "Carrier", "Destroyer")
    private final int size; // Size of the ship (number of cells it occupies)
    private final boolean isHorizontal; // Orientation: true = horizontal, false = vertical
    private final List<int[]> coordinates; // List of occupied coordinates (row, column)
    private int hits; // Number of hits received

    /**
     * Constructor for the Ship class.
     * @param name Name of the ship.
     * @param size Size of the ship (number of cells it occupies).
     * @param isHorizontal Orientation of the ship (true = horizontal, false = vertical).
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
     * @param startRow Starting row.
     * @param startCol Starting column.
     * @return true if the coordinates are successfully set.
     */
    public boolean setCoordinates(int startRow, int startCol) {
        coordinates.clear(); // Clear any previously set coordinates
        for (int i = 0; i < size; i++) {
            int row = isHorizontal ? startRow : startRow + i;
            int col = isHorizontal ? startCol + i : startCol;
            coordinates.add(new int[]{row, col});
        }
        return true;
    }

    /**
     * Checks if the ship occupies a specific cell.
     * @param row Row to check.
     * @param col Column to check.
     * @return true if the ship occupies the cell, false otherwise.
     */
    public boolean occupiesCell(int row, int col) {
        return coordinates.stream().anyMatch(coord -> coord[0] == row && coord[1] == col);
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
     * @return true if the ship is sunk, false otherwise.
     */
    public boolean isSunk() {
        return hits >= size;
    }

    /**
     * Gets the name of the ship.
     * @return The name of the ship.
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the size of the ship.
     * @return The size of the ship.
     */
    public int getSize() {
        return size;
    }

    /**
     * Gets the orientation of the ship.
     * @return true if the ship is horizontal, false if it is vertical.
     */
    public boolean isHorizontal() {
        return isHorizontal;
    }

    /**
     * Gets the coordinates occupied by the ship.
     * @return List of coordinates (row, column) occupied by the ship.
     */
    public List<int[]> getCoordinates() {
        return coordinates;
    }

    /**
     * Gets the number of hits received by the ship.
     * @return The number of hits.
     */
    public int getHits() {
        return hits;
    }
}
