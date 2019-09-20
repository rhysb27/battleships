package ui;

public class CLIStrings {

    public static final String TITLE = "---------------------- Battleships! ----------------------";
    public static final String PROMPT = "  > ";

    public static final String MSG_PLACE_FIRST_SHIP = "It's time to place your ships!";
    public static final String MSG_PLACE_MID_SHIPS = "Please continue adding your ships.";
    public static final String MSG_PLACE_LAST_SHIP = "Last one!";
    public static final String MSG_PICK_FIRST_SHIP = MSG_PLACE_FIRST_SHIP;
    public static final String MSG_PICK_MID_SHIPS = MSG_PLACE_MID_SHIPS;
    public static final String MSG_TURN = "Whose turn is it first?";
    public static final String MSG_PLAYER_TURN = "Your Turn!";
    public static final String MSG_OPP_TURN = "Their Turn!";
    public static final String MSG_QUIT = "Are you sure you wish to quit?";

    public static final String MSG_PRE_PLAYER_HIT = "Nice! ";
    public static final String MSG_PRE_PLAYER_MISS = "Darn. ";
    public static final String MSG_PRE_OPP_HIT = "Ouch! ";
    public static final String MSG_PRE_OPP_MISS = "Phew. ";

    public static final String REQ_PLACE = "Place your %s (size %d) by entering the desired coordinates.\nYour %s is facing %s";
    public static final String REQ_PICK = "Please select one of the following ships to place:\n\n%s";
    public static final String REQ_PLAYER_TURN = "Please select a coordinate to fire upon!";
    public static final String REQ_PLAYER_TURN_EXT = "Hit? [Y] / [N]";
    public static final String REQ_OPP_TURN = "";

    public static final String KEYS_PICK = "[C]: Clear placements    [Q]: Quit";
    public static final String KEYS_PLACE = "[C]: Clear placements    [Q]: Quit    [T] Toggle Direction";
    public static final String KEYS_QUIT = "[Y] / <any key>";
    public static final String KEYS_TURN = "  [1] Mine\n  [2] Theirs";

    public static final String SPECIAL_PLACEMENTS_CLEARED = "***   Battleship placements cleared!   ***";

    public static final String ERR_DEFAULT = "***   Whoops! That wasn't an option.   ***";
    public static final String ERR_PLACEMENT = "Whoops! Looks like that ship doesn't fit there! Please try somewhere else.";
    public static final String ERR_COORDINATE = "***   Whoops! That wasn't a valid coordinate.   ***";

    public static final String DIR_ACROSS = "ACROSS";
    public static final String DIR_DOWN = "DOWN";

    public static final String SIMPLE_BYE = "------ Bye for now! ------";
}
