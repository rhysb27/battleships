package game;

import java.util.ArrayList;

import static game.Game.Winner.*;

public class Game {

    private PlayerGrid playerGrid;
    private Grid opponentGrid;
    private boolean playersTurn;
    private int turns;
    private Winner winner;

    public enum Ship {
        Gunship(2),
        Destroyer(3),
        Submarine(3),
        Battleship(4),
        Carrier(5);

        private final int size;

        Ship(int size) {
            this.size = size;
        }

        public int getSize() {
            return this.size;
        }
    }

    public enum Winner {
        No_Winner,
        Player_Win,
        Opponent_Win
    }

    public Game(){

        this.playerGrid = new PlayerGrid();
        this.opponentGrid = new Grid();
        this.playersTurn = true;
        this.turns = 0;
        this.winner = No_Winner;

    }

    public String playerGridToString() {
        return playerGrid.toString();
    }

    public String opponentGridToString() {
        return opponentGrid.toString();
    }

    public void setPlayersTurn(boolean playersTurn) {

        this.playersTurn = playersTurn;
    }

    public boolean isPlayersTurn() {
        return playersTurn;
    }

    public void clearPlayerShips() {
        playerGrid.init();
    }

    public boolean placeShip(Ship ship, String coordinate, boolean facingDown) {

        ArrayList<GridSquare> proposedSpaces = new ArrayList<>();
        GridSquare startSpace = new GridSquare(coordinate);
        proposedSpaces.add(startSpace);

        int x = startSpace.getX();
        int y = startSpace.getY();
        int offset = ship.getSize() - 1;
        boolean allValid = true;
        boolean success = true;

        if (facingDown) {
            while (offset > 0) {
                try {
                    GridSquare nextSpace = new GridSquare(x, y + offset);
                    proposedSpaces.add(nextSpace);
                    offset--;
                } catch (IllegalArgumentException e) {
                    allValid = false;
                    break;
                }
            }
        } else {
            while (offset >= 0) {
                try {
                    GridSquare nextSpace = new GridSquare(x + offset, y);
                    proposedSpaces.add(nextSpace);
                    offset--;
                } catch (IllegalArgumentException e) {
                    allValid = false;
                    break;
                }
            }
        }

        if (! allValid) {
            return false;
        }

        for (GridSquare space : proposedSpaces) {

            if (playerGrid.isShipAt(space)) {
                success = false;
                break;
            }
        }

        if (success) {
            playerGrid.placeShip(proposedSpaces);
        }

        return success;
    }

    public boolean turn() {

        boolean gameWon = isGameOver();

        if (gameWon) {
            return true;
        }

        playersTurn = !playersTurn;

        return false;
    }

    private boolean isGameOver() {

        if (playersTurn && opponentGrid.hasLost()) {
            this.winner = Player_Win;
            return true;
        }
        else if (!playersTurn && playerGrid.hasLost()) {
            this.winner = Opponent_Win;
            return true;
        }
        else {
            return false;
        }
    }

    public void registerPlayerStrike(GridSquare space, boolean hit) {

        opponentGrid.registerStrike(space, hit);
    }

    public boolean registerOpponentStrike(GridSquare space) {

        boolean hit = this.playerGrid.registerStrike(space);
        return hit;

    }

    public Winner getWinner() {
        return this.winner;
    }


    @Override
    public String toString() {

        String boardString = String.format(
                  "\n--- TURN %d ---"
                + "%s"
                + "\n ==================================================== "
                + "%s\n" ,
                turns, opponentGrid.toString(), playerGrid.toString()
        );

        return boardString;
    }
}
