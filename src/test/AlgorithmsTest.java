package test;

import main.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;


import java.util.Arrays;
import java.util.Random;

public class AlgorithmsTest {

    private final Random rnd = new Random();

    @Test
    void testMergeSortCorrectness() {
        int[] arr = rnd.ints(1000, 0, 10000).toArray();
        int[] expected = arr.clone();
        Arrays.sort(expected);

        Metrics metrics = new Metrics();
        MergeSort.sort(arr, metrics);

        assertArrayEquals(expected, arr, "MergeSort failed");
        assertTrue(metrics.getMaxDepth() > 0);
    }

    @Test
    void testQuickSortCorrectness() {
        int[] arr = rnd.ints(1000, -5000, 5000).toArray();
        int[] expected = arr.clone();
        Arrays.sort(expected);

        Metrics metrics = new Metrics();
        QuickSort.sort(arr, metrics);

        assertArrayEquals(expected, arr, "QuickSort failed");
        assertTrue(metrics.getMaxDepth() <= 2 * (int)(Math.log(arr.length) / Math.log(2)) + 5);
    }

    @Test
    void testDeterministicSelect() {
        int[] arr = rnd.ints(200, 0, 1000).toArray();
        int[] sorted = arr.clone();
        Arrays.sort(sorted);

        Metrics metrics = new Metrics();
        int k = 50;
        int result = DeterministicSelect.select(arr.clone(), k, metrics);

        assertEquals(sorted[k], result, "DeterministicSelect failed");
    }

    @Test
    void testClosestPairSmall() {
        ClosestPair.Point[] pts = {
                new ClosestPair.Point(0, 0),
                new ClosestPair.Point(3, 4),
                new ClosestPair.Point(7, 7),
                new ClosestPair.Point(1, 1)
        };

        Metrics metrics = new Metrics();
        double d = ClosestPair.closestPair(pts, metrics);

        assertEquals(Math.sqrt(2), d, 1e-9, "ClosestPair failed");
    }

    @Test
    void testClosestPairRandomVsBruteForce() {
        int n = 200;
        ClosestPair.Point[] pts = new ClosestPair.Point[n];
        for (int i = 0; i < n; i++) {
            pts[i] = new ClosestPair.Point(rnd.nextDouble() * 1000, rnd.nextDouble() * 1000);
        }

        Metrics metrics = new Metrics();
        double fast = ClosestPair.closestPair(pts, metrics);
        double brute = bruteForce(pts);

        assertEquals(brute, fast, 1e-9);
    }

    private double bruteForce(ClosestPair.Point[] pts) {
        double min = Double.POSITIVE_INFINITY;
        for (int i = 0; i < pts.length; i++) {
            for (int j = i + 1; j < pts.length; j++) {
                double dx = pts[i].x - pts[j].x;
                double dy = pts[i].y - pts[j].y;
                double dist = Math.sqrt(dx * dx + dy * dy);
                if (dist < min) min = dist;
            }
        }
        return min;
    }
}

