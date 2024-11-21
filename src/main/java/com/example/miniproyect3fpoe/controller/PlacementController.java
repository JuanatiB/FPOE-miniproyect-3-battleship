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

    private Ship currentShip; // Barco actual que el jugador está posicionando

    @FXML
    private GridPane ownBoardGrid;

    /**
     * Método llamado al inicializar el controlador.
     */
    @FXML
    public void initialize() {
        initializeOwnBoardUI();
    }

    /**
     * Configura el juego al recibir una instancia de Game.
     * También inicializa los tableros y prepara el estado inicial del juego.
     *
     * @param game Instancia de Game proporcionada por WelcomeController.
     */
    public void setGame(Game game) {
        this.game = game;

        // Acceder directamente a los tableros desde los jugadores
        this.humanBoard = game.human.getBoard();
        this.machineBoard = game.machine.getBoard();

        // Reiniciar el estado inicial de los tableros
        this.humanBoard.reset();
        this.machineBoard.reset();

        // Configurar el primer barco que el jugador humano debe colocar
        if (!game.human.getShips().isEmpty()) {
            this.currentShip = game.human.getShips().get(0);
        }

        System.out.println("Game set successfully. Ready to start!");
    }


    /**
     * Inicializa el GridPane para mostrar el tablero del jugador.
     */
    @FXML
    private void initializeOwnBoardUI() {
        ownBoardGrid.getChildren().clear();

        // Configuración del GridPane y sus celdas
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

        // Evento para cambiar la orientación con la tecla Espacio
        ownBoardGrid.setOnKeyPressed(event -> {
            if (Objects.requireNonNull(event.getCode()) == KeyCode.SPACE) {
                toggleOrientation(); // Cambiar orientación
            }
        });

        // Permitir que el GridPane reciba eventos de teclado
        ownBoardGrid.setFocusTraversable(true);
    }

    @FXML
    private void handleStartButton() throws IOException {
        // Asegúrate de que los barcos de la máquina sean colocados antes de iniciar el juego
        game.machine.placeShips();

        // Crear y configurar el GameStage
        GameStage.getInstance().getGameController().setGame(game);

        // Cerrar el PlacementStage
        PlacementStage.deleteInstance();
    }






    /**
     * Resalta las celdas donde podría colocarse el barco actual.
     *
     * @param startRow Fila inicial del posible posicionamiento.
     * @param startCol Columna inicial del posible posicionamiento.
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
                    // Solo resaltar si la celda no está ocupada
                    if (humanBoard.occupiesCell(row, col)) {
                        rect.setFill(Color.GRAY); // Ocupada, ya tiene un barco
                    } else {
                        rect.setFill(isValid ? Color.GREEN : Color.RED); // Resaltado válido/incorrecto
                    }
                }
            }
        }
    }


    /**
     * Limpia el resaltado en el tablero.
     */
    private void clearHighlight() {
        for (var node : ownBoardGrid.getChildren()) {
            Rectangle rect = (Rectangle) node;
            int row = GridPane.getRowIndex(node);
            int col = GridPane.getColumnIndex(node);

            // Solo limpiar si la celda no está ocupada
            if (humanBoard.occupiesCell(row, col)) {
                rect.setFill(Color.GRAY); // Ocupada, ya tiene un barco
            } else {
                rect.setFill(Color.LIGHTBLUE); // Vacía
            }
        }
    }


    /**
     * Maneja el clic del jugador para intentar colocar un barco.
     *
     * @param startRow Fila inicial seleccionada.
     * @param startCol Columna inicial seleccionada.
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
     * Actualiza la interfaz para mostrar un barco colocado.
     *
     * @param startRow Fila inicial del barco.
     * @param startCol Columna inicial del barco.
     * @param size Tamaño del barco.
     * @param isHorizontal Orientación del barco.
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
     * Checks if all ships have been placed and enables the "Start" button if true.
     */
    private void checkAllShipsPlaced() {
        if (human.getShips().stream().allMatch(ship -> humanBoard.getShips().contains(ship))) {
            startButton.setDisable(false);
            startButton.setStyle("-fx-background-color: #4caf50; -fx-text-fill: white; -fx-font-weight: bold;");
            ownBoardGrid.setDisable(true); // Desactiva el tablero
            System.out.println("All ships placed! Board is now disabled.");
        }
    }



    /**
     * Proceeds to the next ship to be placed, or validates that all ships are placed.
     */
    private void proceedToNextShip() {
        // Get the current index of the ship being placed
        int currentIndex = human.getShips().indexOf(currentShip);

        // If there are more ships to place, move to the next one
        if (currentIndex < human.getShips().size() - 1) {
            currentShip = human.getShips().get(currentIndex + 1);
            System.out.println("Select position for: " + currentShip.getName());
        } else {
            // If no more ships remain, check if all are placed
            System.out.println("All ships placed!");
        }

        // Validate ship placement status
        checkAllShipsPlaced();
    }


    private void toggleOrientation() {
        if (currentShip == null) return;

        // Cambiar la orientación del barco actual
        currentShip.setHorizontal(!currentShip.isHorizontal());
        System.out.println("Orientation toggled: " + (currentShip.isHorizontal() ? "Horizontal" : "Vertical"));
    }


}
