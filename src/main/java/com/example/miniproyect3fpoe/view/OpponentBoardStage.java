package com.example.miniproyect3fpoe.view;

import com.example.miniproyect3fpoe.controller.OpponentBoardController;
import com.example.miniproyect3fpoe.model.Board;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class OpponentBoardStage extends Stage {

    private final OpponentBoardController controller;

    /**
     * Constructor de OpponentBoardStage.
     * @param machineBoard Tablero de la máquina a mostrar.
     * @throws IOException Si no se puede cargar el archivo FXML.
     */
    public OpponentBoardStage(Board machineBoard) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/miniproyect3fpoe/opponent-board-view.fxml"));
        Parent root = loader.load();
        controller = loader.getController();
        controller.setMachineBoard(machineBoard);

        setScene(new Scene(root));
        setTitle("Tablero de la Máquina");
        setResizable(false);
    }

    /**
     * Obtiene el controlador asociado al OpponentBoardStage.
     * @return OpponentBoardController.
     */
    public OpponentBoardController getController() {
        return controller;
    }
}
