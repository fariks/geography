package interpolation;

import static com.google.common.base.Preconditions.*;

import com.google.common.base.Preconditions;

import java.util.List;

/**
 * Created by smirnov on 25.09.2016.
 */
public class Polygon {

    private List<Point2D> boundaryPoints;

    public Polygon(List<Point2D> boundaryPoints) {
        checkArgument(boundaryPoints.size() >= 3, "Polygon must consist at least of three points");
        this.boundaryPoints = boundaryPoints;
    }

    public List<Point2D> getBoundaryPoints() {
        return boundaryPoints;
    }

    public boolean contains(Point2D point) {
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
            if ((yi > y) != (yj > y) &&
                    (x < (xj - xi) * (y - yi) / (yj - yi) + xi)) {
                result = !result;
            }
        }
        return result;
    }

    public double[] getBoundaryPointsAs1DArray() {
        double[] res = new double[boundaryPoints.size() * 2];
        for (int i = 0; i < boundaryPoints.size(); i++) {
            Point2D point2D = boundaryPoints.get(i);
            res[i * 2] = point2D.getX();
            res[i * 2 + 1] = point2D.getY();
        }
        return res;
    }
}