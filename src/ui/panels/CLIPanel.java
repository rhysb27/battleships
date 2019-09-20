package ui.panels;

import static ui.CLIStrings.*;

public class CLIPanel {

    protected String message;
    protected String keys;
    protected boolean error;
    protected String errorMessage;

    public CLIPanel() {
        this.message = "";
        this.keys = "";
        this.error = false;
    }

    /**
     * Sets the panel's error flag and loads the default error message.
     * Note that the error flag will reset to false after one call to the toString() method.
     * @param error the desired value of the error flag.
     */
    public void setError(boolean error) {
        this.error = error;
        this.errorMessage = ERR_DEFAULT;
    }

    /**
     * Sets the panel's error flag and loads the provided error message.
     * Note that the error flag will reset to false after one call to the toString() method.
     * @param error the desired value of the error flag.
     * @param message the desired error message.
     */
    public void setError(boolean error, String message) {
        this.error = error;
        this.errorMessage = message;
    }

    @Override
    public String toString() {
        StringBuilder panel = new StringBuilder("\n" + TITLE);
        if (error) {
            panel.append("\n\n").append(errorMessage);
            error = false;
        }
        panel.append("\n\n").append(message);
        panel.append("\n").append(keys);
        panel.append("\n\n").append(PROMPT);

        return panel.toString();
    }
}
