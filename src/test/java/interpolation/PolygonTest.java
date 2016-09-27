package interpolation;

import static com.google.common.collect.Lists.*;
import static org.junit.Assert.*;

import com.google.common.collect.Lists;

import org.junit.Test;

/**
 * Created by alsm0813 on 27.09.2016.
 */
public class PolygonTest {

    private Polygon convexPolygon = new Polygon(
            newArrayList(new Point2D(1,2), new Point2D(3,6), new Point2D(5,3), new Point2D(5,1), new Point2D(2,1))
    );

    private Polygon nonConvexPolygon = new Polygon(
            newArrayList(new Point2D(1,2), new Point2D(3,6), new Point2D(7,5), new Point2D(7,2), new Point2D(3,4))
    );

    @Test
    public void testContainsPointsOutsideConvexPolygon() {
        assertFalse(convexPolygon.contains(new Point2D(0, 2)));
        assertFalse(convexPolygon.contains(new Point2D(3, 0)));
        assertFalse(convexPolygon.contains(new Point2D(1, 3)));
        assertFalse(convexPolygon.contains(new Point2D(4, 6)));
    }

    @Test
    public void testContainsPointsInsideConvexPolygon() {
        assertTrue(convexPolygon.contains(new Point2D(2, 2)));
        assertTrue(convexPolygon.contains(new Point2D(3, 3)));
        assertTrue(convexPolygon.contains(new Point2D(3, 5)));
        assertTrue(convexPolygon.contains(new Point2D(4, 2)));
    }

    @Test
    public void testContainsPointsOnConvexPolygonEdges() {
        assertTrue(convexPolygon.contains(new Point2D(3, 1)));
        //TODO fix
        //assertTrue(convexPolygon.contains(new Point2D(5, 2)));
        assertTrue(convexPolygon.contains(new Point2D(1, 2)));
        assertTrue(convexPolygon.contains(new Point2D(3, 6)));
        assertTrue(convexPolygon.contains(new Point2D(5, 1)));
    }

    @Test
    public void testContainsPointsOutsideNonConvexPolygon() {
        assertFalse(nonConvexPolygon.contains(new Point2D(1, 1)));
        assertFalse(nonConvexPolygon.contains(new Point2D(2, 5)));
        assertFalse(nonConvexPolygon.contains(new Point2D(3, 3)));
        assertFalse(nonConvexPolygon.contains(new Point2D(7, 6)));
    }

    @Test
    public void testContainsPointsInsideNonConvexPolygon() {
        assertTrue(nonConvexPolygon.contains(new Point2D(3, 5)));
        assertTrue(nonConvexPolygon.contains(new Point2D(4, 5)));
        assertTrue(nonConvexPolygon.contains(new Point2D(5, 4)));
        assertTrue(nonConvexPolygon.contains(new Point2D(6, 5)));
    }

    @Test
    public void testContainsPointsOnNonConvexPolygonEdges() {
        assertTrue(nonConvexPolygon.contains(new Point2D(1, 2)));
        //TODO fix
        //assertTrue(nonConvexPolygon.contains(new Point2D(7, 3)));
        assertTrue(nonConvexPolygon.contains(new Point2D(7, 2)));
        assertTrue(nonConvexPolygon.contains(new Point2D(3, 6)));
        assertTrue(nonConvexPolygon.contains(new Point2D(3, 4)));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testTryCreatePolygonWithTwoPoints() {
        new Polygon(newArrayList(new Point2D(1,2), new Point2D(3,6)));
    }

    @Test
    public void testTryCreatePolygonWithThreePoints() {
        new Polygon(newArrayList(new Point2D(1,2), new Point2D(3,6), new Point2D(5,3)));
    }
}