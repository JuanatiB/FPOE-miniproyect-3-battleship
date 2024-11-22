package com.example.miniproyect3fpoe.model;

import java.util.ArrayList;
import java.util.List;

public class Board {
    public static final int BOARD_SIZE = 10;
    private final char[][] grid;
    private final List<Ship> ships;

    private static final char EMPTY = '-';
    private static final char SHIP = 'S';
    private static final char HIT = 'X';
    private static final char MISS = 'O';

    /**
     * Constructs a new board with a predefined size and initializes it with empty cells.
     */
    public Board() {
        grid = new char[BOARD_SIZE][BOARD_SIZE];
        ships = new ArrayList<>();
        initializeGrid();
    }

    /**
     * Initializes the board grid with empty cells.
     */
    private void initializeGrid() {
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                grid[row][col] = EMPTY;
            }
        }
    }

    /**
     * Places a ship on the board if the placement is valid.
     *
     * @param ship     the ship to place
     * @param startRow the starting row for the ship
     * @param startCol the starting column for the ship
     * @return true if the ship is successfully placed, false otherwise
     */
    public boolean placeShip(Ship ship, int startRow, int startCol) {
        if (!isPlacementValid(ship, startRow, startCol)) {
            return false;
        }

        ship.setCoordinates(startRow, startCol);

        for (int[] coord : ship.getCoordinates()) {
            grid[coord[0]][coord[1]] = SHIP;
        }

        ships.add(ship);
        return true;
    }

    /**
     * Validates whether a ship can be placed at the specified position.
     *
     * @param ship     the ship to validate
     * @param startRow the starting row for the ship
     * @param startCol the starting column for the ship
     * @return true if the placement is valid, false otherwise
     */
    public boolean isPlacementValid(Ship ship, int startRow, int startCol) {
        for (int i = 0; i < ship.getSize(); i++) {
            int row = ship.isHorizontal() ? startRow : startRow + i;
            int col = ship.isHorizontal() ? startCol + i : startCol;

            if (row < 0 || row >= BOARD_SIZE || col < 0 || col >= BOARD_SIZE) {
                return false;
            }

            if (grid[row][col] != EMPTY) {
                return false;
            }
        }
        return true;
    }

    /**
     * Registers a shot on the board and determines the result.
     *
     * @param row the row of the shot
     * @param col the column of the shot
     * @return the result of the shot ("miss", "hit", "sunk", or "invalid")
     */
    public String registerShot(int row, int col) {
        if (row < 0 || row >= BOARD_SIZE || col < 0 || col >= BOARD_SIZE) {
            return "invalid";
        }

        if (grid[row][col] == MISS || grid[row][col] == HIT) {
            return "already shot";
        }

        if (grid[row][col] == SHIP) {
            grid[row][col] = HIT;

            for (Ship ship : ships) {
                if (ship.registerHit(row, col)) {
                    if (ship.isSunk()) {
                        return "sunk";
                    }
                    return "hit";
                }
            }
        }

        grid[row][col] = MISS;
        return "miss";
    }

    /**
     * Checks if all ships on the board have been sunk.
     *
     * @return true if all ships are sunk, false otherwise
     */
    public boolean isGameOver() {
        for (Ship ship : ships) {
            if (!ship.isSunk()) {
                return false;
            }
        }
        return true;
    }

    /**
     * Verifies if a specific cell is a hit.
     *
     * @param row the row to check
     * @param col the column to check
     * @return true if the cell is a hit, false otherwise
     */
    public boolean isHit(int row, int col) {
        if (row < 0 || row >= BOARD_SIZE || col < 0 || col >= BOARD_SIZE) {
            return false;
        }
        return grid[row][col] == HIT;
    }

    /**
     * Verifies if a specific cell is a miss.
     *
     * @param row the row to check
     * @param col the column to check
     * @return true if the cell is a miss, false otherwise
     */
    public boolean isMiss(int row, int col) {
        if (row < 0 || row >= BOARD_SIZE || col < 0 || col >= BOARD_SIZE) {
            return false;
        }
        return grid[row][col] == MISS;
    }

    /**
     * Prints the current state of the board.
     * Used for debugging purposes.
     */
    public void printBoard() {
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                System.out.print(grid[row][col] + " ");
            }
            System.out.println();
        }
    }

    /**
     * Gets the list of ships placed on the board.
     *
     * @return the list of ships
     */
    public List<Ship> getShips() {
        return ships;
    }

    /**
     * Resets the board by clearing all ships and reinitializing the grid.
     */
    public void reset() {
        initializeGrid();
        ships.clear();
    }

    /**
     * Verifies if a specific cell is occupied by a ship.
     *
     * @param row the row to check
     * @param col the column to check
     * @return true if the cell is occupied, false otherwise
     */
    public boolean occupiesCell(int row, int col) {
        return grid[row][col] == SHIP;
    }

    /**
     * Gets the current grid representing the board.
     *
     * @return the grid as a 2D character array
     */
    public char[][] getGrid() {
        return grid;
    }
}
