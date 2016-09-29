package com.maxifier.geography.interpolation.nearestneighbors;

import java.util.*;

import com.google.inject.Inject;
import com.google.inject.name.Named;

import com.maxifier.geography.interpolation.GridInterpolator;
import com.maxifier.geography.interpolation.model.Grid;
import com.maxifier.geography.interpolation.model.Point;
import com.maxifier.geography.interpolation.model.Polygon;

public class NearestNeighborsGridInterpolator implements GridInterpolator {

    private final NeighborsSearcher neighborsSearcher;

    private final ValueCalculator valueCalculator;

    @Inject
    public NearestNeighborsGridInterpolator(NearestNeighborsSearcher neighborsSearcher,
                                            InverseDistanceWeightsCalculator valueCalculator) {
        this.neighborsSearcher = neighborsSearcher;
        this.valueCalculator = valueCalculator;
    }

    @Override
    public Grid interpolate(Grid initialGrid) throws InterruptedException {
        Point min = initialGrid.getMin();
        Point max = initialGrid.getMax();
        int h = initialGrid.getGridStep();
        Polygon boundaries = initialGrid.getBoundaries();
        Map<Point, Double> data = initialGrid.getData();
        double[][] workedGrid = getWorkedGrid(data, min, max);
        Map<Point, Double> interpolatedData = new HashMap<>();
        for (int i = min.getX(); i <= max.getX(); i+=h) {
            for (int j = min.getY(); j <= max.getY(); j+=h) {
                System.out.println(String.format("Point(%d,%d)", i, j));
                if (Thread.interrupted()) {
                    throw new InterruptedException("Grid interpolation has been interrupted");
                }
                Point currentPoint = new Point(i, j);
                if (boundaries.contains(currentPoint)) {
                    Double z = data.get(currentPoint);
                    if (z == null) {
                        z = calculateValue(workedGrid, i - min.getX(), j - min.getY());
                    }
                    interpolatedData.put(currentPoint, z);
                }
            }
        }
        return new Grid(interpolatedData, boundaries, h);
    }

    /**
     * Constructs a worked grid restricted by rectangle with min and max vertices
     * and shifted to (0,0). All points that don't have heights values initialized with -1.
     *
     * @param probeData points that have height's values
     * @param min minimal point on grid
     * @param max maximal point on grid
     * @return worked grid restricted by rectangle with min and max vertices and shifted to (0,0)
     */
    private double[][] getWorkedGrid(Map<Point, Double> probeData, Point min, Point max) {
        double[][] workedGrid = new double[max.getX() - min.getX() + 1][max.getY() - min.getY() + 1];
        for (int i = 0; i < workedGrid.length; i++) {
            for (int j = 0; j < workedGrid[0].length; j++) {
                workedGrid[i][j] = -1;
            }
        }
        for (Map.Entry<Point, Double> entry : probeData.entrySet())
        {
            Point point = entry.getKey();
            if (point.isBelongsRectangle(min, max)) {
                workedGrid[point.getX() - min.getX()][point.getY() - min.getY()] = entry.getValue();
            }
        }
        return workedGrid;
    }

    private double calculateValue(double[][] workedGrid, int x, int y) {
        return valueCalculator.calculateValueByNeighbors(
                neighborsSearcher.getNearestNeighbors(workedGrid, x, y)
        );
    }
}
