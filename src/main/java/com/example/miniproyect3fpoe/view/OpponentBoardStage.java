package com.example.miniproyect3fpoe.view;

import com.example.miniproyect3fpoe.controller.OpponentBoardController;
import com.example.miniproyect3fpoe.model.Board;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * The {@code OpponentBoardStage} class represents the window (stage) where the opponent's board is displayed.
 * It initializes the user interface and sets the board data to be shown.
 */
public class OpponentBoardStage extends Stage {

    private final OpponentBoardController controller;

    /**
     * Constructs a new {@code OpponentBoardStage} instance, loads the FXML file, and sets up the opponent's board.
     *
     * @param machineBoard the opponent's board to be displayed
     * @throws IOException if the FXML file cannot be loaded
     */
    public OpponentBoardStage(Board machineBoard) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/miniproyect3fpoe/opponent-board-view.fxml"));
        Parent root = loader.load();
        controller = loader.getController();
        controller.setMachineBoard(machineBoard);

        setScene(new Scene(root));
        setTitle("Machine's Board");
        setResizable(false);
    }

    /**
     * Returns the {@code OpponentBoardController} associated with this {@code OpponentBoardStage}.
     *
     * @return the {@code OpponentBoardController} instance managing the opponent's board logic
     */
    public OpponentBoardController getController() {
        return controller;
    }
}
