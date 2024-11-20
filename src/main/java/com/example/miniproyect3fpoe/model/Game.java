package com.example.miniproyect3fpoe.model;

public class Game implements IGame {
    private final HumanAdapter human;
    private final MachineAdapter machine;
    private String winner;

    public Game(HumanAdapter human, MachineAdapter machine) {
        this.human = human;
        this.machine = machine;
        this.winner = null;
    }

    @Override
    public String processHumanShot(int row, int col) {
        // El humano dispara al tablero de la máquina
        String result = machine.playTurn(row, col, machine.getBoard());
        if (checkVictory()) {
            winner = "Human";
        }
        return result;
    }

    @Override
    public String processMachineShot(int row, int col) {
        // La máquina dispara al tablero del humano
        String result = human.playTurn(row, col, human.getBoard());
        if (checkVictory()) {
            winner = "Machine";
        }
        return result;
    }

    @Override
    public boolean checkVictory() {
        return human.hasLost() || machine.hasLost();
    }

    @Override
    public String getWinner() {
        if (winner == null) {
            throw new IllegalStateException("No winner yet.");
        }
        return winner;
    }


}
