package game;

import java.util.ArrayList;

import static game.GridSquareState.*;

public class PlayerGrid extends Grid {


    PlayerGrid() {
        super();
    }

    @Override
    public void init() {
        super.init();
    }

    boolean isShipAt(GridSquare square) {
        int x = square.getX();
        int y = square.getY();

        return (grid[x][y] == Ship || grid[x][y] == Hit);
    }


    void placeShip(ArrayList<GridSquare> spaces) {

        int x;
        int y;

        for (GridSquare space : spaces) {
            x = space.getX();
            y = space.getY();

            grid[x][y] = Ship;
        }
    }

    boolean registerStrike(GridSquare space) {

        boolean hit = isShipAt(space);

        if (hit) {
            this.grid[space.getX()][space.getY()] = Hit;
            registerHit();
        } else {
            this.grid[space.getX()][space.getY()] = Miss;
        }

        return hit;
    }
}
