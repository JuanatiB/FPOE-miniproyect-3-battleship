package com.example.miniproyect3fpoe.controller;

import com.example.miniproyect3fpoe.model.*;
import javafx.fxml.FXML;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class GameController {

    private final Board humanBoard = new Board();
    private final Board machineBoard = new Board();
    private final HumanAdapter human = new HumanAdapter(humanBoard);
    private final MachineAdapter machine = new MachineAdapter(machineBoard);
    private Game game = new Game(human, machine);

    private Ship currentShip; // Barco actual que el jugador está posicionando

    @FXML
    private GridPane ownBoardGrid;

    @FXML
    public void initialize() {
        initializeOwnBoardUI();

        // Configurar el evento de teclado para alternar orientación
        ownBoardGrid.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.SPACE) {
                toggleShipOrientation();
            }
        });

        // Pedir foco para el GridPane
        ownBoardGrid.requestFocus();
    }

    private void initializeOwnBoardUI() {
        ownBoardGrid.getChildren().clear();

        for (int row = 0; row < 10; row++) {
            for (int col = 0; col < 10; col++) {
                Rectangle cell = new Rectangle(30, 30);
                cell.setFill(Color.LIGHTBLUE);
                cell.setStroke(Color.BLACK);

                int finalRow = row;
                int finalCol = col;

                cell.setOnMouseEntered(e -> highlightPotentialShip(finalRow, finalCol));
                cell.setOnMouseExited(e -> clearHighlight());
                cell.setOnMouseClicked(e -> handleCellClick(finalRow, finalCol));

                ownBoardGrid.add(cell, col, row);
            }
        }
    }

    private void toggleShipOrientation() {
        if (currentShip == null) {
            System.out.println("No ship selected to toggle orientation.");
            return;
        }

        currentShip.setHorizontal(!currentShip.isHorizontal());
        System.out.println("Orientation for " + currentShip.getName() + " set to: " +
                (currentShip.isHorizontal() ? "Horizontal" : "Vertical"));
    }

    private void highlightPotentialShip(int startRow, int startCol) {
        if (currentShip == null) return;

        boolean isHorizontal = currentShip.isHorizontal();
        int size = currentShip.getSize();

        boolean isValid = humanBoard.isPlacementValid(currentShip, startRow, startCol);

        for (int i = 0; i < size; i++) {
            int row = isHorizontal ? startRow : startRow + i;
            int col = isHorizontal ? startCol + i : startCol;

            for (var node : ownBoardGrid.getChildren()) {
                if (GridPane.getRowIndex(node) == row && GridPane.getColumnIndex(node) == col) {
                    Rectangle rect = (Rectangle) node;
                    rect.setFill(isValid ? Color.GREEN : Color.RED);
                }
            }
        }
    }

    private void clearHighlight() {
        for (var node : ownBoardGrid.getChildren()) {
            Rectangle rect = (Rectangle) node;
            rect.setFill(Color.LIGHTBLUE);
        }
    }

    private void handleCellClick(int startRow, int startCol) {
        if (currentShip == null) {
            System.out.println("No ship selected for placement.");
            return;
        }

        boolean isHorizontal = currentShip.isHorizontal();
        boolean placed = humanBoard.placeShip(currentShip, startRow, startCol);

        if (placed) {
            System.out.println(currentShip.getName() + " placed successfully!");
            updateOwnBoardUI(startRow, startCol, currentShip.getSize(), isHorizontal);
            proceedToNextShip();
        } else {
            System.out.println("Invalid placement. Try again.");
        }
    }

    private void updateOwnBoardUI(int startRow, int startCol, int size, boolean isHorizontal) {
        for (int i = 0; i < size; i++) {
            int row = isHorizontal ? startRow : startRow + i;
            int col = isHorizontal ? startCol + i : startCol;

            for (var node : ownBoardGrid.getChildren()) {
                if (GridPane.getRowIndex(node) == row && GridPane.getColumnIndex(node) == col) {
                    Rectangle rect = (Rectangle) node;
                    rect.setFill(Color.GRAY);
                }
            }
        }
    }

    private void proceedToNextShip() {
        int currentIndex = human.getShips().indexOf(currentShip);
        if (currentIndex < human.getShips().size() - 1) {
            currentShip = human.getShips().get(currentIndex + 1);
            System.out.println("Select position for: " + currentShip.getName());
        } else {
            System.out.println("All ships placed!");
        }
    }
}
