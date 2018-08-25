package game;

import org.junit.Test;

import static org.junit.Assert.*;

public class GridSquareTest {

    private GridSquare testee;

    @Test(expected = IllegalArgumentException.class)
    public void illegalConstructionLowXTest() {

        this.testee = new GridSquare(0, 'A');

    }

    @Test
    public void legalConstructionTest() {

        this.testee = new GridSquare(1, 'A');
    }


    @Test
    public void toStringTest() {

        this.testee = new GridSquare(1, 'A');
        assertEquals("1A", testee.toString());
    }
}