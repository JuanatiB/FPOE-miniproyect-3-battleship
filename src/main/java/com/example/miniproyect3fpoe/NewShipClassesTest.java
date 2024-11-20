package com.example.miniproyect3fpoe;

import com.example.miniproyect3fpoe.model.Board;
import com.example.miniproyect3fpoe.model.Destroyer;
import com.example.miniproyect3fpoe.model.Submarine;
import com.example.miniproyect3fpoe.model.Frigate;
import com.example.miniproyect3fpoe.model.Carrier;

public class NewShipClassesTest {
    public static void main(String[] args) {
        // Crear el tablero
        Board board = new Board();

        // Crear barcos específicos
        Destroyer destroyer = new Destroyer(true);
        Submarine submarine = new Submarine(false);
        Frigate frigate = new Frigate(true);
        Carrier carrier = new Carrier(false);

        System.out.println("=== Placing Ships ===");

        // Colocar barcos en el tablero
        System.out.println("Placing Destroyer at (0, 0): " + board.placeShip(destroyer, 0, 0));
        System.out.println("Placing Submarine at (2, 2): " + board.placeShip(submarine, 2, 2));
        System.out.println("Placing Frigate at (5, 5): " + board.placeShip(frigate, 5, 5));
        System.out.println("Placing Carrier at (6, 1): " + board.placeShip(carrier, 6, 1));

        // Imprimir el tablero después de colocar los barcos
        board.printBoard();

        System.out.println("\n=== Shooting Phase ===");

        // Probar disparos
        System.out.println("Firing at (0, 0): " + board.registerShot(0, 0)); // hit
        System.out.println("Firing at (0, 1): " + board.registerShot(0, 1)); // sunk (Destroyer)
        System.out.println("Firing at (2, 2): " + board.registerShot(2, 2)); // hit
        System.out.println("Firing at (3, 2): " + board.registerShot(3, 2)); // hit
        System.out.println("Firing at (4, 2): " + board.registerShot(4, 2)); // sunk (Submarine)
        System.out.println("Firing at (5, 5): " + board.registerShot(5, 5)); // sunk (Frigate)
        System.out.println("Firing at (7, 1): " + board.registerShot(7, 1)); // hit
        System.out.println("Firing at (8, 1): " + board.registerShot(8, 1)); // hit
        System.out.println("Firing at (9, 1): " + board.registerShot(9, 1)); // sunk (Carrier)
        System.out.println("Firing at (0, 2): " + board.registerShot(0, 2)); // miss

        // Verificar si el juego ha terminado
        System.out.println("\n=== Final Game State ===");
        System.out.println("Is the game over? " + board.isGameOver());

        // Imprimir tablero final
        board.printBoard();
    }
}

