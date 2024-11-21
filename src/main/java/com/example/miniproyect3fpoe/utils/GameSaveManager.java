package com.example.miniproyect3fpoe.utils;

import java.io.*;

public class GameSaveManager {
    private static final String SAVE_FILE = "game_state.ser";

    public static void saveGame(Object gameState) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(SAVE_FILE))) {
            oos.writeObject(gameState);
        }
    }

    public static Object loadGame() throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(SAVE_FILE))) {
            return ois.readObject();
        }
    }

    public static boolean saveExists() {
        return new File(SAVE_FILE).exists();
    }
}
