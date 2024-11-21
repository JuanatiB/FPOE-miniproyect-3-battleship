package com.example.miniproyect3fpoe.model;

public class Game implements IGame {
    public final HumanAdapter human;
    public final MachineAdapter machine;
    private String winner;

    /**
     * Constructor que inicializa el juego con un jugador humano y una máquina.
     * @param human El jugador humano.
     * @param machine El jugador máquina.
     */
    public Game(HumanAdapter human, MachineAdapter machine) {
        this.human = human;
        this.machine = machine;
        this.winner = null;
    }

    /**
     * Procesa el disparo realizado por el humano al tablero de la máquina.
     * @param row La fila del disparo.
     * @param col La columna del disparo.
     * @return El resultado del disparo ("miss", "hit", "sunk").
     */
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

    /**
     * Procesa el disparo realizado por la máquina al tablero del humano.
     * @param row La fila del disparo.
     * @param col La columna del disparo.
     * @return El resultado del disparo ("miss", "hit", "sunk").
     */
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


    /**
     * Verifica si alguno de los jugadores ha perdido todas sus naves.
     * @return true si hay un ganador, false en caso contrario.
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
     * Obtiene el ganador del juego. Lanza una excepción si aún no hay ganador.
     * @return El ganador ("Human" o "Machine").
     */
    @Override
    public String getWinner() {
        if (winner == null) {
            throw new IllegalStateException("No winner yet.");
        }
        return winner;
    }

    /**
     * Obtiene el tablero del jugador máquina.
     * @return El tablero de la máquina.
     */
    public Board getMachineBoard() {
        return machine.getBoard();
    }

    /**
     * Obtiene el tablero del jugador humano.
     * @return El tablero del humano.
     */
    public Board getHumanBoard() {
        return human.getBoard();
    }

    /**
     * Reinicia el juego para que ambos jugadores puedan jugar nuevamente.
     */
    public void resetGame() {
        human.getBoard().reset();
        machine.getBoard().reset();
        this.winner = null;
    }
}
