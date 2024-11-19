package com.example.miniproyect3fpoe;

import com.example.miniproyect3fpoe.model.Board;
import com.example.miniproyect3fpoe.model.Ship;

public class BoardShipConsoleTest {
    public static void main(String[] args) {
        // Crear el tablero
        Board board = new Board();
        System.out.println("Initial Board:");
        board.printBoard();

        // Crear barcos
        Ship destroyer = new Ship("Destroyer", 2, true); // Horizontal inicialmente
        Ship submarine = new Ship("Submarine", 3, false); // Vertical inicialmente

        // Cambiar orientación del Destroyer antes de colocarlo
        System.out.println("\nToggling orientation of Destroyer...");
        destroyer.toggleOrientation();
        System.out.println("Destroyer orientation: " + (destroyer.isHorizontal() ? "Horizontal" : "Vertical"));

        // Colocar el Destroyer en el tablero
        System.out.println("\nPlacing Destroyer at (0, 0):");
        boolean destroyerPlaced = board.placeShip(destroyer, 0, 0);
        System.out.println("Destroyer placed: " + destroyerPlaced);
        board.printBoard();

        // Intentar colocar el Submarine en la misma posición (debe fallar por superposición)
        System.out.println("\nPlacing Submarine at (0, 0):");
        boolean submarinePlaced = board.placeShip(submarine, 0, 0);
        System.out.println("Submarine placed: " + submarinePlaced);
        board.printBoard();

        // Cambiar orientación del Submarine y colocarlo en una posición válida
        System.out.println("\nToggling orientation of Submarine...");
        submarine.toggleOrientation();
        System.out.println("Submarine orientation: " + (submarine.isHorizontal() ? "Horizontal" : "Vertical"));

        System.out.println("\nPlacing Submarine at (2, 2):");
        submarinePlaced = board.placeShip(submarine, 2, 2);
        System.out.println("Submarine placed: " + submarinePlaced);
        board.printBoard();

        // Registrar disparos
        System.out.println("\nFiring at (5, 5):");
        String shotResult = board.registerShot(5, 5);
        System.out.println("Result of shot: " + shotResult);
        board.printBoard();

        System.out.println("\nFiring at (0, 0):");
        shotResult = board.registerShot(0, 0);
        System.out.println("Result of shot: " + shotResult);
        board.printBoard();

        System.out.println("\nFiring at (0, 1):");
        shotResult = board.registerShot(0, 1);
        System.out.println("Result of shot: " + shotResult);
        board.printBoard();

        // Disparar al Submarine hasta hundirlo
        System.out.println("\nFiring at (2, 2):");
        shotResult = board.registerShot(2, 2);
        System.out.println("Result of shot: " + shotResult);
        board.printBoard();

        System.out.println("\nFiring at (3, 2):");
        shotResult = board.registerShot(3, 2);
        System.out.println("Result of shot: " + shotResult);
        board.printBoard();

        System.out.println("\nFiring at (4, 2):");
        shotResult = board.registerShot(4, 2);
        System.out.println("Result of shot: " + shotResult);
        board.printBoard();

        // Verificar si el juego ha terminado
        System.out.println("\nIs the game over? " + board.isGameOver());
    }
}
