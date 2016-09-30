package com.maxifier.geography.interpolation.nearestneighbors;

import static com.google.common.collect.Lists.*;
import static com.maxifier.geography.GeographyModule.*;

import com.google.inject.Guice;
import com.google.inject.Injector;

import com.maxifier.geography.GeographyModule;

import org.junit.Assert;
import org.junit.Test;

public class InverseDistanceWeightingCalculatorTest {

    private Injector injector = Guice.createInjector(new GeographyModule());
    private ValueCalculator valueCalculator = injector.getInstance(ValueCalculator.class);

    @Test
    public void testCalculateValueByNeighborsZeroNeighbors() {
        double w = valueCalculator.calculateValueByNeighbors(newArrayList());
        Assert.assertEquals(0, w, EPS);
    }

    @Test
    public void testCalculateValueByNeighborsOneNeighbor() {
        double w = valueCalculator.calculateValueByNeighbors(newArrayList(new Neighbor(1, 2)));
        Assert.assertEquals(1, w, EPS);
    }

    @Test
    public void testCalculateValueByNeighborsSeveralNeighbors() {
        double w = valueCalculator.calculateValueByNeighbors(newArrayList(
                new Neighbor(1, 2), new Neighbor(2, 2.5), new Neighbor(0, 4)
        ));
        Assert.assertEquals(1.2063, w, EPS);
    }
}