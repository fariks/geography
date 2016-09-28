package interpolation;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by smirnov on 28.09.2016.
 */
public class NearestNeighborsGridInterpolatorTest {

    @Test
    public void test() {
        NearestNeighborsGridInterpolator interpolator = new NearestNeighborsGridInterpolator();
        double[][] A = new double[][] {
                {1, -1, -1 , 2},
                {-1, -1, -1 , 3},
                {-1, -1, -1 , -1},
                {-1, -1, -1 , -1},
        };
        List<NearestNeighborsGridInterpolator.Neighbor> neighbors  = interpolator.getNeighborsOnSquarePerimeter(A, 3, 1, 3);
        assertEquals(2, neighbors.size());
        assertEquals(1, neighbors.get(0).height, 0.0001);
        assertEquals(2, neighbors.get(1).height, 0.0001);
    }
}