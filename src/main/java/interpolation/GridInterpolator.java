package interpolation;

import java.util.Map;

/**
 * Created by alsm0813 on 27.09.2016.
 */
public interface GridInterpolator {
    Map<Point2D, Double>  interpolate(Map<Point2D, Double> data, Polygon polygon, int h);
}
