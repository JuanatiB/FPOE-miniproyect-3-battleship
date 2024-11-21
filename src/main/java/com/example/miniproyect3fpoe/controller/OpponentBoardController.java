package com.example.miniproyect3fpoe.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import com.example.miniproyect3fpoe.model.Board;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;


public class OpponentBoardController {

    @FXML
    private GridPane machineBoardGrid;

    private Board machineBoard;

    public void initialize(Board machineBoard) {
        machineBoardGrid.getChildren().clear();

        for (int row = 0; row < 10; row++) {
            for (int col = 0; col < 10; col++) {
                Rectangle cell = new Rectangle(30, 30);
                cell.setStroke(Color.BLACK);

                if (machineBoard.occupiesCell(row, col)) {
                    cell.setFill(Color.DARKGRAY); // Muestra barcos
                } else if (machineBoard.isHit(row, col)) {
                    cell.setFill(Color.RED); // Disparo exitoso
                } else if (machineBoard.isMiss(row, col)) {
                    cell.setFill(Color.YELLOW); // Disparo fallido
                } else {
                    cell.setFill(Color.LIGHTBLUE); // Agua
                }

                machineBoardGrid.add(cell, col, row);
            }
        }
    }

    private void displayBoard(Board board) {
        machineBoardGrid.getChildren().clear();

        for (int row = 0; row < 10; row++) {
            for (int col = 0; col < 10; col++) {
                Rectangle cell = new Rectangle(30, 30);
                cell.setStroke(Color.BLACK);
                cell.setFill(Color.LIGHTBLUE);

                // Determina el estado de cada celda
                if (board.isHit(row, col)) {
                    cell.setFill(Color.RED); // Disparo exitoso
                } else if (board.isMiss(row, col)) {
                    cell.setFill(Color.YELLOW); // Disparo fallido
                } else if (board.occupiesCell(row, col)) {
                    cell.setFill(Color.DARKGRAY); // Barco visible en el tablero
                }

                machineBoardGrid.add(cell, col, row);
            }
        }
    }


}
