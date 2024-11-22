package com.example.miniproyect3fpoe;

import com.example.miniproyect3fpoe.view.WelcomeStage;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * The {@code Main} class serves as the entry point for the application.
 * It initializes and launches the JavaFX application.
 */
public class Main extends Application {

    /**
     * The main method of the application. Launches the JavaFX runtime.
     *
     * @param args command-line arguments passed during the program execution
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Starts the JavaFX application by displaying the welcome stage.
     *
     * @param primaryStage the primary stage provided by the JavaFX runtime (not used)
     * @throws IOException if there is an issue initializing the welcome stage
     */
    @Override
    public void start(Stage primaryStage) throws IOException {
        WelcomeStage.getInstance();
    }
}

