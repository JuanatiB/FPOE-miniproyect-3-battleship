package com.example.miniproyect3fpoe.controller;

import com.example.miniproyect3fpoe.model.Game;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class GameController {

    public Button viewMachineBoardButton; // Botón para ver el tablero de la máquina
    public GridPane ownBoardGrid;         // Tablero propio del jugador
    public GridPane machineBoardGrid;     // Tablero de ataque a la máquina

    private Game game;

    @FXML
    private Label resultLabel;



    /**
     * Configura el juego al recibir una instancia de Game.
     *
     * @param game Instancia del juego proporcionada por PlacementController.
     */
    public void setGame(Game game) {
        this.game = game;
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
        // Generar coordenadas aleatorias de disparo desde MachineAdapter
        int row = (int) (Math.random() * 10);
        int col = (int) (Math.random() * 10);

        // Registrar el disparo de la máquina en el tablero del humano
        String result = game.processMachineShot(row, col);
        System.out.println("Machine shot result: " + result);

        // Actualizar el tablero del jugador humano
        updateHumanBoardUI();

        // Verificar si la máquina ha ganado
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
        resultLabel.setText(winner.equals("Human") ? "Felicidades, ganaste!" : "Lo siento, la máquina ganó esta");
        ownBoardGrid.setDisable(true);
        machineBoardGrid.setDisable(true);
    }

    /**
     * Maneja el botón para ver el tablero completo de la máquina.
     */
    public void handleViewMachineBoard(ActionEvent actionEvent) {
        System.out.println("Viewing machine board...");
        // Implementación para abrir la ventana del tablero de la máquina
    }
}
