package ui.panels;

import static ui.CLIStrings.*;

public class OpponentTurnPanel extends GridPanel {

    public OpponentTurnPanel(Boolean successfulHit, String grid) {
        super();
        if (successfulHit == null) {
            this.message = MSG_OPP_TURN;
        }
        else if (successfulHit) {
            this.message = MSG_PRE_PLAYER_HIT + MSG_OPP_TURN;
        } else {
            this.message = MSG_PRE_PLAYER_MISS + MSG_OPP_TURN;
        }
        this.grid = grid;
        this.request = REQ_OPP_TURN;
        this.keys = "";
    }

    @Override
    public void setError(boolean error) {
        this.error = error;
        this.errorMessage = ERR_COORDINATE;
        this.message = MSG_OPP_TURN;
    }
}
