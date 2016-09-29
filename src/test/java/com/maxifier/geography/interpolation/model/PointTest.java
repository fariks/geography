package com.maxifier.geography.interpolation.model;

import static com.maxifier.geography.GeographyModule.*;
import static java.lang.Math.*;
import static org.junit.Assert.*;

import org.junit.Test;

public class PointTest {

    @Test
    public void testDistance() {
        Point test = new Point(1,1);
        assertEquals(1, test.distance( new Point(1,2)), EPS);
        assertEquals(sqrt(2), test.distance(new Point(0,0)), EPS);
        assertEquals(sqrt(2) * 2, test.distance(new Point(-1,-1)), EPS);
        assertEquals(3, test.distance(new Point(1,4)), EPS);
    }

    @Test
    public void testIsBelongsRectangle() {
        Point test = new Point(1,1);
        assertTrue(test.isBelongsRectangle(new Point(0,0), new Point(2,2)));
        assertTrue(test.isBelongsRectangle(new Point(1,1), new Point(2,2)));
        assertTrue(test.isBelongsRectangle(new Point(0,0), new Point(1,1)));
        assertTrue(test.isBelongsRectangle(new Point(0,1), new Point(1,2)));
        assertFalse(test.isBelongsRectangle(new Point(-1,-1), new Point(0,0)));
        assertFalse(test.isBelongsRectangle(new Point(2,2), new Point(3,3)));
        assertFalse(test.isBelongsRectangle(new Point(2,0), new Point(4,6)));
    }
}