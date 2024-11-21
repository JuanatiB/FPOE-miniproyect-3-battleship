package com.example.miniproyect3fpoe;

import com.example.miniproyect3fpoe.model.Board;
import com.example.miniproyect3fpoe.model.Ship;

import java.io.*;

public class SerializationTest {
    public static void main(String[] args) {
        String filePath = "test_board.ser"; // Archivo para guardar el objeto

        // Crear un objeto Board con un Ship
        Board board = new Board();
        Ship ship = new Ship("Submarine", 3, true); // Crear un barco
        ship.setCoordinates(2, 3); // Establecer coordenadas
        board.placeShip(ship, 2, 3); // Colocar el barco en el tablero

        // Mostrar el estado inicial del tablero
        System.out.println("Estado inicial del tablero:");
        board.printBoard();

        // Serializar el objeto Board
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(board);
            System.out.println("\nEl tablero se ha serializado correctamente en el archivo: " + filePath);
        } catch (IOException e) {
            System.err.println("Error al serializar el tablero: " + e.getMessage());
        }

        // Deserializar el objeto Board
        Board deserializedBoard = null;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            deserializedBoard = (Board) ois.readObject();
            System.out.println("\nEl tablero se ha deserializado correctamente desde el archivo.");
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error al deserializar el tablero: " + e.getMessage());
        }

        // Mostrar el estado del tablero deserializado
        if (deserializedBoard != null) {
            System.out.println("\nEstado del tablero deserializado:");
            deserializedBoard.printBoard();

            // Verificación adicional
            System.out.println("\n¿Los datos coinciden?");
            System.out.println(board.getShips().size() == deserializedBoard.getShips().size()
                    ? "¡Los datos coinciden! La serialización es correcta."
                    : "Los datos no coinciden. Hay un problema con la serialización.");
        }
    }
}
