package main;

import java.util.Arrays;
import java.util.Comparator;

public class ClosestPair {

    public static class Point {
        public final double x, y;
        public Point(double x, double y) { this.x = x; this.y = y; }
    }

    public static double closestPair(Point[] points, Metrics metrics) {
        Point[] ptsByX = points.clone();
        Arrays.sort(ptsByX, Comparator.comparingDouble(p -> p.x));
        Point[] ptsByY = ptsByX.clone();
        return closestRec(ptsByX, ptsByY, 0, ptsByX.length - 1, metrics);
    }

    private static double closestRec(Point[] ptsByX, Point[] ptsByY, int left, int right, Metrics metrics) {
        int n = right - left + 1;
        if (n <= 3) {
            return bruteForce(ptsByX, left, right, metrics);
        }

        metrics.enterRecursion();

        int mid = (left + right) / 2;
        double midX = ptsByX[mid].x;

        Point[] leftY = new Point[mid - left + 1];
        Point[] rightY = new Point[right - mid];
        int li = 0, ri = 0;
        for (Point p : ptsByY) {
            if (p.x <= midX && li < leftY.length) leftY[li++] = p;
            else rightY[ri++] = p;
        }

        double dLeft = closestRec(ptsByX, leftY, left, mid, metrics);
        double dRight = closestRec(ptsByX, rightY, mid + 1, right, metrics);
        double d = Math.min(dLeft, dRight);

        Point[] strip = new Point[n];
        int stripCount = 0;
        for (Point p : ptsByY) {
            if (Math.abs(p.x - midX) < d) {
                strip[stripCount++] = p;
            }
        }

        for (int i = 0; i < stripCount; i++) {
            for (int j = i + 1; j < stripCount && (strip[j].y - strip[i].y) < d; j++) {
                metrics.incComparison();
                double dist = dist(strip[i], strip[j]);
                if (dist < d) d = dist;
            }
        }

        metrics.exitRecursion();
        return d;
    }

    private static double bruteForce(Point[] pts, int left, int right, Metrics metrics) {
        double min = Double.POSITIVE_INFINITY;
        for (int i = left; i <= right; i++) {
            for (int j = i + 1; j <= right; j++) {
                metrics.incComparison();
                double dist = dist(pts[i], pts[j]);
                if (dist < min) min = dist;
            }
        }
        return min;
    }

    private static double dist(Point a, Point b) {
        double dx = a.x - b.x;
        double dy = a.y - b.y;
        return Math.sqrt(dx * dx + dy * dy);
    }
}
