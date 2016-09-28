package interpolation;

import static com.google.common.base.Preconditions.*;
import static java.lang.Math.sqrt;
import static util.GeometryHelper.distance;

import java.util.List;

/**
 * Created by smirnov on 25.09.2016.
 */
public class Polygon {

    private static final double EPS = 0.0001;

    private List<Point> boundaryPoints;

    public Polygon(List<Point> boundaryPoints) {
        checkArgument(boundaryPoints.size() >= 3, "Polygon must consist at least of three points");
        this.boundaryPoints = boundaryPoints;
    }

    public List<Point> getBoundaryPoints() {
        return boundaryPoints;
    }

    public boolean contains(Point test) {
        //check if point lies on polygon's edges
        for (int i = 0; i < boundaryPoints.size() - 1; i++) {
            if (isPointLiesOnLineSegment(boundaryPoints.get(i), boundaryPoints.get(i + 1), test)) {
                return true;
            }
        }
        if (isPointLiesOnLineSegment(boundaryPoints.get(boundaryPoints.size() - 1), boundaryPoints.get(0), test)) {
            return true;
        }
        //check if point lies inside polygon
        boolean result = false;
        for (int i = 0, j = boundaryPoints.size() - 1; i < boundaryPoints.size(); j = i++) {
            double yi = boundaryPoints.get(i).y;
            double yj = boundaryPoints.get(j).y;
            double xi = boundaryPoints.get(i).x;
            double xj = boundaryPoints.get(j).x;
            double x = test.x;
            double y = test.y;
            boolean isBetweenYValues = (yi > y) != (yj > y);
            if (isBetweenYValues) {
                double intersectionPoint = (xj - xi) * (y - yi) / (yj - yi) + xi;
                if (x < intersectionPoint) {
                    result = !result;
                }
            }
        }
        return result;
    }

    private boolean isPointLiesOnLineSegment(Point i, Point j, Point test) {
        double mustBeZero =
                distance(i.x, i.y, test.x, test.y) + distance(j.x, j.y, test.x, test.y) - distance(i.x, i.y, j.x, j.y);
        return mustBeZero > -EPS && mustBeZero < EPS;
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
