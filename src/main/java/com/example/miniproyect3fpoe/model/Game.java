package com.example.miniproyect3fpoe.model;

public class Game implements IGame {
    public final HumanAdapter human;
    public final MachineAdapter machine;
    private String winner;

    public Game(HumanAdapter human, MachineAdapter machine) {
        this.human = human;
        this.machine = machine;
        this.winner = null;
    }

    @Override
    public String processHumanShot(int row, int col) {
        String result = machine.playTurn(row, col, machine.getBoard());
        System.out.println("Machine Board State After Human Shot:");
        machine.getBoard().printBoard(); // Para depurar el estado del tablero de la máquina

        if (machine.getBoard().isGameOver()) {
            System.out.println("Machine has lost. Declaring Human as the winner.");
            winner = "Human";
        }
        return result;
    }

    @Override
    public String processMachineShot(int row, int col) {
        String result = human.playTurn(row, col, human.getBoard());
        System.out.println("Human Board State After Machine Shot:");
        human.getBoard().printBoard(); // Para depurar el estado del tablero del humano

        if (human.getBoard().isGameOver()) {
            System.out.println("Human has lost. Declaring Machine as the winner.");
            winner = "Machine";
        }
        return result;
    }


    @Override
    public boolean checkVictory() {
        System.out.println("Checking victory condition...");
        boolean humanLost = human.getBoard().isGameOver();
        boolean machineLost = machine.getBoard().isGameOver();
        System.out.println("Human lost: " + humanLost + ", Machine lost: " + machineLost);

        return humanLost || machineLost;
    }


    @Override
    public String getWinner() {
        if (winner == null) {
            throw new IllegalStateException("No winner yet.");
        }
        return winner;
    }


}
