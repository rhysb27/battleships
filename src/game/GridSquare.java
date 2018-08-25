package game;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GridSquare {

    private final int X;
    private final int Y;
    public static final String COORDINATE_PATTERN = "^[A-Ja-j](10|[1-9])$";


    public GridSquare(int x, int y) throws IllegalArgumentException {

        if ((x >= 0) && (x < Grid.BOARD_WIDTH) && (y >= 0) && (y < Grid.BOARD_HEIGHT)) {
            this.X = x;
            this.Y = y;
        } else {
            String message = String.format("%d%d is not a valid grid co-ordinate.", x, y);
            throw new IllegalArgumentException(message);
        }
    }

    public GridSquare(String coordinate) throws IllegalArgumentException {

        if (! isValidCoordinate(coordinate)) {
            throw new IllegalArgumentException(coordinate + " is not a valid coordinate.");
        }

        this.X = "ABCDEFGHIJ".indexOf(Character.toUpperCase(coordinate.charAt(0)));

        if (coordinate.length() == 2) {
            this.Y = Character.getNumericValue(coordinate.charAt(1)) - 1;
        } else {
            this.Y = 9;  // Translate from user-coordinate value 10 to indexed-by-zero grid coordinate.
        }
    }

    public int getX() {
        return X;
    }

    public int getY() {
        return Y;
    }

    public static boolean isValidCoordinate(String coordinate){
        Pattern pattern = Pattern.compile(COORDINATE_PATTERN);
        Matcher matcher = pattern.matcher(coordinate);

        return matcher.matches();
    }

    @Override
    public String toString() {

        return String.format("(%d, %d)", X, Y);
    }
}
