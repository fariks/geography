package com.maxifier.geography.interpolation.nearestneighbors;

import java.util.List;

public interface ValueCalculator {
    double calculateValueByNeighbors(List<Neighbor> neighbors);
}
