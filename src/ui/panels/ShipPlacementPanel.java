package ui.panels;

import game.Game.Ship;

import static ui.panels.ShipPlacementPanel.PlacementPhase.*;
import static ui.CLIStrings.*;

public class ShipPlacementPanel extends GridPanel {

    public enum PlacementPhase {FIRST, INTERMEDIATE, LAST}
    private Ship ship;
    private String shipDirection;

    public ShipPlacementPanel() {
        super();
        this.shipDirection = DIR_DOWN;
        this.keys = KEYS_PLACE;
    }

    public void toggleShipDirection() {
        if (shipDirection.equals(DIR_DOWN)) {
            this.shipDirection = DIR_ACROSS;
        } else {
            this.shipDirection = DIR_DOWN;
        }

        this.request = String.format(REQ_PLACE, ship.name(), ship.getSize(),
                ship.name(), shipDirection);

    }

    public void configure(String grid, Ship ship, PlacementPhase phase) {
        this.grid = grid;

        if (phase == FIRST) {
            this.message = MSG_PLACE_FIRST_SHIP;
        }
        else if (phase == INTERMEDIATE) {
            this.message = MSG_PLACE_MID_SHIPS;
        }
        else {
            this.message = MSG_PLACE_LAST_SHIP;
        }

        this.ship = ship;
        this.request = String.format(REQ_PLACE, ship.name(), ship.getSize(),
                ship.name(), shipDirection);
    }
}
