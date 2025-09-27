package main;

public class MergeSort {

    private static final int CUTOFF = 16;

    public static void sort(int[] arr, Metrics metrics) {
        int[] buffer = new int[arr.length];
        sort(arr, buffer, 0, arr.length - 1, metrics);
    }

    private static void sort(int[] arr, int[] buffer, int left, int right, Metrics metrics) {
        if (right - left + 1 <= CUTOFF) {
            insertionSort(arr, left, right, metrics);
            return;
        }

        metrics.enterRecursion();

        int mid = (left + right) / 2;
        sort(arr, buffer, left, mid, metrics);
        sort(arr, buffer, mid + 1, right, metrics);

        merge(arr, buffer, left, mid, right, metrics);

        metrics.exitRecursion();
    }

    private static void merge(int[] arr, int[] buffer, int left, int mid, int right, Metrics metrics) {
        for (int i = left; i <= mid; i++) {
            buffer[i] = arr[i];
            metrics.incAllocation();
        }

        int i = left;
        int j = mid + 1;
        int k = left;

        while (i <= mid && j <= right) {
            metrics.incComparison();
            if (buffer[i] <= arr[j]) {
                arr[k++] = buffer[i++];
            } else {
                arr[k++] = arr[j++];
            }
        }

        while (i <= mid) {
            arr[k++] = buffer[i++];
        }
    }

    private static void insertionSort(int[] arr, int left, int right, Metrics metrics) {
        for (int i = left + 1; i <= right; i++) {
            int key = arr[i];
            int j = i - 1;
            while (j >= left) {
                metrics.incComparison();
                if (arr[j] > key) {
                    arr[j + 1] = arr[j];
                    j--;
                } else {
                    break;
                }
            }
            arr[j + 1] = key;
        }
    }
}
