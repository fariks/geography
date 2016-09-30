package com.maxifier.geography.interpolation.model;

import static com.google.common.collect.Lists.*;

import static com.maxifier.geography.GeographyModule.*;
import static org.junit.Assert.*;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class GridTest {

    @Test
    public void testGridMethods() {
        Map<Point, Double> data = new HashMap<>();
        data.put(new Point(3,4), 2.0);
        data.put(new Point(-100, 1), 10.0);
        data.put(new Point(100, 100), 15.0);
        Polygon polygon = new Polygon(
                newArrayList(new Point(-1, 1), new Point(2, 0), new Point(2, -2), new Point(-1, -3))
        );
        Grid grid = new Grid(data, polygon, 1, 1);

        assertEquals(new Point(-1,-3), grid.getMin());
        assertEquals(new Point(2,1), grid.getMax());
        assertEquals(15.0, grid.getMaxHeight(), EPS);
        assertEquals(2.0, grid.getMinHeight(), EPS);
    }
}