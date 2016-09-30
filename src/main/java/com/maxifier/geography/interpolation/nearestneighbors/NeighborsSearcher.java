package com.maxifier.geography.interpolation.nearestneighbors;

import java.util.List;

public interface NeighborsSearcher {
    List<Neighbor> getNearestNeighbors(double[][] workedGrid, int x, int y, int neighborCount, int searchRadius);
}
