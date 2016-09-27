package interpolation;

import java.util.*;
import java.util.stream.Collectors;

import static java.lang.Math.*;
import static java.lang.Math.pow;

import interpolation.Point2D;
import interpolation.Polygon;

/**
 * Created by smirnov on 25.09.2016.
 */
public class NearestNeighborsGridInterpolator implements GridInterpolator {

    public static final int NEIGHBORHOOD_COUNT = 3;

    public static final int MAX_SEARCH_RADIUS = 5;

    @Override
    public Map<Point2D, Double>  interpolate(Map<Point2D, Double> zondData, Polygon polygon, int h) {
        Set<Point2D> allPoints = new HashSet<>(zondData.keySet());
        allPoints.addAll(polygon.getBoundaryPoints());
        int xMin = Collections.min(allPoints, (o1, o2) -> Integer.compare(o1.getX(), o2.getX())).getX();
        int xMax = Collections.max(allPoints, (o1, o2) -> Integer.compare(o1.getX(), o2.getX())).getX();
        int yMin = Collections.min(allPoints, (o1, o2) -> Integer.compare(o1.getY(), o2.getY())).getY();
        int yMax = Collections.max(allPoints, (o1, o2) -> Integer.compare(o1.getY(), o2.getY())).getY();
        double[][] workedGrid = getWorkedGrid(zondData, xMin, yMin, xMax, yMax);
        Map<Point2D, Double> interpolatedData = new HashMap<>();
        for (int i = xMin; i <= xMax; i++) {
            for (int j = yMin; j <= yMax; j++) {
                Point2D currentPoint = new Point2D(i, j);
                if (polygon.contains(currentPoint)) {
                    Double z = zondData.get(currentPoint);
                    if (z != null) {
                        interpolatedData.put(currentPoint, z);
                    } else {
                        double value = calculateValue(workedGrid, i - xMin, j - yMin);
                        interpolatedData.put(currentPoint, value);
                    }
                }
            }
        }
        return interpolatedData;
    }

    private double[][] getWorkedGrid(Map<Point2D, Double> zondData, int xMin, int yMin, int xMax, int yMax) {
        double[][] workedGrid = new double[xMax - xMin + 1][yMax - yMin + 1];
        for (int i = 0; i < workedGrid.length; i++) {
            for (int j = 0; j < workedGrid[0].length; j++) {
                workedGrid[i][j] = -1;
            }
        }
        for (Map.Entry<Point2D, Double> entry : zondData.entrySet())
        {
            Point2D point2D = entry.getKey();
            workedGrid[point2D.getX() - xMin][point2D.getY() - yMin] = entry.getValue();
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
            foundNeighbors.addAll(getNeighborsOnSquarePerimeter(workedGrid, x, y, k));
            //TODO change with stream
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

    private double distance(int x1, int y1, int x2, int y2) {
        return sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
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
    private List<Neighbor> getNeighborsOnSquarePerimeter(double[][] workedGrid, int x, int y, int k)
    {
        List<Neighbor> neighbors = new ArrayList<Neighbor>();
        int xMax = workedGrid.length;
        int yMax = workedGrid[0].length;
        //top edge
        for (int i = max(0, x - k + 1); i < min(xMax, x + k); i++) {
            if (y + k < yMax && workedGrid[i][y + k] != -1)
            {
                neighbors.add(new Neighbor(workedGrid[i][y + k], distance(x, y, i, y + k)));
            }
        }
        //right edge
        for (int j = min(yMax, y + k - 1); j < max(0, y - k); j--) {
            if (x + k < xMax && workedGrid[x + k][j] != -1)
            {
                neighbors.add(new Neighbor(workedGrid[x + k][j], distance(x, y, x + k, j)));
            }
        }
        //bottom edge
        for (int i = min(xMax, x + k - 1); i < min(0, x - k); i--) {
            if (y - k >= 0 && workedGrid[i][y - k] != -1)
            {
                neighbors.add(new Neighbor(workedGrid[i][y - k], distance(x, y, i, y - k)));
            }
        }
        //left edge
        for (int j = max(0, y - k + 1); j < min(yMax, y + k); j++) {
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
