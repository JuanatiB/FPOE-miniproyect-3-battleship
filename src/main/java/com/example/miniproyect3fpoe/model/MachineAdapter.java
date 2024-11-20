package com.example.miniproyect3fpoe.model;

import java.util.Random;

public class MachineAdapter extends PlayerAdapter {
    private static final int BOARD_SIZE = 10; // Tamaño fijo del tablero
    private Random random;

    public MachineAdapter(Board board) {
        super(board);
        this.random = new Random();
    }

    /**
     * Automatically places all ships on the board. Ships are placed in random valid positions.
     */
    public void placeShips() {
        for (Ship ship : ships) {
            boolean placed = false;

            while (!placed) {
                // Generate random starting row, column, and orientation
                int startRow = random.nextInt(BOARD_SIZE);
                int startCol = random.nextInt(BOARD_SIZE);
                boolean isHorizontal = random.nextBoolean();

                // Set the ship's orientation
                ship.setHorizontal(isHorizontal);

                // Try to place the ship on the board
                placed = board.placeShip(ship, startRow, startCol);
                if (!placed) {
                    // If the placement is invalid, retry with new random values
                    System.out.println("Machine failed to place " + ship.getName() + " at (" + startRow + ", " + startCol + "). Retrying...");
                }
            }
        }
        System.out.println("Machine placed all ships successfully!");
    }

    /**
     * Executes the machine player's turn. The row and column for the attack
     * are generated randomly.
     * @return A message indicating the result of the attack ("miss", "hit", or "sunk").
     */
    @Override
    public String playTurn(int row, int col, Board opponentBoard) {
        String result;

        // Intentar registrar un disparo en las coordenadas dadas
        result = opponentBoard.registerShot(row, col);

        // Si ya se había disparado en esa celda, retorna un mensaje indicativo
        if (result.equals("already shot")) {
            return "Invalid move, cell already shot.";
        }

        // Retorna el resultado del disparo
        return result;
    }


}
