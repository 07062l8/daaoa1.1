package main;

import java.util.Random;

public class Main {
    public static void main(String[] args) {
        String fileName = "results.csv";

        try {
            java.nio.file.Files.writeString(
                    java.nio.file.Paths.get(fileName),
                    "algorithmName;n;runTimeMicros;comparisons;depth;allocations\n"
            );
        } catch (Exception e) {
            e.printStackTrace();
        }

        int[] sizes = {1000, 2000, 5000, 10000, 20000};

        for (int n : sizes) {
            int[] arr = new Random().ints(n, 0, 100000).toArray();

            runAndRecord("MergeSort", n, arr.clone(), fileName, (a, m) -> MergeSort.sort(a, m));

            runAndRecord("QuickSort", n, arr.clone(), fileName, (a, m) -> QuickSort.sort(a, m));

            runAndRecord("DeterministicSelect", n, arr.clone(), fileName,
                    (a, m) -> DeterministicSelect.select(a, a.length / 2, m));

            ClosestPair.Point[] pts = new ClosestPair.Point[n];
            Random rnd = new Random();
            for (int i = 0; i < n; i++) {
                pts[i] = new ClosestPair.Point(rnd.nextDouble() * 10000, rnd.nextDouble() * 10000);
            }
            runAndRecordClosest("ClosestPair", n, pts, fileName);
        }

        System.out.println("Benchmark finished. Results saved to " + fileName);
    }

    private static void runAndRecord(String name, int n, int[] arr, String fileName, Algorithm algo) {
        Metrics m = new Metrics();
        long start = System.nanoTime();
        algo.run(arr, m);
        long end = System.nanoTime();
        long micros = (end - start) / 1000;
        Metrics.writeCsvLine(fileName, name, n, micros, m);
    }

    private static void runAndRecordClosest(String name, int n, ClosestPair.Point[] pts, String fileName) {
        Metrics m = new Metrics();
        long start = System.nanoTime();
        ClosestPair.closestPair(pts, m);
        long end = System.nanoTime();
        long micros = (end - start) / 1000;
        Metrics.writeCsvLine(fileName, name, n, micros, m);
    }

    @FunctionalInterface
    interface Algorithm {
        void run(int[] arr, Metrics m);
    }
}


