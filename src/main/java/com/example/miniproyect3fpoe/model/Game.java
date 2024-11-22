package com.example.miniproyect3fpoe.model;

/**
 * Represents the main game logic for a Battleship game.
 * Manages turns, processes shots, and determines the winner.
 */
public class Game implements IGame {

    public final HumanAdapter human;
    public final MachineAdapter machine;
    private String winner;

    /**
     * Constructs a new game instance with a human and a machine player.
     *
     * @param human   the human player
     * @param machine the machine player
     */
    public Game(HumanAdapter human, MachineAdapter machine) {
        this.human = human;
        this.machine = machine;
        this.winner = null;
    }

    /**
     * Processes the human player's shot on the machine's board.
     * Updates the game state and checks for a winner.
     *
     * @param row the row of the target cell
     * @param col the column of the target cell
     * @return the result of the shot ("hit", "miss", or "sunk")
     */
    @Override
    public String processHumanShot(int row, int col) {
        String result = machine.playTurn(row, col, machine.getBoard());
        System.out.println("Machine Board State After Human Shot:");
        machine.getBoard().printBoard();

        if (machine.getBoard().isGameOver()) {
            System.out.println("Machine has lost. Declaring Human as the winner.");
            winner = "Human";
        }
        return result;
    }

    /**
     * Processes the machine player's shot on the human's board.
     * Updates the game state and checks for a winner.
     *
     * @param row the row of the target cell
     * @param col the column of the target cell
     * @return the result of the shot ("hit", "miss", or "sunk")
     */
    @Override
    public String processMachineShot(int row, int col) {
        String result = human.playTurn(row, col, human.getBoard());
        System.out.println("Human Board State After Machine Shot:");
        human.getBoard().printBoard();

        if (human.getBoard().isGameOver()) {
            System.out.println("Human has lost. Declaring Machine as the winner.");
            winner = "Machine";
        }
        return result;
    }

    /**
     * Checks if there is a victory condition.
     *
     * @return true if either player has lost all ships, false otherwise
     */
    @Override
    public boolean checkVictory() {
        System.out.println("Checking victory condition...");
        boolean humanLost = human.getBoard().isGameOver();
        boolean machineLost = machine.getBoard().isGameOver();
        System.out.println("Human lost: " + humanLost + ", Machine lost: " + machineLost);

        return humanLost || machineLost;
    }

    /**
     * Returns the winner of the game.
     *
     * @return the winner ("Human" or "Machine")
     * @throws IllegalStateException if there is no winner yet
     */
    @Override
    public String getWinner() {
        if (winner == null) {
            throw new IllegalStateException("No winner yet.");
        }
        return winner;
    }
}
