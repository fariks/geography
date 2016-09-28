package interpolation;

import util.GeometryHelper;

import java.util.*;
import java.util.stream.Collectors;

import static java.lang.Math.*;
import static java.lang.Math.pow;
import static util.GeometryHelper.*;

/**
 * Created by smirnov on 25.09.2016.
 */
public class NearestNeighborsGridInterpolator implements GridInterpolator {

    public static final int NEIGHBORHOOD_COUNT = 3;

    public static final int MAX_SEARCH_RADIUS = 5;

    @Override
    public Grid interpolate(Grid initialGrid) throws InterruptedException {
        int xMin = initialGrid.getxMin();
        int xMax = initialGrid.getxMax();
        int yMin = initialGrid.getyMin();
        int yMax = initialGrid.getyMax();
        int h = initialGrid.getGridStep();
        Polygon boundaries = initialGrid.getBoundaries();
        Map<Point, Double> data = initialGrid.getData();
        double[][] workedGrid = getWorkedGrid(data, xMin, yMin, xMax, yMax);
        Map<Point, Double> interpolatedData = new HashMap<>();
        for (int i = xMin; i <= xMax; i+=h) {
            for (int j = yMin; j <= yMax; j+=h) {
                System.out.println(String.format("Point(%d,%d)", i, j));
                if (Thread.interrupted()) {
                    throw new InterruptedException("Grid interpolation has been interrupted");
                }
                Point currentPoint = new Point(i, j);
                if (boundaries.contains(currentPoint)) {
                    Double z = data.get(currentPoint);
                    if (z != null) {
                        interpolatedData.put(currentPoint, z);
                    } else {
                        double value = calculateValue(workedGrid, i - xMin, j - yMin);
                        interpolatedData.put(currentPoint, value);
                    }
                }
            }
        }
        return new Grid(interpolatedData, boundaries, h);
    }

    private double[][] getWorkedGrid(Map<Point, Double> probeData, int xMin, int yMin, int xMax, int yMax) {
        double[][] workedGrid = new double[xMax - xMin + 1][yMax - yMin + 1];
        for (int i = 0; i < workedGrid.length; i++) {
            for (int j = 0; j < workedGrid[0].length; j++) {
                workedGrid[i][j] = -1;
            }
        }
        for (Map.Entry<Point, Double> entry : probeData.entrySet())
        {
            Point point = entry.getKey();
            workedGrid[point.getX() - xMin][point.getY() - yMin] = entry.getValue();
        }
        return workedGrid;
    }

    public double calculateValue(double[][] workedGrid, int x, int y) {
        List<Neighbor> nearestNeighbors = new ArrayList<>();
        List<Neighbor> foundNeighbors = new ArrayList<>();
        int k = 1;
        int xMax = workedGrid.length;
        int yMax = workedGrid[0].length;
        while (nearestNeighbors.size() <= NEIGHBORHOOD_COUNT && (k < xMax || k < yMax)) {
            System.out.println("k=" + k);
            foundNeighbors.addAll(getNeighborsOnSquarePerimeter(workedGrid, x, y, k));
            Iterator<Neighbor> iterator = foundNeighbors.iterator();
            while (iterator.hasNext()) {
                Neighbor neighbor = iterator.next();
                if (neighbor.distance <= k + 1) {
                    iterator.remove();
                    nearestNeighbors.add(neighbor);
                }
            }
            k++;
        }
        return calcValueByNeighbors(nearestNeighbors.stream().sorted().limit(NEIGHBORHOOD_COUNT).collect(Collectors.<Neighbor>toList()));
    }

    private double calcValueByNeighbors(List<Neighbor> neighbors) {
        double sum = 0;
        double weigh_sum = 0;
        for (Neighbor neighbor : neighbors) {
            double weight = pow(1 / neighbor.distance, 2);
            weigh_sum += weight;
            sum += weight * neighbor.height;
        }
        return sum / weigh_sum;
    }

    /**
     * x---
     *     |
     *  ---|
     *
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

    static class Neighbor implements Comparable<Neighbor> {
        double height;
        double distance;

        public Neighbor(double height, double distance) {
            this.height = height;
            this.distance = distance;
        }

        @Override
        public int compareTo(Neighbor o) {
            return Double.compare(this.distance, o.distance);
        }
    }
}
