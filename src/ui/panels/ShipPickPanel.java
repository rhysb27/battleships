package ui.panels;

import static ui.CLIStrings.*;
import static ui.panels.ShipPickPanel.PickerPhase.*;

public class ShipPickPanel extends GridPanel {

    public enum PickerPhase {FIRST, INTERMEDIATE}

    public ShipPickPanel() {
        super();
        this.keys = KEYS_PICK;
    }

    public void configure(String grid, String shipPicker, PickerPhase phase,
                          boolean error) {
        this.grid = grid;
        this.error = error;

        if (phase == FIRST) {
            this.message = MSG_PICK_FIRST_SHIP;
        }
        else {
            this.message = MSG_PICK_MID_SHIPS;
        }

        this.request = String.format(REQ_PICK, shipPicker);
    }
}
