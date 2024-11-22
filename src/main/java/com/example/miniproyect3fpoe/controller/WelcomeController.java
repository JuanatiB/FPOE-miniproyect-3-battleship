package com.example.miniproyect3fpoe.controller;

import com.example.miniproyect3fpoe.model.Board;
import com.example.miniproyect3fpoe.model.Game;
import com.example.miniproyect3fpoe.model.HumanAdapter;
import com.example.miniproyect3fpoe.model.MachineAdapter;
import com.example.miniproyect3fpoe.view.PlacementStage;
import com.example.miniproyect3fpoe.view.InstructionStage;
import com.example.miniproyect3fpoe.view.WelcomeStage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;

public class WelcomeController {

    @FXML
    private Button InstructionsButton;

    @FXML
    private Button StartGameButton;

    private final Board humanBoard = new Board();
    private final Board machineBoard = new Board();
    private final HumanAdapter human = new HumanAdapter(humanBoard);
    private final MachineAdapter machine = new MachineAdapter(machineBoard);
    private final Game game = new Game(human, machine);

    /**
     * Handles the action to show the instructions screen.
     * Closes the welcome stage and opens the instruction stage.
     *
     * @param event the action event triggered by clicking the instructions button
     * @throws IOException if there is an error loading the instruction stage
     */
    @FXML
    void HandleInstructions(ActionEvent event) throws IOException {
        InstructionStage.getInstance();
        WelcomeStage.deleteInstance();
    }

    /**
     * Handles the action to start the game.
     * Closes the welcome stage and opens the placement stage, initializing the game.
     *
     * @param event the action event triggered by clicking the start game button
     * @throws IOException if there is an error loading the placement stage
     */
    @FXML
    void HandlePlay(ActionEvent event) throws IOException {
        PlacementStage.getInstance().getGameController().setGame(game);
        WelcomeStage.deleteInstance();
    }
}
