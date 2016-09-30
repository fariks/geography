package com.maxifier.geography.interpolation.nearestneighbors;

import static com.maxifier.geography.GeographyModule.*;
import static java.lang.Math.*;
import static org.junit.Assert.*;

import com.google.inject.Guice;
import com.google.inject.Injector;

import com.maxifier.geography.GeographyModule;

import org.junit.Test;

import java.util.List;

public class NearestNeighborsSearcherTest {

    private Injector injector = Guice.createInjector(new GeographyModule());

    private NeighborsSearcher neighborsSearcher = injector.getInstance(NeighborsSearcher.class);

    private double[][] grid3x3 = new double[][] {
            {-1,2,-1},
            {2,0,-1},
            {9,-1,9},
    };

    private double[][] grid5x5 = new double[][] {
            {4,-1,3,-1,4},
            {-1,2,-1,2,-1},
            {-1,-1,0,-1,-1},
            {-1,2,-1,2,-1},
            {4,-1,3,-1,4},
    };

    private double[][] grid9x9 = new double[][] {
            {-1,-1,-1,-1, 3,-1,-1,-1,-1},
            {-1,-1,-1,-1,-1,-1,-1, 2,-1},
            {-1,-1,-1,-1,-1,-1,-1,-1,-1},
            {-1,-1,-1,-1,-1,-1,-1,-1,-1},
            {-1,-1,-1,-1, 0,-1,-1,-1,-1},
            {-1,-1,-1,-1,-1,-1,-1,-1,-1},
            {-1,-1,-1,-1,-1,-1,-1,-1,-1},
            {-1,-1,-1,-1,-1,-1,-1,-1,-1},
            {-1,-1,-1,-1,-1,-1,-1,-1,-1},
    };

    @Test
    public void testGetNearestNeighbors3x3Grid2Neighbors() {
        int count = 2;
        List<Neighbor> neighbors = neighborsSearcher.getNeighbors(grid3x3, 1, 1, count, 1);
        assertEquals(count, neighbors.size());
        assertEquals(1, neighbors.get(0).getDistance(), EPS);
        assertEquals(2, neighbors.get(0).getHeight(), EPS);
        assertEquals(1, neighbors.get(1).getDistance(), EPS);
        assertEquals(2, neighbors.get(1).getHeight(), EPS);
    }

    @Test
    public void testGetNearestNeighbors3x3Grid4Neighbors() {
        int count = 4;
        List<Neighbor> neighbors = neighborsSearcher.getNeighbors(grid3x3, 1, 1, count, 4);
        assertEquals(count, neighbors.size());
        assertEquals(1, neighbors.get(0).getDistance(), EPS);
        assertEquals(2, neighbors.get(0).getHeight(), EPS);
        assertEquals(1, neighbors.get(1).getDistance(), EPS);
        assertEquals(2, neighbors.get(1).getHeight(), EPS);
        assertEquals(sqrt(2), neighbors.get(2).getDistance(), EPS);
        assertEquals(9, neighbors.get(2).getHeight(), EPS);
        assertEquals(sqrt(2), neighbors.get(3).getDistance(), EPS);
        assertEquals(9, neighbors.get(3).getHeight(), EPS);
    }

    @Test
    public void testGetNearestNeighbors5x5Grid4Neighbors() {
        int count = 4;
        List<Neighbor> neighbors = neighborsSearcher.getNeighbors(grid5x5, 2, 2, count, 2);
        assertEquals(count, neighbors.size());
        assertEquals(sqrt(2), neighbors.get(0).getDistance(), EPS);
        assertEquals(2, neighbors.get(0).getHeight(), EPS);
        assertEquals(count, neighbors.size());
        assertEquals(sqrt(2), neighbors.get(1).getDistance(), EPS);
        assertEquals(2, neighbors.get(1).getHeight(), EPS);
        assertEquals(count, neighbors.size());
        assertEquals(sqrt(2), neighbors.get(2).getDistance(), EPS);
        assertEquals(2, neighbors.get(2).getHeight(), EPS);
        assertEquals(count, neighbors.size());
        assertEquals(sqrt(2), neighbors.get(3).getDistance(), EPS);
        assertEquals(2, neighbors.get(3).getHeight(), EPS);
    }

