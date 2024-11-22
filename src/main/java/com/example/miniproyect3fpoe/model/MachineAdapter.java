package com.example.miniproyect3fpoe.model;

import java.util.Random;

/**
 * Represents a machine player in the game. Handles automatic placement of ships
 * and generates random attacks during the game.
 */
public class MachineAdapter extends PlayerAdapter {
    public static final int BOARD_SIZE = 10;
    private final Random random;

    /**
     * Constructs a MachineAdapter with the specified board.
     *
     * @param board the board associated with the machine player
     */
    public MachineAdapter(Board board) {
        super(board);
        this.random = new Random();
    }

    /**
     * Retrieves the board associated with the machine player.
     *
     * @return the machine's board
     */
    public Board getBoard() {
        return super.getBoard();
    }

    /**
     * Automatically places all ships on the board.
     * Ships are placed in random valid positions with random orientations.
     */
    public void placeShips() {
        for (Ship ship : ships) {
            boolean placed = false;

            while (!placed) {
                int startRow = random.nextInt(BOARD_SIZE);
                int startCol = random.nextInt(BOARD_SIZE);
                boolean isHorizontal = random.nextBoolean();

                ship.setHorizontal(isHorizontal);
                placed = board.placeShip(ship, startRow, startCol);

                if (!placed) {
                    System.out.println("Machine failed to place " + ship.getName() + " at (" + startRow + ", " + startCol + "). Retrying...");
                }
            }
        }
        System.out.println("Machine placed all ships successfully!");
    }

    /**
     * Executes the machine player's turn by attacking a specified cell on the opponent's board.
     *
     * @param row           the row to attack
     * @param col           the column to attack
     * @param opponentBoard the opponent's board to attack
     * @return a message indicating the result of the attack ("miss", "hit", "sunk", or "invalid")
     */
    @Override
    public String playTurn(int row, int col, Board opponentBoard) {
        String result = opponentBoard.registerShot(row, col);

        if (result.equals("already shot")) {
            return "Invalid move, cell already shot.";
        }

        return result;
    }
}
