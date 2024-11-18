package com.example.miniproyect3fpoe.controller;

import com.example.miniproyect3fpoe.view.InstructionStage;
import com.example.miniproyect3fpoe.view.WelcomeStage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;

public class InstructionController {

    @FXML
    private Button BackButton;

    @FXML
    void HandleBack(ActionEvent event) throws IOException {
        WelcomeStage.getInstance();
        InstructionStage.deleteInstance();
    }

}

