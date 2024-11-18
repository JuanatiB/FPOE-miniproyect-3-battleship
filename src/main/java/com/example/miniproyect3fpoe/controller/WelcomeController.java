package com.example.miniproyect3fpoe.controller;

import com.example.miniproyect3fpoe.model.Game;
import com.example.miniproyect3fpoe.view.GameStage;
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

    private final Game game = new Game();

    @FXML
    void HandleInstructions(ActionEvent event) {

    }

    @FXML
    void HandlePlay(ActionEvent event) throws IOException {
        GameStage.getInstance().getGameController().setGame(game);
        WelcomeStage.deleteInstance();

    }


}
