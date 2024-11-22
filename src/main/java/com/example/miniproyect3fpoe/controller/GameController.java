package com.example.miniproyect3fpoe.controller;

import com.example.miniproyect3fpoe.model.Board;
import com.example.miniproyect3fpoe.model.Game;
import com.example.miniproyect3fpoe.model.MachineAdapter;
import com.example.miniproyect3fpoe.model.Ship;
import com.example.miniproyect3fpoe.utils.ImageUtils;
import com.example.miniproyect3fpoe.view.OpponentBoardStage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;

public class GameController {

    public Button viewMachineBoardButton; // Botón para ver el tablero de la máquina
    public GridPane ownBoardGrid;         // Tablero propio del jugador
    public GridPane machineBoardGrid;     // Tablero de ataque a la máquina
    private MachineAdapter machinePlayer; // Jugador máquina
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
        this.machinePlayer = game.machine; // Inicializar la referencia a MachineAdapter
        initializeHumanBoardUIWithImages();
        initializeMachineBoardUI();
    }

    /**
     * Inicializa el tablero del jugador humano para mostrar los barcos colocados.
     */
    private void initializeHumanBoardUIWithImages() {
        ownBoardGrid.getChildren().clear(); // Limpiar contenido existente

        // Configurar el GridPane con celdas de tamaño fijo
        configureGridPane(ownBoardGrid, 30.0); // Cada celda de 30x30

        var humanBoard = game.human.getBoard();
        var ships = humanBoard.getShips();

        // Dimensiones de una celda en el tablero
        double cellWidth = 30.0;
        double cellHeight = 30.0;

        // Añadir imágenes de barcos al tablero
        for (Ship ship : ships) {
            String imagePath = getImagePathForShip(ship.getSize());
            if (imagePath == null) continue;

            // Crear el ImageView para la imagen del barco
            ImageView shipImage = ImageUtils.loadImage(imagePath);

            // Rotar y redimensionar según la orientación
            if (ship.isHorizontal()) {
                ImageUtils.resizeImage(shipImage, cellWidth * ship.getSize(), cellHeight);
            } else {
                ImageUtils.resizeImage(shipImage, cellWidth, cellHeight * ship.getSize());
                ImageUtils.rotateImage(shipImage, 90);
            }

            // Coordenadas iniciales del barco
            int[] startCoord = ship.getCoordinates().get(0);
            int startRow = startCoord[0];
            int startCol = startCoord[1];

            // Añadir la imagen al GridPane
            ownBoardGrid.add(shipImage, startCol, startRow);
            if (ship.isHorizontal()) {
                GridPane.setColumnSpan(shipImage, ship.getSize());
            } else {
                GridPane.setRowSpan(shipImage, ship.getSize());
            }
        }

        // Añadir celdas vacías o disparos sobre las imágenes
        for (int row = 0; row < 10; row++) {
            for (int col = 0; col < 10; col++) {
                Rectangle cell = new Rectangle(cellWidth, cellHeight);
                cell.setStroke(Color.BLACK);

                if (humanBoard.isHit(row, col)) {
                    cell.setFill(Color.RED);
                } else if (humanBoard.isMiss(row, col)) {
                    cell.setFill(Color.YELLOW);
                } else if (!humanBoard.occupiesCell(row, col)) {
                    cell.setFill(Color.LIGHTBLUE);
                } else {
                    cell.setFill(Color.TRANSPARENT); // Transparente para no ocultar las imágenes
                }

                ownBoardGrid.add(cell, col, row);
            }
        }
    }

    private void configureGridPane(GridPane gridPane, double cellSize) {
        gridPane.getColumnConstraints().clear();
        gridPane.getRowConstraints().clear();

        // Configurar columnas y filas con tamaño fijo
        for (int i = 0; i < 10; i++) { // 10 es el tamaño del tablero
            ColumnConstraints colConstraints = new ColumnConstraints(cellSize);
            colConstraints.setHalignment(HPos.CENTER); // Centrar horizontalmente
            gridPane.getColumnConstraints().add(colConstraints);

            RowConstraints rowConstraints = new RowConstraints(cellSize);
            rowConstraints.setValignment(VPos.CENTER); // Centrar verticalmente
            gridPane.getRowConstraints().add(rowConstraints);
        }
    }


    /**
     * Devuelve la ruta de la imagen correspondiente al tamaño del barco.
     */
    private String getImagePathForShip(int size) {
        return switch (size) {
            case 4 -> "/com/example/miniproyect3fpoe/images/portaaviones.png";
            case 3 -> "/com/example/miniproyect3fpoe/images/submarino.png";
            case 2 -> "/com/example/miniproyect3fpoe/images/destructor.png";
            case 1 -> "/com/example/miniproyect3fpoe/images/fragata.png";
            default -> null;
        };
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
            // Verificar si el nodo es un Rectangle
            if (node instanceof Rectangle rect) {
                int row = GridPane.getRowIndex(node);
                int col = GridPane.getColumnIndex(node);

                if (humanBoard.isHit(row, col)) {
                    rect.setFill(Color.RED);
                } else if (humanBoard.isMiss(row, col)) {
                    rect.setFill(Color.YELLOW);
                }
            }
            // Si es un ImageView (barco), lo ignoramos
            else if (node instanceof ImageView) {
                // No hacemos nada porque los barcos no cambian
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
    @FXML
    private void handleViewMachineBoard() {
        try {
            if (machinePlayer != null) {
                OpponentBoardStage opponentBoardStage = new OpponentBoardStage(machinePlayer.getBoard());
                opponentBoardStage.show();
            } else {
                System.err.println("Error: machinePlayer no está inicializado.");
            }
        } catch (IOException e) {
            System.err.println("Error al cargar la vista del tablero del oponente: " + e.getMessage());
        }
    }



}
