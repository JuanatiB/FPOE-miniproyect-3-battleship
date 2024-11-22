package com.example.miniproyect3fpoe.controller;

import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import com.example.miniproyect3fpoe.model.Board;

public class OpponentBoardController {

    @FXML
    private GridPane opponentBoardGrid;

    private Board machineBoard;

    /**
     * Configura el tablero de la máquina en el controlador.
     * @param board Tablero de la máquina.
     */
    public void setMachineBoard(Board board) {
        this.machineBoard = board;
        updateBoard();
    }

    /**
     * Actualiza la vista del tablero del oponente.
     */
    /**
     * Actualiza la vista del tablero del oponente.
     */
    private void updateBoard() {
        opponentBoardGrid.getChildren().clear();

        for (int row = 0; row < Board.BOARD_SIZE; row++) {
            for (int col = 0; col < Board.BOARD_SIZE; col++) {
                Rectangle cell = new Rectangle(30, 30);
                cell.setStroke(Color.BLACK);

                // Obtener el estado de la celda
                char cellState = machineBoard.getGrid()[row][col];

                // Asignar color según el estado de la celda
                switch (cellState) {
                    case '-': // Vacío
                        cell.setFill(Color.LIGHTBLUE);
                        break;
                    case 'X': // Golpe
                        cell.setFill(Color.RED);
                        break;
                    case 'O': // Fallo
                        cell.setFill(Color.YELLOW);
                        break;
                    case 'S': // Barco
                        cell.setFill(Color.DARKGRAY);
                        break;
                    default: // Estado desconocido
                        cell.setFill(Color.WHITE);
                }

                opponentBoardGrid.add(cell, col, row);
            }
        }
    }


    /**
     * Cierra la ventana actual.
     */
    @FXML
    private void closeWindow() {
        opponentBoardGrid.getScene().getWindow().hide();
    }
}
