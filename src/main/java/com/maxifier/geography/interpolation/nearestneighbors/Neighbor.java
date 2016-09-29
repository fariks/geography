package com.maxifier.geography.interpolation.nearestneighbors;

public class Neighbor implements Comparable<Neighbor>  {
    private double height;
    private double distance;

    public Neighbor(double height, double distance) {
        this.height = height;
        this.distance = distance;
    }

    public double getHeight() {
        return height;
    }

    public double getDistance() {
        return distance;
    }

    @Override
    public int compareTo(Neighbor o) {
        return Double.compare(this.distance, o.distance);
    }
}
