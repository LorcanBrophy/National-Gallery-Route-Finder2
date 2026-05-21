package com.nationalgalleryroutefinder.util;

import javafx.geometry.Point2D;

public final class PointUtils {
    /**
     * @param a The first point.
     * @param b The second point.
     * @return The squared distance between the two points.
     */
    // squared distance between two points
    public static double distanceSquared(Point2D a, Point2D b) {
        double distanceX = a.getX() - b.getX();
        double distanceY = a.getY() - b.getY();

        return (distanceX * distanceX) + (distanceY * distanceY);
    }

    /**
     * @param a The first point.
     * @param b The second point.
     * @return The distance between the two points.
     */
    // distance between two points
    public static double distance(Point2D a, Point2D b) {
        return Math.sqrt(distanceSquared(a, b));
    }
}