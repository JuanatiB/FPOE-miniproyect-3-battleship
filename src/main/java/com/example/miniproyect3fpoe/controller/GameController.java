package com.example.miniproyect3fpoe.controller;

import com.example.miniproyect3fpoe.model.Game;
import com.example.miniproyect3fpoe.utils.GameSaveManager;
import com.example.miniproyect3fpoe.view.OpponentBoardStage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.io.IOException;

public class GameController {

    public Button viewMachineBoardButton; // Botón para ver el tablero de la máquina
    public GridPane ownBoardGrid;         // Tablero propio del jugador
    public GridPane machineBoardGrid;     // Tablero de ataque a la máquina

    private Game game;

    @FXML
    private Label resultLabel;

    public void setGame(Game game) {
        this.game = game;
        initializeHumanBoardUI();
        initializeMachineBoardUI();

        // Intentar cargar el estado previo del juego si existe
        if (GameSaveManager.saveExists()) {
            try {
                this.game = (Game) GameSaveManager.loadGame();
                System.out.println("Game state loaded successfully!");
            } catch (Exception e) {
                System.err.println("Failed to load game state: " + e.getMessage());
            }
        }
    }

    private void initializeHumanBoardUI() {
        ownBoardGrid.getChildren().clear();
        var humanBoard = game.human.getBoard();

        for (int row = 0; row < 10; row++) {
            for (int col = 0; col < 10; col++) {
                Rectangle cell = new Rectangle(30, 30);
                cell.setStroke(Color.BLACK);
                cell.setFill(humanBoard.occupiesCell(row, col) ? Color.DARKGRAY : Color.LIGHTBLUE);

                if (humanBoard.isHit(row, col)) {
                    cell.setFill(Color.RED);
                } else if (humanBoard.isMiss(row, col)) {
                    cell.setFill(Color.YELLOW);
                }

                ownBoardGrid.add(cell, col, row);
            }
        }
    }

    private void initializeMachineBoardUI() {
        machineBoardGrid.getChildren().clear();
        var machineBoard = game.machine.getBoard();

        for (int row = 0; row < 10; row++) {
            for (int col = 0; col < 10; col++) {
                Rectangle cell = new Rectangle(30, 30);
                cell.setStroke(Color.BLACK);
                cell.setFill(Color.LIGHTBLUE);

                int finalRow = row;
                int finalCol = col;

                cell.setOnMouseClicked(e -> handleHumanShot(finalRow, finalCol));
                machineBoardGrid.add(cell, col, row);
            }
        }
    }

    public void handleHumanShot(int row, int col) {
        System.out.println("Human shot at (" + row + ", " + col + ")...");
        String result = game.processHumanShot(row, col);
        System.out.println("Human shot result: " + result);

        updateMachineBoardUI();

        if (game.checkVictory()) {
            System.out.println("Victory detected after human shot.");
            endGame(game.getWinner());
            return;
        }

        saveGameState(); // Guardar el estado después del turno humano

        handleMachineTurn();
    }

    private void handleMachineTurn() {
        int row = (int) (Math.random() * 10);
        int col = (int) (Math.random() * 10);

        String result = game.processMachineShot(row, col);
        System.out.println("Machine shot result: " + result);

        updateHumanBoardUI();

        if (game.checkVictory()) {
            endGame(game.getWinner());
        }

        saveGameState(); // Guardar el estado después del turno de la máquina
    }

    private void saveGameState() {
        try {
            GameSaveManager.saveGame(game);
            System.out.println("Game state saved successfully!");
        } catch (IOException e) {
            System.err.println("Failed to save game state: " + e.getMessage());
        }
    }

    private void updateMachineBoardUI() {
        var machineBoard = game.machine.getBoard();

        for (var node : machineBoardGrid.getChildren()) {
            int row = GridPane.getRowIndex(node);
            int col = GridPane.getColumnIndex(node);
            Rectangle rect = (Rectangle) node;

            if (machineBoard.isHit(row, col)) {
                rect.setFill(Color.RED);
            } else if (machineBoard.isMiss(row, col)) {
                rect.setFill(Color.YELLOW);
            }
        }
    }

    private void updateHumanBoardUI() {
        var humanBoard = game.human.getBoard();

        for (var node : ownBoardGrid.getChildren()) {
            int row = GridPane.getRowIndex(node);
            int col = GridPane.getColumnIndex(node);
            Rectangle rect = (Rectangle) node;

            if (humanBoard.isHit(row, col)) {
                rect.setFill(Color.RED);
            } else if (humanBoard.isMiss(row, col)) {
                rect.setFill(Color.YELLOW);
            }
        }
    }

    private void endGame(String winner) {
        resultLabel.setText(winner.equals("Human") ? "Felicidades, ganaste!" : "Lo siento, la máquina ganó esta");
        ownBoardGrid.setDisable(true);
        machineBoardGrid.setDisable(true);
    }

    /**
     * Maneja el botón para ver el tablero completo de la máquina.
     */
    public void handleViewMachineBoard(ActionEvent actionEvent) throws IOException {
        System.out.println("Viewing machine board...");
        OpponentBoardStage.getInstance().getOpponentBoardController().initialize(game.machine.getBoard());
    }
}

