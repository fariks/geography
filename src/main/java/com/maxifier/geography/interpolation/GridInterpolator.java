package com.maxifier.geography.interpolation;

import com.maxifier.geography.interpolation.model.Grid;

public interface GridInterpolator {

    /**
     * Interpolates grid by searching neighbors in given radius
     *
     * @param initialGrid initial grid to interpolate
     * @param neighborCount count of neighbors needed to compute value for point on grid
     * @param searchRadius radius for neighbors search
     * @return interpolated grid
     * @throws InterruptedException
     */
    Grid interpolate(Grid initialGrid, int neighborCount, int searchRadius) throws InterruptedException;
}
