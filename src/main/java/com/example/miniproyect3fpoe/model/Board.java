package com.example.miniproyect3fpoe.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Board implements Serializable {
    private static final long serialVersionUID = 1L; // Identificador único para la serialización

    private static final int BOARD_SIZE = 10; // Tamaño del tablero (10x10)
    private final char[][] grid; // Representación del tablero
    private final List<Ship> ships; // Lista de barcos en el tablero

    // Caracteres que representan el estado de cada celda
    private static final char EMPTY = '-'; // Celda vacía
    private static final char SHIP = 'S'; // Celda ocupada por un barco
    private static final char HIT = 'X'; // Celda golpeada
    private static final char MISS = 'O'; // Celda fallida

    /**
     * Constructor de la clase Board. Inicializa la cuadrícula y la lista de barcos.
     */
    public Board() {
        grid = new char[BOARD_SIZE][BOARD_SIZE];
        ships = new ArrayList<>();
        initializeGrid();
    }

    /**
     * Inicializa la cuadrícula con celdas vacías.
     */
    private void initializeGrid() {
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                grid[row][col] = EMPTY;
            }
        }
    }

    /**
     * Coloca un barco en el tablero si la posición es válida.
     * @param ship El barco a colocar.
     * @param startRow Fila inicial del barco.
     * @param startCol Columna inicial del barco.
     * @return true si el barco se colocó correctamente, false en caso contrario.
     */
    public boolean placeShip(Ship ship, int startRow, int startCol) {
        if (!isPlacementValid(ship, startRow, startCol)) {
            return false;
        }

        // Establecer las coordenadas del barco
        ship.setCoordinates(startRow, startCol);

        // Marcar la posición del barco en la cuadrícula
        for (int[] coord : ship.getCoordinates()) {
            grid[coord[0]][coord[1]] = SHIP;
        }

        ships.add(ship); // Agregar el barco a la lista
        return true;
    }

    /**
     * Verifica si la colocación de un barco es válida.
     * @param ship El barco a validar.
     * @param startRow Fila inicial.
     * @param startCol Columna inicial.
     * @return true si la colocación es válida, false en caso contrario.
     */
    public boolean isPlacementValid(Ship ship, int startRow, int startCol) {
        for (int i = 0; i < ship.getSize(); i++) {
            int row = ship.isHorizontal() ? startRow : startRow + i;
            int col = ship.isHorizontal() ? startCol + i : startCol;

            // Verificar límites
            if (row < 0 || row >= BOARD_SIZE || col < 0 || col >= BOARD_SIZE) {
                return false;
            }

            // Verificar si la celda ya está ocupada
            if (grid[row][col] != EMPTY) {
                return false;
            }
        }
        return true;
    }

    /**
     * Registra un disparo en el tablero y devuelve el resultado.
     * @param row Fila del disparo.
     * @param col Columna del disparo.
     * @return El resultado del disparo ("miss", "hit" o "sunk").
     */
    public String registerShot(int row, int col) {
        if (row < 0 || row >= BOARD_SIZE || col < 0 || col >= BOARD_SIZE) {
            return "invalid"; // Disparo inválido
        }

        if (grid[row][col] == MISS || grid[row][col] == HIT) {
            return "already shot"; // Celda ya disparada
        }

        if (grid[row][col] == SHIP) {
            grid[row][col] = HIT; // Marcar la celda como golpeada

            // Verificar qué barco fue golpeado
            for (Ship ship : ships) {
                if (ship.registerHit(row, col)) {
                    if (ship.isSunk()) {
                        return "sunk"; // El barco está completamente hundido
                    }
                    return "hit"; // El barco fue golpeado pero no hundido
                }
            }
        }

        // Si no se golpeó ningún barco, es un fallo
        grid[row][col] = MISS;
        return "miss";
    }

    /**
     * Copia el estado de otro tablero en este.
     * @param other El tablero desde el cual copiar el estado.
     */
    public void copyState(Board other) {
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                this.grid[row][col] = other.grid[row][col];
            }
        }
        this.ships.clear();
        for (Ship ship : other.getShips()) {
            Ship newShip = new Ship(ship.getName(), ship.getSize(), ship.isHorizontal());
            newShip.setCoordinates(ship.getCoordinates());
            this.ships.add(newShip);
        }
    }

    /**
     * Verifica si todos los barcos en el tablero han sido hundidos.
     * @return true si todos los barcos están hundidos, false en caso contrario.
     */
    public boolean isGameOver() {
        System.out.println("Checking if all ships are sunk...");
        for (Ship ship : ships) {
            if (!ship.isSunk()) {
                System.out.println("Ship " + ship.getName() + " is not sunk.");
                return false;
            }
        }
        System.out.println("All ships are sunk. Game over.");
        return true;
    }

    /**
     * Verifica si una celda específica está golpeada.
     * @param row Fila a verificar.
     * @param col Columna a verificar.
     * @return true si la celda está golpeada, false en caso contrario.
     */
    public boolean isHit(int row, int col) {
        if (row < 0 || row >= BOARD_SIZE || col < 0 || col >= BOARD_SIZE) {
            return false; // Fuera de límites
        }
        return grid[row][col] == HIT;
    }

    /**
     * Verifica si una celda específica es un fallo.
     * @param row Fila a verificar.
     * @param col Columna a verificar.
     * @return true si la celda es un fallo, false en caso contrario.
     */
    public boolean isMiss(int row, int col) {
        if (row < 0 || row >= BOARD_SIZE || col < 0 || col >= BOARD_SIZE) {
            return false; // Fuera de límites
        }
        return grid[row][col] == MISS;
    }

    /**
     * Imprime el estado actual del tablero (para propósitos de depuración).
     */
    public void printBoard() {
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                System.out.print(grid[row][col] + " ");
            }
            System.out.println();
        }
    }

    // Métodos auxiliares sin eliminar contenido
    public List<Ship> getShips() {
        return ships;
    }

    public void reset() {
        initializeGrid(); // Reinicia la cuadrícula con celdas vacías
        ships.clear();    // Elimina todos los barcos
    }

    public boolean occupiesCell(int row, int col) {
        return grid[row][col] == SHIP;
    }
}
