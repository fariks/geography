package com.maxifier.geography.interpolation.nearestneighbors;

import java.util.List;

public interface ValueCalculator {

    /**
     * Calculates value by given neighbors
     *
     * @param neighbors given neighbors
     * @return calculated value
     */
    double calculateValueByNeighbors(List<Neighbor> neighbors);
}
