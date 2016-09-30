package com.maxifier.geography.interpolation.nearestneighbors;

import static com.maxifier.geography.GeographyModule.*;
import static com.maxifier.geography.interpolation.model.Point.distance;
import static java.lang.Math.max;
import static java.lang.Math.min;

import com.maxifier.geography.GeographyModule;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class NearestNeighborsSearcher implements NeighborsSearcher {

    /**
     * Returns nearest neighbors of point with coordinates (x,y)
     *
     * @param workedGrid grid for search neighbors
     * @param x x coordinate of point
     * @param y y coordinate of point
     * @param neighborCount count of neighbors to search
     * @param searchRadius radius for neighbors search
     * @return neighbors of point with coordinates (x,y)
     */
    @Override
    public List<Neighbor> getNeighbors(double[][] workedGrid, int x, int y, int neighborCount, int searchRadius) {
        List<Neighbor> nearestNeighbors = new ArrayList<>();
        List<Neighbor> foundNeighbors = new ArrayList<>();
        int k = 1;
        int xMax = workedGrid.length;
        int yMax = workedGrid[0].length;
        while (nearestNeighbors.size() < neighborCount && k <= searchRadius && (k < xMax || k < yMax)) {
            foundNeighbors.addAll(getNeighborsOnSquarePerimeter(workedGrid, x, y, k));
            Iterator<Neighbor> iterator = foundNeighbors.iterator();
            while (iterator.hasNext()) {
                Neighbor neighbor = iterator.next();
                if (neighbor.getDistance() > searchRadius) {
                    iterator.remove();
                    continue;
                }
                if (neighbor.getDistance() <= k + 1) {
                    iterator.remove();
                    nearestNeighbors.add(neighbor);
                }
            }
            k++;
        }
        if (nearestNeighbors.size() < neighborCount) {
            nearestNeighbors.addAll(foundNeighbors);
        }
        return nearestNeighbors.stream().sorted().limit(neighborCount).collect(Collectors.<Neighbor>toList());
    }

    /**
     * Returns neighbors of point with coordinates (x,y) on square perimeter.
     * Using following search pattern for point c starting from s and moving clockwise:
     * ∧ s - - >
     * |        |
     * |   c    |
     * |        |
     * < - - - ∨
     *
     * @param workedGrid grid for search neighbors
     * @param x x coordinate of point
     * @param y y coordinate of point
     * @param k half of square edge
     * @return neighbors of point with coordinates (x,y) on square perimeter
     */
    public List<Neighbor> getNeighborsOnSquarePerimeter(double[][] workedGrid, int x, int y, int k)
    {
        List<Neighbor> neighbors = new ArrayList<Neighbor>();
        int xMax = workedGrid.length - 1;
        int yMax = workedGrid[0].length - 1;
        //top edge
        for (int i = max(0, x - k + 1); i <= min(xMax, x + k); i++) {
            if (y + k <= yMax && workedGrid[i][y + k] != -1)
            {
                neighbors.add(new Neighbor(workedGrid[i][y + k], distance(x, y, i, y + k)));
            }
        }
        //right edge
        for (int j = min(yMax, y + k - 1); j >= max(0, y - k); j--) {
            if (x + k <= xMax && workedGrid[x + k][j] != -1)
            {
                neighbors.add(new Neighbor(workedGrid[x + k][j], distance(x, y, x + k, j)));
            }
        }
        //bottom edge
        for (int i = min(xMax, x + k - 1); i >= max(0, x - k); i--) {
            if (y - k >= 0 && workedGrid[i][y - k] != -1)
            {
                neighbors.add(new Neighbor(workedGrid[i][y - k], distance(x, y, i, y - k)));
            }
        }
        //left edge
        for (int j = max(0, y - k + 1); j <= min(yMax, y + k); j++) {
            if (x - k >= 0 && workedGrid[x - k][j] != -1)
            {
                neighbors.add(new Neighbor(workedGrid[x - k][j], distance(x, y, x - k, j)));
            }
        }
        return neighbors;
    }
}
