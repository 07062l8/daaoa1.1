package main;

import java.util.Random;

public class QuickSort {

    private static final Random random = new Random();

    public static void sort(int[] arr, Metrics metrics) {
        quicksort(arr, 0, arr.length - 1, metrics);
    }

    private static void quicksort(int[] arr, int left, int right, Metrics metrics) {
        while (left < right) {
            metrics.enterRecursion();

            int pivotIndex = left + random.nextInt(right - left + 1);
            int pivot = arr[pivotIndex];
            swap(arr, pivotIndex, right, metrics);

            int partitionIndex = partition(arr, left, right, pivot, metrics);

            if (partitionIndex - left < right - partitionIndex) {
                quicksort(arr, left, partitionIndex - 1, metrics);
                left = partitionIndex + 1;
            } else {
                quicksort(arr, partitionIndex + 1, right, metrics);
                right = partitionIndex - 1;
            }

            metrics.exitRecursion();
        }
    }

    private static int partition(int[] arr, int left, int right, int pivot, Metrics metrics) {
        int i = left;
        int j = right - 1;

        while (i <= j) {
            while (i <= j) {
                metrics.incComparison();
                if (arr[i] < pivot) {
                    i++;
                } else break;
            }
            while (i <= j) {
                metrics.incComparison();
                if (arr[j] >= pivot) {
                    j--;
                } else break;
            }
            if (i < j) {
                swap(arr, i, j, metrics);
                i++;
                j--;
            }
        }

        swap(arr, i, right, metrics);
        return i;
    }

    private static void swap(int[] arr, int i, int j, Metrics metrics) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
        metrics.incAllocation();
    }
}

