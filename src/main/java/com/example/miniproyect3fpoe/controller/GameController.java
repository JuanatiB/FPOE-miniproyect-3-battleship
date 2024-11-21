package com.example.miniproyect3fpoe.controller;

import com.example.miniproyect3fpoe.model.Board;
import com.example.miniproyect3fpoe.model.Game;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.geometry.Pos;

import java.io.IOException;

public class GameController {
    @FXML
    private GridPane ownBoardGrid; // Asignado al tablero del jugador humano
    private Game game;

    @FXML
    private GridPane machineBoardGrid; // Referencia al tablero del oponente en el FXML

    public void setGame(Game game) {
        this.game = game;
    }

    public Game getGame() {
        return game;
    }

    @FXML
    public void handleViewMachineBoard(ActionEvent actionEvent) {
        try {
            // Cargar el archivo FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/miniproyect3fpoe/machine-board-view.fxml"));
            Parent root = loader.load();

            // Configurar un nuevo Stage
            Stage stage = new Stage();
            stage.setTitle("Tablero de Posición del Oponente");
            stage.setScene(new Scene(root));

            // Obtener el GridPane del FXML
            GridPane machineBoardGrid = (GridPane) root.lookup("#machineBoardGrid");

            // Poblar el tablero con los datos del oponente
            Board machineBoard = game.machine.getBoard();
            populateMachineBoard(machineBoard, machineBoardGrid);

            // Mostrar la ventana
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error al cargar la vista del tablero del oponente.");
        }
    }

    private void populateMachineBoard(Board machineBoard, GridPane gridPane) {
        for (int row = 0; row < 10; row++) {
            for (int col = 0; col < 10; col++) {
                Label cell = new Label();
                cell.setMinSize(30, 30); // Tamaño de las celdas
                cell.setAlignment(Pos.CENTER);

                // Establecer estilo y contenido de la celda
                if (machineBoard.occupiesCell(row, col)) {
                    cell.setStyle("-fx-background-color: lightgray; -fx-border-color: black;");
                    cell.setText("B"); // Indica que hay un barco
                } else {
                    cell.setStyle("-fx-background-color: white; -fx-border-color: black;");
                }

                gridPane.add(cell, col, row);
            }
        }
    }

    @FXML
    public void handleCloseMachineBoard(ActionEvent actionEvent) {
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }
}
