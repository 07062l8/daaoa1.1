package main;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Metrics {
    private long comparisons = 0;
    private long allocations = 0;
    private int currentDepth = 0;
    private int maxDepth = 0;

    public void incComparison() {
        comparisons++;
    }

    public void incAllocation() {
        allocations++;
    }

    public void enterRecursion() {
        currentDepth++;
        if (currentDepth > maxDepth) {
            maxDepth = currentDepth;
        }
    }

    public void exitRecursion() {
        currentDepth--;
    }

    public long getComparisons() {
        return comparisons;
    }

    public long getAllocations() {
        return allocations;
    }

    public int getMaxDepth() {
        return maxDepth;
    }

    public void reset() {
        comparisons = 0;
        allocations = 0;
        currentDepth = 0;
        maxDepth = 0;
    }


    public static void writeCsvLine(String fileName,
                                    String algorithmName,
                                    int n,
                                    long runTimeMicros,
                                    Metrics m) {
        try (PrintWriter out = new PrintWriter(new FileWriter(fileName, true))) {
            out.printf("%s;%d;%d;%d;%d;%d%n",
                    algorithmName,
                    n,
                    runTimeMicros,
                    m.getComparisons(),
                    m.getMaxDepth(),
                    m.getAllocations());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
