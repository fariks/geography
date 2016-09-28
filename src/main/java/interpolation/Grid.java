package interpolation;

import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by alsm0813 on 28.09.2016.
 */
public class Grid {

    private Map<Point, Double> data;

    private Polygon boundaries;

    private int gridStep;

    private int xMin;
    private int xMax;
    private int yMin;
    private int yMax;
    private double zMin;
    private double zMax;

    public Grid(Map<Point, Double> data, Polygon boundaries, int gridStep) {
        this.data = data;
        this.boundaries = boundaries;
        this.gridStep = gridStep;
        Set<Point> allPoints = new HashSet<>(data.keySet());
        allPoints.addAll(boundaries.getBoundaryPoints());
        xMin = Collections.min(allPoints, (o1, o2) -> Integer.compare(o1.getX(), o2.getX())).getX();
        xMax = Collections.max(allPoints, (o1, o2) -> Integer.compare(o1.getX(), o2.getX())).getX();
        yMin = Collections.min(allPoints, (o1, o2) -> Integer.compare(o1.getY(), o2.getY())).getY();
        yMax = Collections.max(allPoints, (o1, o2) -> Integer.compare(o1.getY(), o2.getY())).getY();
        zMin = Collections.min(data.values(), Double::compare);
        zMax = Collections.max(data.values(), Double::compare);
    }

    public Map<Point, Double> getData() {
        return data;
    }

    public Polygon getBoundaries() {
        return boundaries;
    }

    public int getGridStep() {
        return gridStep;
    }

    public int getxMin() {
        return xMin;
    }

    public int getxMax() {
        return xMax;
    }

    public int getyMin() {
        return yMin;
    }

    public int getyMax() {
        return yMax;
    }

    public double getzMin() {
        return zMin;
    }

    public double getzMax() {
        return zMax;
    }
}
