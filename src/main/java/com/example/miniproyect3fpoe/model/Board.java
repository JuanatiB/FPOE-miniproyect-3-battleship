package com.example.miniproyect3fpoe.model;

import java.util.ArrayList;
import java.util.List;

public class Board {
    private static final int BOARD_SIZE = 10; // Size of the board (10x10 grid)
    private final char[][] grid; // Grid representing the board
    private final List<Ship> ships; // List of ships placed on the board

    // Characters representing the state of each cell
    private static final char EMPTY = '-'; // Empty cell
    private static final char SHIP = 'S'; // Cell occupied by a ship
    private static final char HIT = 'X'; // Cell hit successfully
    private static final char MISS = 'O'; // Cell hit unsuccessfully

    /**
     * Constructor for the Board class. Initializes the grid and the list of ships.
     */
    public Board() {
        grid = new char[BOARD_SIZE][BOARD_SIZE];
        ships = new ArrayList<>();
        initializeGrid();
    }

    /**
     * Initializes the grid with empty cells.
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
     * @param ship The ship to place.
     * @param startRow Starting row for the ship.
     * @param startCol Starting column for the ship.
     * @return true if the ship was placed successfully, false otherwise.
     */
    public boolean placeShip(Ship ship, int startRow, int startCol) {
        if (!isPlacementValid(ship, startRow, startCol)) {
            return false;
        }

        // Set the coordinates of the ship
        ship.setCoordinates(startRow, startCol);

        // Mark the ship's position on the grid
        for (int[] coord : ship.getCoordinates()) {
            grid[coord[0]][coord[1]] = SHIP;
        }

        ships.add(ship); // Add the ship to the list
        return true;
    }

    /**
     * Checks if the placement of a ship is valid.
     * @param ship The ship to validate.
     * @param startRow Starting row for the ship.
     * @param startCol Starting column for the ship.
     * @return true if the placement is valid, false otherwise.
     */
    public boolean isPlacementValid(Ship ship, int startRow, int startCol) {
        for (int i = 0; i < ship.getSize(); i++) {
            int row = ship.isHorizontal() ? startRow : startRow + i;
            int col = ship.isHorizontal() ? startCol + i : startCol;

            // Check bounds
            if (row < 0 || row >= BOARD_SIZE || col < 0 || col >= BOARD_SIZE) {
                return false;
            }

            // Check if the cell is already occupied
            if (grid[row][col] != EMPTY) {
                return false;
            }
        }
        return true;
    }

    /**
     * Registers a shot on the board and returns the result.
     * @param row Row of the shot.
     * @param col Column of the shot.
     * @return The result of the shot ("miss", "hit", or "sunk").
     */
    public String registerShot(int row, int col) {
        if (row < 0 || row >= BOARD_SIZE || col < 0 || col >= BOARD_SIZE) {
            return "invalid"; // Invalid shot
        }

        if (grid[row][col] == MISS || grid[row][col] == HIT) {
            return "already shot"; // Cell already shot
        }

        if (grid[row][col] == SHIP) {
            grid[row][col] = HIT; // Mark the cell as hit

            // Check which ship was hit
            for (Ship ship : ships) {
                if (ship.registerHit(row, col)) {
                    if (ship.isSunk()) {
                        return "sunk"; // The ship is completely sunk
                    }
                    return "hit"; // The ship was hit but not sunk
                }
            }
        }

        // If no ship is hit, it's a miss
        grid[row][col] = MISS;
        return "miss";
    }

    /**
     * Checks if all ships on the board have been sunk.
     * @return true if all ships are sunk, false otherwise.
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
     * Prints the current state of the board (for debugging purposes).
     */
    public void printBoard() {
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                System.out.print(grid[row][col] + " ");
            }
            System.out.println();
        }
    }

    // Getter for the list of ships
    public List<Ship> getShips() {
        return ships;
    }

    public void reset() {
        initializeGrid(); // Vuelve a llenar la matriz con celdas vacías
        ships.clear();    // Elimina todos los barcos
    }

    /**
     * Verifica si una celda específica está ocupada por un barco.
     *
     * @param row La fila a verificar.
     * @param col La columna a verificar.
     * @return true si la celda está ocupada, false en caso contrario.
     */
    public boolean occupiesCell(int row, int col) {
        return grid[row][col] == SHIP; // Compara con el carácter que representa un barco
    }



}
