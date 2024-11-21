package com.example.miniproyect3fpoe.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Ship implements Serializable {
    private static final long serialVersionUID = 1L; // Identificador único para la serialización

    private final String name; // Nombre del barco (e.g., "Carrier", "Destroyer")
    private final int size; // Tamaño del barco (cantidad de celdas que ocupa)
    private boolean isHorizontal; // Orientación: true = horizontal, false = vertical
    private final List<int[]> coordinates; // Lista de coordenadas ocupadas (fila, columna)
    private int hits; // Número de golpes recibidos

    /**
     * Constructor para la clase Ship.
     * @param name Nombre del barco.
     * @param size Tamaño del barco (número de celdas que ocupa).
     * @param isHorizontal Orientación del barco (true = horizontal, false = vertical).
     */
    public Ship(String name, int size, boolean isHorizontal) {
        this.name = name;
        this.size = size;
        this.isHorizontal = isHorizontal;
        this.coordinates = new ArrayList<>();
        this.hits = 0;
    }

    /**
     * Establece las coordenadas del barco basado en su posición inicial y orientación.
     * @param startRow Fila inicial.
     * @param startCol Columna inicial.
     * @return true si las coordenadas se establecen con éxito.
     */
    public boolean setCoordinates(int startRow, int startCol) {
        coordinates.clear(); // Limpia cualquier coordenada previamente establecida
        for (int i = 0; i < size; i++) {
            int row = isHorizontal ? startRow : startRow + i;
            int col = isHorizontal ? startCol + i : startCol;
            coordinates.add(new int[]{row, col});
        }
        return true;
    }

    /**
     * Establece directamente una lista de coordenadas para el barco.
     * @param newCoordinates Lista de coordenadas a establecer.
     */
    public void setCoordinates(List<int[]> newCoordinates) {
        coordinates.clear();
        coordinates.addAll(newCoordinates);
    }

    /**
     * Verifica si el barco ocupa una celda específica.
     * @param row Fila a verificar.
     * @param col Columna a verificar.
     * @return true si el barco ocupa la celda, false en caso contrario.
     */
    public boolean occupiesCell(int row, int col) {
        return coordinates.stream().anyMatch(coord -> coord[0] == row && coord[1] == col);
    }

    /**
     * Registra un golpe en el barco en una celda específica.
     * @param row Fila del golpe.
     * @param col Columna del golpe.
     * @return true si el golpe es exitoso, false en caso contrario.
     */
    public boolean registerHit(int row, int col) {
        if (occupiesCell(row, col)) {
            hits++;
            return true;
        }
        return false;
    }

    /**
     * Establece la orientación del barco.
     * @param horizontal true si el barco es horizontal, false si es vertical.
     */
    public void setHorizontal(boolean horizontal) {
        this.isHorizontal = horizontal;
    }

    /**
     * Verifica si el barco está hundido (todas las celdas han sido golpeadas).
     * @return true si el barco está hundido, false en caso contrario.
     */
    public boolean isSunk() {
        return hits >= size;
    }

    /**
     * Devuelve el nombre del barco.
     * @return El nombre del barco.
     */
    public String getName() {
        return name;
    }

    /**
     * Devuelve el tamaño del barco.
     * @return El tamaño del barco.
     */
    public int getSize() {
        return size;
    }

    /**
     * Devuelve la orientación del barco.
     * @return true si el barco es horizontal, false si es vertical.
     */
    public boolean isHorizontal() {
        return isHorizontal;
    }

    /**
     * Devuelve las coordenadas ocupadas por el barco.
     * @return Lista de coordenadas (fila, columna) ocupadas por el barco.
     */
    public List<int[]> getCoordinates() {
        return coordinates;
    }

    /**
     * Devuelve la cantidad de golpes recibidos por el barco.
     * @return Número de golpes recibidos.
     */
    public int getHits() {
        return hits;
    }
}
