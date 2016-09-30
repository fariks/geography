package com.maxifier.geography.interpolation.nearestneighbors;

import java.util.List;

public interface NeighborsSearcher {

    /**
     * Returns neighbors of point with coordinates (x,y)
     *
     * @param workedGrid grid for search neighbors
     * @param x x coordinate of point
     * @param y y coordinate of point
     * @param neighborCount count of neighbors to search
     * @param searchRadius radius for neighbors search
     * @return neighbors of point with coordinates (x,y)
     */
    List<Neighbor> getNeighbors(double[][] workedGrid, int x, int y, int neighborCount, int searchRadius);
}
