package com.maxifier.geography.interpolation.model;

import static com.google.common.collect.Lists.*;
import static org.junit.Assert.*;

import org.junit.Test;

public class PolygonTest {

    private Polygon convexPolygon = new Polygon(
            newArrayList(new Point(1, 2), new Point(3, 6), new Point(5, 3), new Point(5, 1), new Point(2, 1))
    );

    private Polygon nonConvexPolygon = new Polygon(
            newArrayList(new Point(1, 2), new Point(3, 6), new Point(7, 5), new Point(7, 2), new Point(3, 4))
    );

    @Test
    public void testContainsPointsOutsideConvexPolygon() {
        assertFalse(convexPolygon.contains(new Point(0, 2)));
        assertFalse(convexPolygon.contains(new Point(3, 0)));
        assertFalse(convexPolygon.contains(new Point(1, 3)));
        assertFalse(convexPolygon.contains(new Point(4, 6)));
    }

    @Test
    public void testContainsPointsInsideConvexPolygon() {
        assertTrue(convexPolygon.contains(new Point(2, 2)));
        assertTrue(convexPolygon.contains(new Point(3, 3)));
        assertTrue(convexPolygon.contains(new Point(3, 5)));
        assertTrue(convexPolygon.contains(new Point(4, 2)));
    }

    @Test
    public void testContainsPointsOnConvexPolygonEdges() {
        assertTrue(convexPolygon.contains(new Point(3, 1)));
        assertTrue(convexPolygon.contains(new Point(5, 2)));
        assertTrue(convexPolygon.contains(new Point(1, 2)));
        assertTrue(convexPolygon.contains(new Point(3, 6)));
        assertTrue(convexPolygon.contains(new Point(5, 1)));
    }

    @Test
    public void testContainsPointsOutsideNonConvexPolygon() {
        assertFalse(nonConvexPolygon.contains(new Point(1, 1)));
        assertFalse(nonConvexPolygon.contains(new Point(2, 5)));
        assertFalse(nonConvexPolygon.contains(new Point(3, 3)));
        assertFalse(nonConvexPolygon.contains(new Point(7, 6)));
    }

    @Test
    public void testContainsPointsInsideNonConvexPolygon() {
        assertTrue(nonConvexPolygon.contains(new Point(3, 5)));
        assertTrue(nonConvexPolygon.contains(new Point(4, 5)));
        assertTrue(nonConvexPolygon.contains(new Point(5, 4)));
        assertTrue(nonConvexPolygon.contains(new Point(6, 5)));
    }

    @Test
    public void testContainsPointsOnNonConvexPolygonEdges() {
        assertTrue(nonConvexPolygon.contains(new Point(1, 2)));
        assertTrue(nonConvexPolygon.contains(new Point(7, 3)));
        assertTrue(nonConvexPolygon.contains(new Point(7, 2)));
        assertTrue(nonConvexPolygon.contains(new Point(3, 6)));
        assertTrue(nonConvexPolygon.contains(new Point(3, 4)));
    }

    @Test
    public void testContainsPointsOnSquareEdges() {
        Polygon square = new Polygon(newArrayList(new Point(0, 0), new Point(0, 4), new Point(4, 4), new Point(4, 0)));
        assertTrue(square.contains(new Point(0, 0)));
        assertTrue(square.contains(new Point(0, 2)));
        assertTrue(square.contains(new Point(0, 4)));
        assertTrue(square.contains(new Point(1, 4)));
        assertTrue(square.contains(new Point(3, 0)));
        assertTrue(square.contains(new Point(4, 2)));
        assertFalse(square.contains(new Point(5, 0)));
        assertFalse(square.contains(new Point(0, 5)));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testTryCreatePolygonWithTwoPoints() {
        new Polygon(newArrayList(new Point(1, 2), new Point(3, 6)));
    }

    @Test
    public void testTryCreatePolygonWithThreePoints() {
        new Polygon(newArrayList(new Point(1, 2), new Point(3, 6), new Point(5, 3)));
    }
}