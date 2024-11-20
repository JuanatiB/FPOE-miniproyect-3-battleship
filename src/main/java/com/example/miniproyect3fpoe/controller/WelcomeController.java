package com.example.miniproyect3fpoe.controller;

import com.example.miniproyect3fpoe.model.Board;
import com.example.miniproyect3fpoe.model.Game;
import com.example.miniproyect3fpoe.model.HumanAdapter;
import com.example.miniproyect3fpoe.model.MachineAdapter;
import com.example.miniproyect3fpoe.view.GameStage;
import com.example.miniproyect3fpoe.view.InstructionStage;
import com.example.miniproyect3fpoe.view.WelcomeStage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;


public class WelcomeController  {

    @FXML
    private Button InstructionsButton;

    @FXML
    private Button StartGameButton;

    private final Board humanBoard = new Board();

    private final Board machineBoard = new Board();

    private final HumanAdapter human = new HumanAdapter(humanBoard);

    private final MachineAdapter machine = new MachineAdapter(machineBoard);

    private final Game game = new Game(human, machine);

    @FXML
    void HandleInstructions(ActionEvent event) throws IOException {
        InstructionStage.getInstance();
        WelcomeStage.deleteInstance();

    }

    @FXML
    void HandlePlay(ActionEvent event) throws IOException {
        GameStage.getInstance().getGameController().setGame(game);
        WelcomeStage.deleteInstance();

    }


}
