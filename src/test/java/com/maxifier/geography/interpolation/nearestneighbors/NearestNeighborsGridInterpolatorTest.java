package com.maxifier.geography.interpolation.nearestneighbors;

import static com.google.common.collect.Lists.*;
import static org.mockito.Mockito.*;

import com.google.common.collect.Lists;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Scopes;
import com.google.inject.util.Modules;

import com.maxifier.geography.GeographyModule;
import com.maxifier.geography.interpolation.GridInterpolator;
import com.maxifier.geography.interpolation.model.Grid;
import com.maxifier.geography.interpolation.model.Point;
import com.maxifier.geography.interpolation.model.Polygon;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.HashMap;
import java.util.Map;


public class NearestNeighborsGridInterpolatorTest {

    private ValueCalculator valueCalculator = mock(ValueCalculator.class);

    private NeighborsSearcher neighborsSearcher = mock(NeighborsSearcher.class);

    private Injector injector = Guice.createInjector(Modules.override(new GeographyModule()).with(
            new AbstractModule() {
                @Override
                protected void configure() {
                    bind(ValueCalculator.class).toInstance(valueCalculator);
                    bind(NeighborsSearcher.class).toInstance(neighborsSearcher);
                }
            }
    ));

    private GridInterpolator gridInterpolator = injector.getInstance(GridInterpolator.class);

    @Test
    public void testInterpolate() throws InterruptedException {
        Map<Point, Double> data = new HashMap<>();
        data.put(new Point(1,1), 1.0);
        data.put(new Point(0,0), 1.0);
        data.put(new Point(5,4), 1.0);
        data.put(new Point(4,5), 1.0);
        data.put(new Point(10,10), 1.0);

        Polygon polygon = new Polygon(newArrayList(new Point(0, 0), new Point(0, 4), new Point(4, 4), new Point(4, 0)));
        Grid grid = new Grid(data, polygon, 1, 1);
        int neighborCount = 3;
        int searchRadius = 5;
        gridInterpolator.interpolate(grid, neighborCount, searchRadius);

        Point min = grid.getMin();
        Point max = grid.getMax();
        int size = (max.getX() - min.getX() + 1) * (max.getY() - min.getY() + 1) - 2;
        verify(valueCalculator, times(size)).calculateValueByNeighbors(anyListOf(Neighbor.class));
    }
}