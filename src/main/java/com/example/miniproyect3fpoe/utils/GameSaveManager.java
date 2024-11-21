package com.example.miniproyect3fpoe.utils;

import com.example.miniproyect3fpoe.model.Board;

import java.io.*;

public class GameSaveManager {
    private static final String SAVE_FILE = "game_state.ser";
    private static final String PLAYER_FILE = "player_data.txt";

    // Save the game state to a file
    public static void saveGame(Board humanBoard, Board machineBoard, String nickname, int humanShipsSunk) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(SAVE_FILE))) {
            oos.writeObject(humanBoard);
            oos.writeObject(machineBoard);
            System.out.println("Game state saved successfully.");
        } catch (IOException e) {
            System.err.println("Error saving game state: " + e.getMessage());
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(PLAYER_FILE))) {
            writer.write(nickname + "\n");
            writer.write(humanShipsSunk + "\n");
            System.out.println("Player data saved successfully.");
        } catch (IOException e) {
            System.err.println("Error saving player data: " + e.getMessage());
        }
    }

    // Cargar el estado del juego
    public static Object[] loadGame() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(SAVE_FILE))) {
            Board humanBoard = (Board) ois.readObject();
            Board machineBoard = (Board) ois.readObject();
            System.out.println("Game state loaded successfully.");
            return new Object[]{humanBoard, machineBoard};
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading game state: " + e.getMessage());
        }
        return null;
    }

    // Cargar datos del jugador
    public static String[] loadPlayerData() {
        try (BufferedReader reader = new BufferedReader(new FileReader(PLAYER_FILE))) {
            String nickname = reader.readLine();
            int humanShipsSunk = Integer.parseInt(reader.readLine());
            System.out.println("Player data loaded successfully.");
            return new String[]{nickname, String.valueOf(humanShipsSunk)};
        } catch (IOException e) {
            System.err.println("Error loading player data: " + e.getMessage());
        }
        return null;
    }

    // Verificar si existe un archivo de guardado
    public static boolean hasSavedGame() {
        return new File(SAVE_FILE).exists() && new File(PLAYER_FILE).exists();
    }
}

