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

    public Button viewMachineBoardButton;
    public GridPane ownBoardGrid;
    public GridPane machineBoardGrid;
    private MachineAdapter machinePlayer;
    private Game game;

    @FXML
    private Label resultLabel;

    /**
     * Sets up the game instance and initializes both boards.
     *
     * @param game the game instance provided by PlacementController
     */
    public void setGame(Game game) {
        this.game = game;
        this.machinePlayer = game.machine;
        initializeHumanBoardUIWithImages();
        initializeMachineBoardUI();
    }

    /**
     * Initializes the player's board UI to display placed ships and status.
     */
    private void initializeHumanBoardUIWithImages() {
        ownBoardGrid.getChildren().clear();
        configureGridPane(ownBoardGrid, 30.0);
        var humanBoard = game.human.getBoard();
        var ships = humanBoard.getShips();
        double cellWidth = 30.0;
        double cellHeight = 30.0;

        for (Ship ship : ships) {
            String imagePath = getImagePathForShip(ship.getSize());
            if (imagePath == null) continue;

            ImageView shipImage = ImageUtils.loadImage(imagePath);

            if (ship.isHorizontal()) {
                ImageUtils.resizeImage(shipImage, cellWidth * ship.getSize(), cellHeight);
            } else {
                ImageUtils.resizeImage(shipImage, cellWidth, cellHeight * ship.getSize());
                ImageUtils.rotateImage(shipImage, 90);
            }

            int[] startCoord = ship.getCoordinates().get(0);
            int startRow = startCoord[0];
            int startCol = startCoord[1];

            ownBoardGrid.add(shipImage, startCol, startRow);
            if (ship.isHorizontal()) {
                GridPane.setColumnSpan(shipImage, ship.getSize());
            } else {
                GridPane.setRowSpan(shipImage, ship.getSize());
            }
        }

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
                    cell.setFill(Color.TRANSPARENT);
                }

                ownBoardGrid.add(cell, col, row);
            }
        }
    }

    /**
     * Configures the grid pane with fixed cell sizes.
     *
     * @param gridPane the grid pane to configure
     * @param cellSize the size of each cell
     */
    private void configureGridPane(GridPane gridPane, double cellSize) {
        gridPane.getColumnConstraints().clear();
        gridPane.getRowConstraints().clear();

        for (int i = 0; i < 10; i++) {
            ColumnConstraints colConstraints = new ColumnConstraints(cellSize);
            colConstraints.setHalignment(HPos.CENTER);
            gridPane.getColumnConstraints().add(colConstraints);

            RowConstraints rowConstraints = new RowConstraints(cellSize);
            rowConstraints.setValignment(VPos.CENTER);
            gridPane.getRowConstraints().add(rowConstraints);
        }
    }

    /**
     * Retrieves the image path for a ship based on its size.
     *
     * @param size the size of the ship
     * @return the image path or null if no image exists
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
     * Initializes the machine's board for the player to attack.
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

                cell.setOnMouseClicked(e -> handleHumanShot(finalRow, finalCol));
                machineBoardGrid.add(cell, col, row);
            }
        }
    }

    /**
     * Handles the player's shot at the machine's board.
     *
     * @param row the row selected by the player
     * @param col the column selected by the player
     */
    private void handleHumanShot(int row, int col) {
        String result = game.processHumanShot(row, col);
        updateMachineBoardUI();

        if (game.checkVictory()) {
            endGame(game.getWinner());
            return;
        }

        handleMachineTurn();
    }

    /**
     * Processes the machine's turn to shoot at the player's board.
     */
    private void handleMachineTurn() {
        int row = (int) (Math.random() * 10);
        int col = (int) (Math.random() * 10);

        String result = game.processMachineShot(row, col);
        updateHumanBoardUI();

        if (game.checkVictory()) {
            endGame(game.getWinner());
        }
    }

    /**
     * Updates the machine's board UI to reflect hits and misses.
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
     * Updates the player's board UI to reflect hits and misses.
     */
    private void updateHumanBoardUI() {
        var humanBoard = game.human.getBoard();

        for (var node : ownBoardGrid.getChildren()) {
            if (node instanceof Rectangle rect) {
                int row = GridPane.getRowIndex(node);
                int col = GridPane.getColumnIndex(node);

                if (humanBoard.isHit(row, col)) {
                    rect.setFill(Color.RED);
                } else if (humanBoard.isMiss(row, col)) {
                    rect.setFill(Color.YELLOW);
                }
            }
        }
    }

    /**
     * Ends the game and displays the winner.
     *
     * @param winner the name of the winner
     */
    private void endGame(String winner) {
        resultLabel.setText(winner.equals("Human") ? "Congratulations, you won!" : "Sorry, the machine won.");
        ownBoardGrid.setDisable(true);
        machineBoardGrid.setDisable(true);
    }

    /**
     * Handles the button to view the machine's full board.
     */
    @FXML
    private void handleViewMachineBoard() {
        try {
            if (machinePlayer != null) {
                OpponentBoardStage opponentBoardStage = new OpponentBoardStage(machinePlayer.getBoard());
                opponentBoardStage.show();
            } else {
                System.err.println("Error: machinePlayer is not initialized.");
            }
        } catch (IOException e) {
            System.err.println("Error loading opponent board view: " + e.getMessage());
        }
    }
}
