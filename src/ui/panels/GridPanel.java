package ui.panels;

import static ui.CLIStrings.PROMPT;
import static ui.CLIStrings.TITLE;

public class GridPanel extends CLIPanel {

    public enum Special {NONE, TOP, BOTTOM}

    protected String grid;
    protected Special special;
    protected String specialString;
    protected String request;


    public GridPanel() {
        super();
        this.grid = "";
        this.specialString = "";
        this.special = Special.NONE;
        this.request = "";
    }

    /**
     * Set a special message and alert the panel as to where the string
     * should be printed. The message will self-cancel after one call to
     * the toString() method.
     * @param specialString the string to be printed.
     * @param placement the desired position of the string.
     */
    public void setSpecial(String specialString, Special placement) {
        this.special = placement;
        this.specialString = specialString;
    }

    @Override
    public String toString() {
        StringBuilder panel = new StringBuilder("\n" + TITLE);
        if (error) {
            panel.append("\n\n").append(errorMessage);
            this.error = false;
        }
        if (special == Special.TOP) panel.append("\n\n").append(specialString);
        panel.append("\n\n").append(message);
        panel.append("\n\n").append(grid);
        if (special == Special.BOTTOM) panel.append("\n\n").append(specialString);
        panel.append("\n\n").append(request);
        if (!(keys.equals(""))) panel.append("\n\n").append(keys);
        panel.append("\n\n").append(PROMPT);

        if (special != Special.NONE) this.setSpecial("", Special.NONE);

        return panel.toString();
    }

}
