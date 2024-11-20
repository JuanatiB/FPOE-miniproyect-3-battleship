package com.example.miniproyect3fpoe.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * This class represents the welcome stage (window) of the application.
 * It extends {@link javafx.stage.Stage} and loads the welcome-view layout from an FXML file.
 * The class follows a singleton pattern to ensure only one instance of the stage is created.
 */
public class WelcomeStage extends Stage {

    /**
     * Constructs the WelcomeStage and initializes the window by loading the FXML layout
     * and setting up the scene, title, icon, and window properties.
     *
     * @throws IOException If there is an issue loading the FXML file.
     */
    public WelcomeStage() throws IOException {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/com/example/miniproyect3fpoe/welcome-view.fxml")
        );
        Parent root = loader.load();
        Scene scene = new Scene(root);
        setScene(scene);
        setTitle("Miniproyecto 3 Batalla naval");
        getIcons().add(new Image(String.valueOf(
                getClass().getResource("/com/example/miniproyect3fpoe/images/favicon.png"))
        ));
        setResizable(false);
        show();
    }

    /**
     * A private static inner class responsible for holding the singleton instance of {@link WelcomeStage}.
     * This ensures lazy initialization.
     */
    private static class WelcomeStageHolder {
        private static WelcomeStage INSTANCE;
    }

    /**
     * Provides access to the singleton instance of {@link WelcomeStage}.
     * If the instance does not already exist, it is created.
     *
     * @throws IOException If there is an issue creating or loading the stage.
     */
    public static void getInstance() throws IOException {
        WelcomeStageHolder.INSTANCE =
                WelcomeStageHolder.INSTANCE != null ?
                        WelcomeStageHolder.INSTANCE :
                        new WelcomeStage();

    }

    /**
     * Deletes the singleton instance of {@link WelcomeStage} and closes the window.
     * After calling this method, a new instance can be created.
     */
    public static void deleteInstance() {
        WelcomeStageHolder.INSTANCE.close();
        WelcomeStageHolder.INSTANCE = null;
    }
}

