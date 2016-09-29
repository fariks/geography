package com.maxifier.geography;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;
import com.google.inject.name.Names;

import com.maxifier.geography.gui.GridRenderer;
import com.maxifier.geography.interpolation.GridInterpolator;
import com.maxifier.geography.interpolation.nearestneighbors.InverseDistanceWeightsCalculator;
import com.maxifier.geography.interpolation.nearestneighbors.NearestNeighborsGridInterpolator;
import com.maxifier.geography.interpolation.nearestneighbors.NearestNeighborsSearcher;
import com.maxifier.geography.interpolation.nearestneighbors.NeighborsSearcher;
import com.maxifier.geography.interpolation.nearestneighbors.ValueCalculator;
import com.maxifier.geography.util.GridCSVHelper;

public class GeographyModule extends AbstractModule {

    public static final double EPS = 0.0001;

    public static final int NEIGHBOR_COUNT = 3;

    @Override
    protected void configure() {
        bind(GridInterpolator.class).to(NearestNeighborsGridInterpolator.class).in(Scopes.SINGLETON);
        bind(GridCSVHelper.class).in(Scopes.SINGLETON);
        bind(GridRenderer.class).in(Scopes.SINGLETON);
        bind(ValueCalculator.class).to(InverseDistanceWeightsCalculator.class).in(Scopes.SINGLETON);
        bind(NeighborsSearcher.class).to(NearestNeighborsSearcher.class).in(Scopes.SINGLETON);
        bindConstant().annotatedWith(Names.named("neighborhoodCount")).to(NEIGHBOR_COUNT);
    }
}
