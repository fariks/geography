package com.maxifier.geography.interpolation.model;

import java.util.Collections;
import java.util.Map;

public class Grid {

    private Map<Point, Double> data;

    private Polygon boundaries;

    private int gridStep;

    private Point min;
    private Point max;
    private Double minHeight;
    private Double maxHeight;

    public Grid(Map<Point, Double> data, Polygon boundaries, int gridStep) {
        this.data = data;
        this.boundaries = boundaries;
        this.gridStep = gridStep;
    }

    public Map<Point, Double> getData() {
        return data;
    }

    public Polygon getBoundaries() {
        return boundaries;
    }

    public int getGridStep() {
        return gridStep;
    }

    public Point getMin() {
        if (min == null) {
            int x = Collections.min(boundaries.getBoundaryPoints(), (o1, o2) -> Integer.compare(o1.getX(), o2.getX())).getX();
            int y = Collections.min(boundaries.getBoundaryPoints(), (o1, o2) -> Integer.compare(o1.getY(), o2.getY())).getY();
            min = new Point(x, y);
        }
        return min;
    }

    public Point getMax() {
        if (max == null) {
            int x = Collections.max(boundaries.getBoundaryPoints(), (o1, o2) -> Integer.compare(o1.getX(), o2.getX())).getX();
            int y = Collections.max(boundaries.getBoundaryPoints(), (o1, o2) -> Integer.compare(o1.getY(), o2.getY())).getY();
            max = new Point(x, y);
        }
        return max;
    }

    public Double getMinHeight() {
        if (minHeight == null) {
            minHeight = Collections.min(data.values(), Double::compare);
        }
        return minHeight;
    }

    public Double getMaxHeight() {
        if (maxHeight == null) {
            maxHeight = Collections.max(data.values(), Double::compare);
        }
        return maxHeight;
    }
}