    @Test
    public void testGetNearestNeighbors5x5Grid5Neighbors() {
        int count = 5;
        List<Neighbor> neighbors = neighborsSearcher.getNeighbors(grid5x5, 2, 2, count, 2);
        assertEquals(count, neighbors.size());
        assertEquals(sqrt(2), neighbors.get(0).getDistance(), EPS);
        assertEquals(2, neighbors.get(0).getHeight(), EPS);
        assertEquals(count, neighbors.size());
        assertEquals(sqrt(2), neighbors.get(1).getDistance(), EPS);
        assertEquals(2, neighbors.get(1).getHeight(), EPS);
        assertEquals(count, neighbors.size());
        assertEquals(sqrt(2), neighbors.get(2).getDistance(), EPS);
        assertEquals(2, neighbors.get(2).getHeight(), EPS);
        assertEquals(count, neighbors.size());
        assertEquals(sqrt(2), neighbors.get(3).getDistance(), EPS);
        assertEquals(2, neighbors.get(3).getHeight(), EPS);
        assertEquals(2, neighbors.get(4).getDistance(), EPS);
        assertEquals(3, neighbors.get(4).getHeight(), EPS);
    }

    @Test
    public void testGetNearestNeighbors5x5Grid8Neighbors() {
        int count = 8;
        List<Neighbor> neighbors = neighborsSearcher.getNeighbors(grid5x5, 2, 2, count, 3);
        assertEquals(count, neighbors.size());
        assertEquals(sqrt(2), neighbors.get(0).getDistance(), EPS);
        assertEquals(2, neighbors.get(0).getHeight(), EPS);
        assertEquals(count, neighbors.size());
        assertEquals(sqrt(2), neighbors.get(1).getDistance(), EPS);
        assertEquals(2, neighbors.get(1).getHeight(), EPS);
        assertEquals(count, neighbors.size());
        assertEquals(sqrt(2), neighbors.get(2).getDistance(), EPS);
        assertEquals(2, neighbors.get(2).getHeight(), EPS);
        assertEquals(count, neighbors.size());
        assertEquals(sqrt(2), neighbors.get(3).getDistance(), EPS);
        assertEquals(2, neighbors.get(3).getHeight(), EPS);
        assertEquals(2, neighbors.get(4).getDistance(), EPS);
        assertEquals(3, neighbors.get(4).getHeight(), EPS);
        assertEquals(2, neighbors.get(5).getDistance(), EPS);
        assertEquals(3, neighbors.get(5).getHeight(), EPS);
        assertEquals(2 * sqrt(2), neighbors.get(6).getDistance(), EPS);
        assertEquals(4, neighbors.get(6).getHeight(), EPS);
        assertEquals(2 * sqrt(2), neighbors.get(7).getDistance(), EPS);
        assertEquals(4, neighbors.get(7).getHeight(), EPS);
    }

    @Test
    public void testGetNearestNeighbors9x9Grid1Neighbor() {
        int count = 1;
        List<Neighbor> neighbors = neighborsSearcher.getNeighbors(grid9x9, 4, 4, count, 10);
        assertEquals(count, neighbors.size());
        assertEquals(4, neighbors.get(0).getDistance(), EPS);
        assertEquals(3, neighbors.get(0).getHeight(), EPS);
    }

    @Test
    public void testGetNearestNeighbors9x9Grid1NeighborRadiusRestriction() {
        int count = 2;
        List<Neighbor> neighbors = neighborsSearcher.getNeighbors(grid9x9, 4, 4, count, 4);
        assertEquals(count - 1, neighbors.size());
        assertEquals(4, neighbors.get(0).getDistance(), EPS);
        assertEquals(3, neighbors.get(0).getHeight(), EPS);
    }

    @Test
    public void testGetNearestNeighbors9x9Grid2NeighborOn2Layers() {
        int count = 2;
        List<Neighbor> neighbors = neighborsSearcher.getNeighbors(grid9x9, 4, 4, count, 5);
        assertEquals(count, neighbors.size());
        assertEquals(4, neighbors.get(0).getDistance(), EPS);
        assertEquals(3, neighbors.get(0).getHeight(), EPS);
        assertEquals(3 * sqrt(2), neighbors.get(1).getDistance(), EPS);
        assertEquals(2, neighbors.get(1).getHeight(), EPS);
    }

    @Test
    public void testGetNearestNeighbors9x9Grid1NeighborLeftBottomPoint() {
        int count = 1;
        List<Neighbor> neighbors = neighborsSearcher.getNeighbors(grid9x9, grid9x9.length - 1, 0, count, 6);
        assertEquals(count, neighbors.size());
        assertEquals(4 * sqrt(2), neighbors.get(0).getDistance(), EPS);
        assertEquals(0, neighbors.get(0).getHeight(), EPS);
    }

    @Test
    public void testGetNearestNeighbors9x9Grid1NeighborRightTopPoint() {
        int count = 1;
        List<Neighbor> neighbors = neighborsSearcher.getNeighbors(grid9x9, 0, grid9x9.length - 1, count, 3);
        assertEquals(count, neighbors.size());
        assertEquals(sqrt(2), neighbors.get(0).getDistance(), EPS);
        assertEquals(2, neighbors.get(0).getHeight(), EPS);
    }
}