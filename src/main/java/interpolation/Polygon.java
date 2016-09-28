package interpolation;

import static com.google.common.base.Preconditions.*;

import java.util.List;

/**
 * Created by smirnov on 25.09.2016.
 */
public class Polygon {

    private static final double EPS = 0.01;

    private List<Point> boundaryPoints;

    public Polygon(List<Point> boundaryPoints) {
        checkArgument(boundaryPoints.size() >= 3, "Polygon must consist at least of three points");
        this.boundaryPoints = boundaryPoints;
    }

    public List<Point> getBoundaryPoints() {
        return boundaryPoints;
    }

    public boolean contains(Point point) {
        if (boundaryPoints.contains(point)) {
            return true;
        }
        boolean result = false;
        for (int i = 0, j = boundaryPoints.size() - 1; i < boundaryPoints.size(); j = i++) {
            double yi = boundaryPoints.get(i).y;
            double yj = boundaryPoints.get(j).y;
            double xi = boundaryPoints.get(i).x;
            double xj = boundaryPoints.get(j).x;
            double x = point.x;
            double y = point.y;
            boolean isBetweenYValues = (yi > y) != (yj > y);
            if (isBetweenYValues) {
                double intersectionPoint = (xj - xi) * (y - yi) / (yj - yi) + xi;
                if (x < intersectionPoint + EPS) {
                    result = !result;
                }
            }
        }
        return result;
    }

    public double[] getBoundariesPointsAs1DArray() {
        double[] res = new double[boundaryPoints.size() * 2];
        for (int i = 0; i < boundaryPoints.size(); i++) {
            Point point = boundaryPoints.get(i);
            res[i * 2] = point.getX();
            res[i * 2 + 1] = point.getY();
        }
        return res;
    }
}
