package interpolation;

import java.util.Map;

/**
 * Created by alsm0813 on 27.09.2016.
 */
public interface GridInterpolator {
    Grid interpolate(Grid grid) throws InterruptedException;
}
