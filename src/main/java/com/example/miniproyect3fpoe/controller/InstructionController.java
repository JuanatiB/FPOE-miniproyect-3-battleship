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

    /**
     * Handles the action to return to the welcome stage.
     * Deletes the current instance of the instruction stage.
     *
     * @param event the action event triggered by the back button
     * @throws IOException if there is an error initializing the welcome stage
     */
    @FXML
    void HandleBack(ActionEvent event) throws IOException {
        WelcomeStage.getInstance();
        InstructionStage.deleteInstance();
    }
}

