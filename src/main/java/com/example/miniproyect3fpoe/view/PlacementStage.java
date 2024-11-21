package com.example.miniproyect3fpoe.view;

import com.example.miniproyect3fpoe.controller.PlacementController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * The {@code GameStage} class represents the window (stage) where the game will be displayed.
 * It sets up the user interface, including loading the FXML file, configuring the controller,
 * and adjusting window properties such as size and icon.
 */
public class PlacementStage extends Stage {
    /**
     * The controller associated with the game view.
     */
    private final PlacementController placementController;

    /**
     * Creates a new {@code GameStage} instance, loads the FXML file, and sets up the UI properties.
     *
     * @throws IOException if the FXML file cannot be loaded.
     */
    public PlacementStage() throws IOException {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/com/example/miniproyect3fpoe/placement-view.fxml")
        );
        Parent root = loader.load();
        placementController = loader.getController();
        Scene scene = new Scene(root);
        setScene(scene);
        setTitle("Posicionamiento");
//        getIcons().add(new Image(String.valueOf(
//                getClass().getResource("/proyecto1/numero-1.png"))
//        ));
        setResizable(false);
        setWidth(700);
        setHeight(400);
        show();
    }

    /**
     * Returns the {@code GameController} associated with this {@code GameStage}.
     *
     * @return the {@code GameController} instance controlling the game logic.
     */
    public PlacementController getGameController() {
        return placementController;
    }

    /**
     * Nested static class that holds the singleton instance of {@code GameStage}.
     */
    private static class GameStageHolder {
        /**
         * Singleton instance of {@code GameStage}.
         */
        private static PlacementStage INSTANCE;
    }

    /**
     * Returns the singleton instance of {@code GameStage}, creating it if necessary.
     *
     * @return the singleton instance of {@code GameStage}.
     * @throws IOException if the FXML file cannot be loaded during instantiation.
     */
    public static PlacementStage getInstance() throws IOException {
        GameStageHolder.INSTANCE =
                GameStageHolder.INSTANCE != null ?
                        GameStageHolder.INSTANCE :
                        new PlacementStage();

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