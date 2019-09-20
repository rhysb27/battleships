package ui.panels;

import org.jetbrains.annotations.Nullable;

import static ui.CLIStrings.*;

public class PlayerTurnPanel extends GridPanel {

    public PlayerTurnPanel(@Nullable Boolean successfulHit, String grid) {
        super();

        if (successfulHit == null) {
            this.message = MSG_PLAYER_TURN;
        }
        else if (successfulHit) {
            this.message = MSG_PRE_OPP_HIT + MSG_PLAYER_TURN;
        } else {
            this.message = MSG_PRE_OPP_MISS + MSG_PLAYER_TURN;
        }
        this.grid = grid;
        this.request = REQ_PLAYER_TURN;
        this.keys = "";
    }

    @Override
    public void setError(boolean error) {
        this.error = error;
        this.errorMessage = ERR_COORDINATE;
        this.message = MSG_PLAYER_TURN;
    }

    public void triggerExtension(String response) {
        this.keys = PROMPT + response + "\n\n" + REQ_PLAYER_TURN_EXT;
    }
}
