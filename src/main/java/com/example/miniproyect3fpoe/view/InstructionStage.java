package com.example.miniproyect3fpoe.view;

import com.example.miniproyect3fpoe.controller.InstructionController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * The {@code InstructionStage} class represents the window (stage) where the game instructions
 * and credits are displayed. It sets up the user interface, including loading the FXML file,
 * configuring the controller, and adjusting window properties such as size and title.
 */
public class InstructionStage extends Stage {

    private final InstructionController instructionController;

    /**
     * Creates a new {@code InstructionStage} instance, loads the FXML file, and sets up the UI properties.
     *
     * @throws IOException if the FXML file cannot be loaded
     */
    public InstructionStage() throws IOException {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/com/example/miniproyect3fpoe/instruction-view.fxml")
        );
        Parent root = loader.load();
        instructionController = loader.getController();
        Scene scene = new Scene(root);
        setScene(scene);
        setTitle("Instructions and Credits");
        setResizable(false);
        setWidth(400);
        setHeight(700);
        show();
    }

    /**
     * Returns the {@code InstructionController} associated with this {@code InstructionStage}.
     *
     * @return the {@code InstructionController} instance controlling the instruction logic
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
     * @return the singleton instance of {@code InstructionStage}
     * @throws IOException if the FXML file cannot be loaded during instantiation
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
        if (InstructionStageHolder.INSTANCE != null) {
            InstructionStageHolder.INSTANCE.close();
            InstructionStageHolder.INSTANCE = null;
        }
    }
}
