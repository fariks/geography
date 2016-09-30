package com.maxifier.geography.interpolation.nearestneighbors;

import static java.lang.Math.pow;

import java.util.List;

public class InverseDistanceWeightingCalculator implements ValueCalculator {

    /**
     * Calculates value by given neighbors using inverse distance weighting algorithm
     * https://en.wikipedia.org/wiki/Inverse_distance_weighting
     *
     * @param neighbors given neighbors
     * @return calculated value
     */
    @Override
    public double calculateValueByNeighbors(List<Neighbor> neighbors) {
        double sum = 0;
        double weigh_sum = 0;
        for (Neighbor neighbor : neighbors) {
            double weight = pow(1 / neighbor.getDistance(), 2);
            weigh_sum += weight;
            sum += weight * neighbor.getHeight();
        }
        return Double.compare(weigh_sum, 0) == 0 ? 0 : sum / weigh_sum;
    }
}
