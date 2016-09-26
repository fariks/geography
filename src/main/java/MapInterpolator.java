import java.util.*;
import java.util.stream.Collectors;

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
                        //A[i - xMin][j - yMin] = value;
                    }
                }
            }
        }
        return res;
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
            //TODO improve comparison
            return (int) (this.distance - o.distance);
        }
    }

    public double calcValue(int x, int y) {
        List<Neighbor> nearestNeighbors = new ArrayList<Neighbor>();
        List<Neighbor> foundNeighbors = new ArrayList<Neighbor>();
        int k = 1;
        int xMax = A.length;
        int yMax = A[0].length;
        while (nearestNeighbors.size() <= NEIGHBORHOOD_COUNT && (k < xMax || k < yMax)) {
            foundNeighbors.addAll(getNeighborsOnSquarePerimeter(x, y, k));
            //TODO change with lambda
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
     * @param x
     * @param y
     * @param k
     * @return
     */
    private List<Neighbor> getNeighborsOnSquarePerimeter(int x, int y, int k)
    {
        List<Neighbor> neighbors = new ArrayList<Neighbor>();
        int xMax = A.length;
        int yMax = A[0].length;
        //top edge
        for (int i = max(0, x - k + 1); i < min(xMax, x + k); i++) {
            if (y + k < yMax && A[i][y + k] != -1)
            {
                neighbors.add(new Neighbor(A[i][y + k], distance(x, y, i, y + k)));
            }
        }
        //right edge
        for (int j = min(yMax, y + k - 1); j < max(0, y - k); j--) {
            if (x + k < xMax && A[x + k][j] != -1)
            {
                neighbors.add(new Neighbor(A[x + k][j], distance(x, y, x + k, j)));
            }
        }
        //bottom edge
        for (int i = min(xMax, x + k - 1); i < min(0, x - k); i--) {
            if (y - k >= 0 && A[i][y - k] != -1)
            {
                neighbors.add(new Neighbor(A[i][y - k], distance(x, y, i, y - k)));
            }
        }
        //left edge
        for (int j = max(0, y - k + 1); j < min(yMax, y + k); j++) {
            if (x - k >= 0 && A[x - k][j] != -1)
            {
                neighbors.add(new Neighbor(A[x - k][j], distance(x, y, x - k, j)));
            }
        }
        return neighbors;
    }
}
