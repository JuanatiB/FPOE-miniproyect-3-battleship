package com.example.miniproyect3fpoe.view;

import com.example.miniproyect3fpoe.controller.GameController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * The {@code GameStage} class represents the window (stage) where the game will be displayed.
 * It sets up the user interface, including loading the FXML file, configuring the controller,
 * and adjusting window properties such as size and icon.
 */
public class GameStage extends Stage {
    /**
     * The controller associated with the game view.
     */
    private final GameController gameController;

    /**
     * Creates a new {@code GameStage} instance, loads the FXML file, and sets up the UI properties.
     *
     * @throws IOException if the FXML file cannot be loaded.
     */
    public GameStage() throws IOException {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/com/example/miniproyect3fpoe/game-view.fxml")
        );
        Parent root = loader.load();
        gameController = loader.getController();
        Scene scene = new Scene(root);
        setScene(scene);
        setTitle("!Batalla Naval!");
        getIcons().add(new Image(String.valueOf(
                getClass().getResource("/com/example/miniproyect3fpoe/images/favicon.png"))
        ));
        setResizable(false);
        setWidth(1000);
        setHeight(570);
        show();
    }

    /**
     * Returns the {@code GameController} associated with this {@code GameStage}.
     *
     * @return the {@code GameController} instance controlling the game logic.
     */
    public GameController getGameController() {
        return gameController;
    }

    /**
     * Nested static class that holds the singleton instance of {@code GameStage}.
     */
    private static class GameStageHolder {
        /**
         * Singleton instance of {@code GameStage}.
         */
        private static GameStage INSTANCE;
    }

    /**
     * Returns the singleton instance of {@code GameStage}, creating it if necessary.
     *
     * @return the singleton instance of {@code GameStage}.
     * @throws IOException if the FXML file cannot be loaded during instantiation.
     */
    public static GameStage getInstance() throws IOException {
        GameStageHolder.INSTANCE =
                GameStageHolder.INSTANCE != null ?
                        GameStageHolder.INSTANCE :
                        new GameStage();

        return GameStageHolder.INSTANCE;
    }

    /**
     * Closes the current {@code GameStage} instance and resets the singleton to null.
     */
    public static void deleteInstance() {
        GameStageHolder.INSTANCE.close();
        GameStageHolder.INSTANCE = null;
    }
}
