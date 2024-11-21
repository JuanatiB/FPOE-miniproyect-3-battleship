package com.example.miniproyect3fpoe.controller;

import com.example.miniproyect3fpoe.model.Game;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

public class GameController {
    public Button viewMachineBoardButton;
    public GridPane ownBoardGrid;
    private Game game;

    public void setGame(Game game) {
        this.game = game;
    }

    public Game getGame() {
        return game;
    }
    
    

    public void handleViewMachineBoard(ActionEvent actionEvent) {
        //Aquí te encargas de crear la instancia de la ventana en la que se
        //muestre el board de la máquina
        System.out.println("viewing machine board...");
    }
}

