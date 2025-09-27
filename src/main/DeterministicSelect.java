package main;

public class DeterministicSelect {

    private static final int GROUP_SIZE = 5;
    private static final int CUTOFF = 16;

    // API: вернуть значение k-го элемента (0-indexed) в массиве
    public static int select(int[] a, int k, Metrics metrics) {
        if (a == null || a.length == 0) throw new IllegalArgumentException("Empty array");
        if (k < 0 || k >= a.length) throw new IllegalArgumentException("k out of range");
        return selectRange(a, 0, a.length - 1, k, metrics);
    }

    private static int selectRange(int[] a, int left, int right, int k, Metrics metrics) {
        while (true) {
            int n = right - left + 1;
            if (n <= CUTOFF) {
                insertionSort(a, left, right, metrics);
                return a[left + k];
            }

            metrics.enterRecursion();

            int mediansStart = left;
            int countMedians = 0;
            int i = left;
            while (i <= right) {
                int groupEnd = Math.min(i + GROUP_SIZE - 1, right);
                insertionSort(a, i, groupEnd, metrics);
                int medianIndex = i + (groupEnd - i) / 2;
                swap(a, mediansStart + countMedians, medianIndex, metrics);
                countMedians++;
                i = groupEnd + 1;
            }

            int medLeft = mediansStart;
            int medRight = mediansStart + countMedians - 1;
            int medK = countMedians / 2;
            int pivotValue = selectRange(a, medLeft, medRight, medK, metrics);

            int pivotPos = partitionAroundValue(a, left, right, pivotValue, metrics);

            int leftSize = pivotPos - left;

            if (k == leftSize) {
                metrics.exitRecursion();
                return a[pivotPos];
            } else if (k < leftSize) {
                right = pivotPos - 1;
                metrics.exitRecursion();
            } else {
                k = k - leftSize - 1;
                left = pivotPos + 1;
                metrics.exitRecursion();
            }
        }
    }

    private static int partitionAroundValue(int[] a, int left, int right, int pivotValue, Metrics metrics) {
        int pivotIdx = left;
        while (pivotIdx <= right) {
            metrics.incComparison();
            if (a[pivotIdx] == pivotValue) break;
            pivotIdx++;
        }
        swap(a, pivotIdx, right, metrics);

        int store = left;
        for (int i = left; i < right; i++) {
            metrics.incComparison();
            if (a[i] < pivotValue) {
                swap(a, i, store, metrics);
                store++;
            }
        }
        swap(a, store, right, metrics);
        return store;
    }

    private static void insertionSort(int[] a, int left, int right, Metrics metrics) {
        for (int i = left + 1; i <= right; i++) {
            int key = a[i];
            int j = i - 1;
            while (j >= left) {
                metrics.incComparison();
                if (a[j] > key) {
                    a[j + 1] = a[j];
                    metrics.incAllocation();
                    j--;
                } else {
                    break;
                }
            }
            a[j + 1] = key;
            metrics.incAllocation();
        }
    }

    private static void swap(int[] a, int i, int j, Metrics metrics) {
        if (i == j) return;
        int tmp = a[i];
        a[i] = a[j];
        a[j] = tmp;
        metrics.incAllocation();
    }
}
