package com.example.miniproyect3fpoe.controller;

import com.example.miniproyect3fpoe.model.*;
import com.example.miniproyect3fpoe.view.GameStage;
import com.example.miniproyect3fpoe.view.PlacementStage;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.io.IOException;
import java.util.Objects;

public class PlacementController {

    public Button startButton;
    private Game game;
    private Board humanBoard;
    private Board machineBoard;
    private final HumanAdapter human = new HumanAdapter(new Board());
    private final MachineAdapter machine = new MachineAdapter(new Board());
    private Ship currentShip;

    @FXML
    private GridPane ownBoardGrid;

    /**
     * Initializes the controller and sets up the player's board UI.
     */
    @FXML
    public void initialize() {
        initializeOwnBoardUI();
    }

    /**
     * Sets the game instance and initializes boards.
     * Prepares the game for ship placement.
     *
     * @param game the Game instance provided by WelcomeController
     */
    public void setGame(Game game) {
        this.game = game;
        this.humanBoard = game.human.getBoard();
        this.machineBoard = game.machine.getBoard();
        this.humanBoard.reset();
        this.machineBoard.reset();

        if (!game.human.getShips().isEmpty()) {
            this.currentShip = game.human.getShips().get(0);
        }

        System.out.println("Game set successfully. Ready to start!");
    }

    /**
     * Initializes the player's board UI with a GridPane.
     */
    @FXML
    private void initializeOwnBoardUI() {
        ownBoardGrid.getChildren().clear();

        for (int row = 0; row < 10; row++) {
            for (int col = 0; col < 10; col++) {
                Rectangle cell = new Rectangle(25, 25);
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

        ownBoardGrid.setOnKeyPressed(event -> {
            if (Objects.requireNonNull(event.getCode()) == KeyCode.SPACE) {
                toggleOrientation();
            }
        });

        ownBoardGrid.setFocusTraversable(true);
    }

    /**
     * Handles the action of starting the game after ship placement.
     *
     * @throws IOException if there is an error loading the game stage
     */
    @FXML
    private void handleStartButton() throws IOException {
        game.machine.placeShips();
        GameStage.getInstance().getGameController().setGame(game);
        PlacementStage.deleteInstance();
    }

    /**
     * Highlights the cells where the current ship can be placed.
     *
     * @param startRow the starting row of the potential placement
     * @param startCol the starting column of the potential placement
     */
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
                    if (humanBoard.occupiesCell(row, col)) {
                        rect.setFill(Color.GRAY);
                    } else {
                        rect.setFill(isValid ? Color.GREEN : Color.RED);
                    }
                }
            }
        }
    }

    /**
     * Clears any highlighting on the player's board.
     */
    private void clearHighlight() {
        for (var node : ownBoardGrid.getChildren()) {
            Rectangle rect = (Rectangle) node;
            int row = GridPane.getRowIndex(node);
            int col = GridPane.getColumnIndex(node);

            if (humanBoard.occupiesCell(row, col)) {
                rect.setFill(Color.GRAY);
            } else {
                rect.setFill(Color.LIGHTBLUE);
            }
        }
    }

    /**
     * Handles the player's attempt to place a ship.
     *
     * @param startRow the starting row of the attempted placement
     * @param startCol the starting column of the attempted placement
     */
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

    /**
     * Updates the board UI to reflect a placed ship.
     *
     * @param startRow the starting row of the ship
     * @param startCol the starting column of the ship
     * @param size the size of the ship
     * @param isHorizontal the orientation of the ship
     */
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

    /**
     * Checks if all ships are placed and enables the start button if true.
     */
    private void checkAllShipsPlaced() {
        if (human.getShips().stream().allMatch(ship -> humanBoard.getShips().contains(ship))) {
            startButton.setDisable(false);
            startButton.setStyle("-fx-background-color: #d6dbdf; -fx-text-fill: #1e2e51; -fx-font-weight: impact;");
            ownBoardGrid.setDisable(true);
            System.out.println("All ships placed! Board is now disabled.");
        }
    }

    /**
     * Moves to the next ship to place or validates that all ships are placed.
     */
    private void proceedToNextShip() {
        int currentIndex = human.getShips().indexOf(currentShip);

        if (currentIndex < human.getShips().size() - 1) {
            currentShip = human.getShips().get(currentIndex + 1);
            System.out.println("Select position for: " + currentShip.getName());
        } else {
            System.out.println("All ships placed!");
        }

        checkAllShipsPlaced();
    }

    /**
     * Toggles the orientation of the current ship between horizontal and vertical.
     */
    private void toggleOrientation() {
        if (currentShip == null) return;

        currentShip.setHorizontal(!currentShip.isHorizontal());
        System.out.println("Orientation toggled: " + (currentShip.isHorizontal() ? "Horizontal" : "Vertical"));
    }
}
