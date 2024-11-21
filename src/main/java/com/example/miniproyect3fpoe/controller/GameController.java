package com.example.miniproyect3fpoe.controller;

import com.example.miniproyect3fpoe.model.Game;
import com.example.miniproyect3fpoe.utils.GameSaveManager;
import com.example.miniproyect3fpoe.view.OpponentBoardStage;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.io.IOException;

public class GameController {

    public Button viewMachineBoardButton; // Botón para ver el tablero de la máquina
    public GridPane ownBoardGrid;         // Tablero propio del jugador
    public GridPane machineBoardGrid;     // Tablero de ataque a la máquina

    private Game game;
    private String playerNickname = "Player"; // Nickname por defecto

    /**
     * Configura el juego al recibir una instancia de Game.
     *
     * @param game Instancia del juego proporcionada por PlacementController.
     */
    public void setGame(Game game) {
        this.game = game;

        // Cargar estado del juego si existe un archivo de guardado
        if (GameSaveManager.hasSavedGame()) {
            Object[] gameState = GameSaveManager.loadGame();
            String[] playerData = GameSaveManager.loadPlayerData();

            if (gameState != null && playerData != null) {
                game.human.getBoard().copyState((com.example.miniproyect3fpoe.model.Board) gameState[0]);
                game.machine.getBoard().copyState((com.example.miniproyect3fpoe.model.Board) gameState[1]);
                playerNickname = playerData[0];
                System.out.println("Game loaded successfully for player: " + playerNickname);
            }
        }

        initializeHumanBoardUI();
        initializeMachineBoardUI();
    }

    /**
     * Retorna la instancia actual de Game.
     *
     * @return Instancia del juego.
     */
    public Game getGame() {
        return game;
    }

    /**
     * Inicializa el tablero del jugador humano para mostrar los barcos colocados.
     */
    private void initializeHumanBoardUI() {
        ownBoardGrid.getChildren().clear();
        var humanBoard = game.human.getBoard();

        for (int row = 0; row < 10; row++) {
            for (int col = 0; col < 10; col++) {
                Rectangle cell = new Rectangle(30, 30);
                cell.setStroke(Color.BLACK);
                cell.setFill(humanBoard.occupiesCell(row, col) ? Color.DARKGRAY : Color.LIGHTBLUE);

                // Muestra los disparos hechos por la máquina
                if (humanBoard.isHit(row, col)) {
                    cell.setFill(Color.RED);
                } else if (humanBoard.isMiss(row, col)) {
                    cell.setFill(Color.YELLOW);
                }

                ownBoardGrid.add(cell, col, row);
            }
        }
    }

    /**
     * Inicializa el tablero de la máquina para que el jugador humano pueda disparar.
     */
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

                // Interactividad del tablero de ataque
                cell.setOnMouseClicked(e -> handleHumanShot(finalRow, finalCol));
                machineBoardGrid.add(cell, col, row);
            }
        }
    }

    /**
     * Maneja el disparo del jugador humano al tablero de la máquina.
     *
     * @param row Fila seleccionada.
     * @param col Columna seleccionada.
     */
    private void handleHumanShot(int row, int col) {
        System.out.println("Human shot at (" + row + ", " + col + ")...");
        String result = game.processHumanShot(row, col);
        System.out.println("Human shot result: " + result);

        // Guardar automáticamente el estado del juego
        GameSaveManager.saveGame(game.human.getBoard(), game.machine.getBoard(), playerNickname, game.human.getSunkShipsCount());

        // Actualiza el tablero de la máquina
        updateMachineBoardUI();

        // Solo terminar si checkVictory confirma que alguien ha ganado
        if (game.checkVictory()) {
            System.out.println("Victory detected after human shot.");
            endGame(game.getWinner());
            return;
        }

        // Si no hay ganador, pasa al turno de la máquina
        handleMachineTurn();
    }

    /**
     * La máquina realiza un disparo en el tablero del jugador humano.
     */
    private void handleMachineTurn() {
        int row = (int) (Math.random() * 10);
        int col = (int) (Math.random() * 10);

        String result = game.processMachineShot(row, col);
        System.out.println("Machine shot result: " + result);

        // Guardar automáticamente el estado del juego
        GameSaveManager.saveGame(game.human.getBoard(), game.machine.getBoard(), playerNickname, game.human.getSunkShipsCount());

        // Actualizar el tablero del jugador humano
        updateHumanBoardUI();

        if (game.checkVictory()) {
            endGame(game.getWinner());
        }
    }

    /**
     * Actualiza la interfaz del tablero de la máquina.
     */
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

    /**
     * Actualiza la interfaz del tablero del jugador humano.
     */
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

    /**
     * Finaliza el juego y muestra el ganador.
     *
     * @param winner Nombre del ganador.
     */
    private void endGame(String winner) {
        System.out.println("Game Over! Winner: " + winner);
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
