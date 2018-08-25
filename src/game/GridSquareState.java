package game;

public enum GridSquareState {

    Empty ("  "),
    Ship  (" *"),
    Hit   (" X"),
    Miss  (" O");

    private final String displayRep;

    GridSquareState(String displayRep) {
        this.displayRep = displayRep;
    }

    @Override
    public String toString() {
        return this.displayRep;
    }

    public String v() {
        return this.displayRep;
    }



}
