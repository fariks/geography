package com.maxifier.geography.interpolation.nearestneighbors;

import static com.maxifier.geography.interpolation.model.Point.distance;
import static java.lang.Math.max;
import static java.lang.Math.min;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class NearestNeighborsSearcher implements NeighborsSearcher {

    @Override
    public List<Neighbor> getNearestNeighbors(double[][] workedGrid, int x, int y, int neighborCount) {
        List<Neighbor> nearestNeighbors = new ArrayList<>();
        List<Neighbor> foundNeighbors = new ArrayList<>();
        int k = 1;
        int xMax = workedGrid.length;
        int yMax = workedGrid[0].length;
        while (nearestNeighbors.size() < neighborCount && (k < xMax || k < yMax)) {
            System.out.println("k=" + k);
            foundNeighbors.addAll(getNeighborsOnSquarePerimeter(workedGrid, x, y, k));
            Iterator<Neighbor> iterator = foundNeighbors.iterator();
            while (iterator.hasNext()) {
                Neighbor neighbor = iterator.next();
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
     *
     *
     * ∧ s - - >
     * |        |
     * |   c    |
     * |        |
     * < - - - ∨
     *
     * @param workedGrid
     * @param x
     * @param y
     * @param k
     * @return
     */
    public List<Neighbor> getNeighborsOnSquarePerimeter(double[][] workedGrid, int x, int y, int k)
    {
        List<Neighbor> neighbors = new ArrayList<Neighbor>();
        int xMax = workedGrid.length - 1;
        int yMax = workedGrid[0].length - 1;
        //top edge
        for (int i = max(0, x - k + 1); i <= min(xMax, x + k); i++) {
            if (y + k < yMax && workedGrid[i][y + k] != -1)
            {
                neighbors.add(new Neighbor(workedGrid[i][y + k], distance(x, y, i, y + k)));
            }
        }
        //right edge
        for (int j = min(yMax, y + k - 1); j >= max(0, y - k); j--) {
            if (x + k < xMax && workedGrid[x + k][j] != -1)
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
