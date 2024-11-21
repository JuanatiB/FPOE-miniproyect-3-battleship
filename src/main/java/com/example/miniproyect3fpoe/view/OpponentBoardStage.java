package com.example.miniproyect3fpoe.view;

import com.example.miniproyect3fpoe.controller.OpponentBoardController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class OpponentBoardStage extends Stage {
        /**
     * The controller associated with the game view.
     */
    private final OpponentBoardController opponentBoardController;

    /**
     * Creates a new {@code GameStage} instance, loads the FXML file, and sets up the UI properties.
     *
     * @throws IOException if the FXML file cannot be loaded.
     */
    public OpponentBoardStage() throws IOException {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/com/example/miniproyect3fpoe/opponent-board-view.fxml")
        );
        Parent root = loader.load();
        opponentBoardController = loader.getController();
        Scene scene = new Scene(root);
        setScene(scene);
        setTitle("Opponent Board");
//        getIcons().add(new Image(String.valueOf(
//                getClass().getResource("/proyecto1/numero-1.png"))
//        ));
        setResizable(false);
        setWidth(800);
        setHeight(500);
        show();
    }

    /**
     * Returns the {@code GameController} associated with this {@code GameStage}.
     *
     * @return the {@code GameController} instance controlling the game logic.
     */
    public OpponentBoardController getOpponentBoardController() {
        return opponentBoardController;
    }

    /**
     * Nested static class that holds the singleton instance of {@code GameStage}.
     */
    private static class OpponentBoardStageHolder {
        /**
         * Singleton instance of {@code GameStage}.
         */
        private static OpponentBoardStage INSTANCE;
    }

    /**
     * Returns the singleton instance of {@code GameStage}, creating it if necessary.
     *
     * @return the singleton instance of {@code GameStage}.
     * @throws IOException if the FXML file cannot be loaded during instantiation.
     */
    public static OpponentBoardStage getInstance() throws IOException {
        OpponentBoardStage.OpponentBoardStageHolder.INSTANCE =
                OpponentBoardStage.OpponentBoardStageHolder.INSTANCE != null ?
                        com.example.miniproyect3fpoe.view.OpponentBoardStage.OpponentBoardStageHolder.INSTANCE :
                        new OpponentBoardStage();

        return OpponentBoardStageHolder.INSTANCE;
    }

    /**
     * Closes the current {@code GameStage} instance and resets the singleton to null.
     */
    public static void deleteInstance() {
        OpponentBoardStageHolder.INSTANCE.close();
        OpponentBoardStageHolder.INSTANCE = null;
    }
}
