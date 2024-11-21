package com.example.miniproyect3fpoe.view;


import com.example.miniproyect3fpoe.controller.InstructionController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;


public class InstructionStage extends Stage {

    private final InstructionController instructionController;

    public InstructionStage() throws IOException {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/com/example/miniproyect3fpoe/instruction-view.fxml")
        );
        Parent root = loader.load();
        instructionController = loader.getController();
        Scene scene = new Scene(root);
        setScene(scene);
        setTitle("Instructions");
//        getIcons().add(new Image(String.valueOf(
//                getClass().getResource("/proyecto1/numero-1.png"))
//        ));
        setResizable(false);
        setWidth(800);
        setHeight(500);
        show();
    }

    /**
     * Returns the {@code InstructionController} associated with this {@code InstructionStage}.
     *
     * @return the {@code InstructionController} instance controlling the instruction logic.
     */
    public InstructionController getInstructionController() {
        return instructionController;
    }

    /**
     * Nested static class that holds the singleton instance of {@code InstructionStage}.
     */
    private static class InstructionStageHolder {
        /**
         * Singleton instance of {@code InstructionStage}.
         */
        private static InstructionStage INSTANCE;
    }

    /**
     * Returns the singleton instance of {@code InstructionStage}, creating it if necessary.
     *
     * @return the singleton instance of {@code InstructionStage}.
     * @throws IOException if the FXML file cannot be loaded during instantiation.
     */
    public static InstructionStage getInstance() throws IOException {
        InstructionStageHolder.INSTANCE =
                InstructionStageHolder.INSTANCE != null ?
                        InstructionStageHolder.INSTANCE :
                        new InstructionStage();

        return InstructionStageHolder.INSTANCE;
    }

    /**
     * Closes the current {@code InstructionStage} instance and resets the singleton to null.
     */
    public static void deleteInstance() {
        InstructionStageHolder.INSTANCE.close();
        InstructionStageHolder.INSTANCE = null;
    }

}
