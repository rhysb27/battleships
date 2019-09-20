package game;

import java.util.EnumSet;

import static game.GridSquareState.Hit;
import static game.GridSquareState.Miss;

public class Grid {

    protected GridSquareState[][] grid;
    public static final int BOARD_WIDTH = 10;
    public static final int BOARD_HEIGHT = 10;
    private int hitsRemaining;

    /**
     *
     */
    Grid() {

        this.grid = new GridSquareState[BOARD_WIDTH][BOARD_HEIGHT];
        init();
        this.hitsRemaining = EnumSet.allOf(Game.Ship.class).stream().mapToInt(Game.Ship::getSize).sum();
    }

    protected void init() {
        for (int y = 0; y < BOARD_HEIGHT; y++) {

            for (int x = 0; x < BOARD_WIDTH; x++) {
                grid[x][y] = GridSquareState.Empty;
            }
        }
    }


    void registerStrike(GridSquare space, boolean hit) {
        if (hit) {
            grid[space.getX()][space.getY()] = Hit;
            registerHit();
        } else {
            grid[space.getX()][space.getY()] = Miss;
        }
    }

    protected void registerHit(){
        this.hitsRemaining--;
    }


    boolean hasLost() {
        return hitsRemaining == 0;
    }

    @Override
    public String toString() {

        return String.format(
                          "      -A-  -B-  -C-  -D-  -E-  -F-  -G-  -H-  -I-  -J-     "
                        + "\n -1- [%s ][%s ][%s ][%s ][%s ][%s ][%s ][%s ][%s ][%s ] -1-"
                        + "\n -2- [%s ][%s ][%s ][%s ][%s ][%s ][%s ][%s ][%s ][%s ] -2-"
                        + "\n -3- [%s ][%s ][%s ][%s ][%s ][%s ][%s ][%s ][%s ][%s ] -3-"
                        + "\n -4- [%s ][%s ][%s ][%s ][%s ][%s ][%s ][%s ][%s ][%s ] -4-"
                        + "\n -5- [%s ][%s ][%s ][%s ][%s ][%s ][%s ][%s ][%s ][%s ] -5-"
                        + "\n -6- [%s ][%s ][%s ][%s ][%s ][%s ][%s ][%s ][%s ][%s ] -6-"
                        + "\n -7- [%s ][%s ][%s ][%s ][%s ][%s ][%s ][%s ][%s ][%s ] -7-"
                        + "\n -8- [%s ][%s ][%s ][%s ][%s ][%s ][%s ][%s ][%s ][%s ] -8-"
                        + "\n -9- [%s ][%s ][%s ][%s ][%s ][%s ][%s ][%s ][%s ][%s ] -9-"
                        + "\n 10- [%s ][%s ][%s ][%s ][%s ][%s ][%s ][%s ][%s ][%s ] -10"
                        + "\n      -A-  -B-  -C-  -D-  -E-  -F-  -G-  -H-  -I-  -J-     ",
                grid[0][0].v(), grid[1][0].v(), grid[2][0].v(), grid[3][0].v(), grid[4][0].v(),
                grid[5][0].v(), grid[6][0].v(), grid[7][0].v(), grid[8][0].v(), grid[9][0].v(),
                grid[0][1].v(), grid[1][1].v(), grid[2][1].v(), grid[3][1].v(), grid[4][1].v(),
                grid[5][1].v(), grid[6][1].v(), grid[7][1].v(), grid[8][1].v(), grid[9][1].v(),
                grid[0][2].v(), grid[1][2].v(), grid[2][2].v(), grid[3][2].v(), grid[4][2].v(),
                grid[5][2].v(), grid[6][2].v(), grid[7][2].v(), grid[8][2].v(), grid[9][2].v(),
                grid[0][3].v(), grid[1][3].v(), grid[2][3].v(), grid[3][3].v(), grid[4][3].v(),
                grid[5][3].v(), grid[6][3].v(), grid[7][3].v(), grid[8][3].v(), grid[9][3].v(),
                grid[0][4].v(), grid[1][4].v(), grid[2][4].v(), grid[3][4].v(), grid[4][4].v(),
                grid[5][4].v(), grid[6][4].v(), grid[7][4].v(), grid[8][4].v(), grid[9][4].v(),
                grid[0][5].v(), grid[1][5].v(), grid[2][5].v(), grid[3][5].v(), grid[4][5].v(),
                grid[5][5].v(), grid[6][5].v(), grid[7][5].v(), grid[8][5].v(), grid[9][5].v(),
                grid[0][6].v(), grid[1][6].v(), grid[2][6].v(), grid[3][6].v(), grid[4][6].v(),
                grid[5][6].v(), grid[6][6].v(), grid[7][6].v(), grid[8][6].v(), grid[9][6].v(),
                grid[0][7].v(), grid[1][7].v(), grid[2][7].v(), grid[3][7].v(), grid[4][7].v(),
                grid[5][7].v(), grid[6][7].v(), grid[7][7].v(), grid[8][7].v(), grid[9][7].v(),
                grid[0][8].v(), grid[1][8].v(), grid[2][8].v(), grid[3][8].v(), grid[4][8].v(),
                grid[5][8].v(), grid[6][8].v(), grid[7][8].v(), grid[8][8].v(), grid[9][8].v(),
                grid[0][9].v(), grid[1][9].v(), grid[2][9].v(), grid[3][9].v(), grid[4][9].v(),
                grid[5][9].v(), grid[6][9].v(), grid[7][9].v(), grid[8][9].v(), grid[9][9].v()
        );
    }

}
