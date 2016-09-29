package com.maxifier.geography.interpolation.nearestneighbors;

import static com.google.common.collect.Lists.*;

import com.google.inject.Guice;
import com.google.inject.Injector;

import com.maxifier.geography.GeographyModule;

import org.junit.Test;

public class InverseDistanceWeightsCalculatorTest {

    private Injector injector = Guice.createInjector(new GeographyModule());

    @Test
    public void testCalculateValueByNeighbors() {
        ValueCalculator valueCalculator = injector.getInstance(ValueCalculator.class);
        valueCalculator.calculateValueByNeighbors(newArrayList());
    }
}