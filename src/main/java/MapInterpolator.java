import java.util.*;

import static java.lang.Math.*;
import static java.lang.Math.pow;

/**
 * Created by smirnov on 25.09.2016.
 */
public class MapInterpolator {

    public static final int NEIGHBORHOOD_COUNT = 3;

    public static final int MAX_DISTANCE_STEP = 5;

    private Map<Point2D, Double> zondData;

    private Polygon polygon;

    private int h;

    private int xMin;
    private int xMax;
    private int yMin;
    private int yMax;

    private double[][] A;

    public MapInterpolator(Map<Point2D, Double> zondData, Polygon polygon, int h) {
        this.zondData = zondData;
        this.polygon = polygon;
        this.h = h;
        initGridBoundaries();
    }

    private void initGridBoundaries() {
        Set<Point2D> allPoints = new HashSet<Point2D>(zondData.keySet());
        allPoints.addAll(polygon.getBoundaryPoints());
        xMin = Collections.min(allPoints, new Comparator<Point2D>() {
            public int compare(Point2D o1, Point2D o2) {
                return o1.getX() - o2.getX();
            }
        }).getX();
        xMax = Collections.max(allPoints, new Comparator<Point2D>() {
            public int compare(Point2D o1, Point2D o2) {
                return o1.getX() - o2.getX();
            }
        }).getX();
        yMin = Collections.min(allPoints, new Comparator<Point2D>() {
            public int compare(Point2D o1, Point2D o2) {
                return o1.getY() - o2.getY();
            }
        }).getY();
        yMax = Collections.max(allPoints, new Comparator<Point2D>() {
            public int compare(Point2D o1, Point2D o2) {
                return o1.getY() - o2.getY();
            }
        }).getY();
        A = new double[xMax - xMin + 1][yMax - yMin + 1];
        for (int i = 0; i < A.length; i++) {
            for (int j = 0; j < A[0].length; j++) {
                A[i][j] = -1;
            }
        }
        for (Map.Entry<Point2D, Double> entry : zondData.entrySet())
        {
            Point2D point2D = entry.getKey();
            A[point2D.getX() - xMin][point2D.getY() - yMin] = entry.getValue();
        }
    }

    public Map<Point2D, Double> getInterpolationData() {
        Map<Point2D, Double> res = new HashMap<Point2D, Double>();
        for (int i = xMin; i < xMax; i++) {
            for (int j = yMin; j < yMax; j++) {
                Point2D currentPoint = new Point2D(i, j);
                if (polygon.contains(currentPoint)) {
                    Double z = zondData.get(currentPoint);
                    if (z != null) {
                        res.put(currentPoint, z);
                    } else {
                        double value = calcValue(i - xMin, j - yMin);
                        res.put(currentPoint, value);
                        A[i - xMin][j - yMin] = value;
                    }
                }
            }
        }
        return res;
    }

    static class Neighbor {
        double height;
        double distance;

        public Neighbor(double height, double distance) {
            this.height = height;
            this.distance = distance;
        }
    }

    //fix bug with angle element
    public double calcValue(int x, int y) {
        List<Neighbor> neighbors = new ArrayList<Neighbor>();
        int k = 1;
        int n = xMax - xMin;
        int m = yMax - yMin;
        while (neighbors.size() < NEIGHBORHOOD_COUNT && (k < n || k < m)) {
            for (int i = max(0, x - k); i < min(n, x + k); i++) {
                if (y - k >= 0 && A[i][y - k] != -1) {
                    neighbors.add(new Neighbor(A[i][y - k], distance(x, y, i, y - k)));
                }
                if (y + k < m && A[i][y + k] != -1) {
                    neighbors.add(new Neighbor(A[i][y + k], distance(x, y, i, y + k)));
                }
            }
            for (int j = max(0, y - k); j < min(m, y + k); j++) {
                if (x - k >= 0 && A[x - k][j] != -1) {
                    neighbors.add(new Neighbor(A[x - k][j], distance(x, y, x - k, j)));
                }
                if (x + k < n && A[x + k][j] != -1) {
                    neighbors.add(new Neighbor(A[x + k][j], distance(x, y, x + k, j)));
                }
            }
            k++;
        }
        return calcValueByNeighbors(neighbors);
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
}
