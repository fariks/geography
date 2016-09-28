package util;

import static java.lang.Math.sqrt;

/**
 * Created by smirnov on 28.09.2016.
 */
public class GeometryHelper {

    public static double distance(int x1, int y1, int x2, int y2) {
        return sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
    }
}
