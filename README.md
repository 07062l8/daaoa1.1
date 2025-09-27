# Design and Analysis of Algorithms – Assignment 1
## Learning Goals
- Implement classic Divide & Conquer algorithms with secure recursive patterns.
- Analyze execution time using Master Theorem and Akra–Bazzi.
- Collect metrics (time, recursion depth, memory comparisons/allocations).
- Compare theory and practice, and document the results.
## Implemented algorithms
1. MergeSort – linear merge, reusable buffer, small-n cut-off (insertion sort).
2. QuickSort (robust) – random pivot, recursion only into the smaller part, O(log n) stack.
3. Deterministic Select (Median-of-Medians) – groups of 5, median of medians, recursion only into the desired part.
4. Closest Pair of Points (2D) – sort by x, recursive division, "band" check by y.
## Architectural Notes
- Recursion depth control: QuickSort always recurses to the smallest part → O(log n) stack.

- Memory allocation control: MergeSort uses a single buffer for merging.

- Cutoff: Insertion sort is used for small arrays (≤16 elements).

- Metrics: Comparisons, allocations, recursion depth, and execution time are collected.
## Recurrence Formulas and Analysis

- MergeSort
T(n) = 2T(n/2) + Θ(n)
→ Master Theorem, Case 2 → Θ(n log n)
For MergeSort, the recurrence is T(n) = 2T(n/2) + Θ(n). Using Master Theorem (Case 2), we get Θ(n log n). In my experiments, the runtime graph clearly follows this growth, though for small n the insertion sort cut‑off makes it faster than expected.

- QuickSort (average case)
T(n) = T(k) + T(n−k−1) + Θ(n)
→ average Θ(n log n), worst case Θ(n²), but the probability is low.

- Deterministic Select (Median-of-Medians)
T(n) = T(n/5) + T(7n/10) + Θ(n)
→ Θ(n) (Akra–Bazzi).

- Closest Pair of Points 
T(n) = 2T(n/2) + Θ(n) 
→ Master Theorem, Case 2 → Θ(n log n).
## Experiments and Graphs

### Runtime (time vs. n) 
<img width="942" height="565" alt="Снимок экрана 2025-09-27 105401" src="https://github.com/user-attachments/assets/ad1813c8-2662-4032-a41a-f19ce3db4325" />
- MergeSort and QuickSort show growth of ~n log n.

- Select grows linearly.

- Closest Pair grows ~n log n.

### Comparisons vs n
<img width="838" height="508" alt="Снимок экрана 2025-09-27 105849" src="https://github.com/user-attachments/assets/8ebc718b-25a5-4f2b-8627-51315b0fe4d6" />

The number of comparisons for MergeSort and QuickSort grows as 𝑛log𝑛, which is confirmed by Master Theorem analysis. For Select, growth is closer to linear, as the median of medians guarantees balance. Closest Pair has fewer comparisons, but they are more expensive, which is reflected over time.

### Recursion depth vs n
<img width="833" height="502" alt="Снимок экрана 2025-09-27 110918" src="https://github.com/user-attachments/assets/43447e97-d842-4b95-911e-c2f2e9b4c550" />

- MergeSort: depth ≈ log₂ n.

- QuickSort: depth is bounded at ≈ 2 log₂ n.

- Select: depth is linearly shallow (≈ log n in medians).

- Closest Pair: depth ≈ log₂ n.

The maximum recursion depth for MergeSort and QuickSort grows logarithmically, as shown in the graph. For Select, the depth is even smaller, as recursive calls quickly reduce the problem size. For Closest Pair, the depth is also bounded by 𝑂(log𝑛), confirming the theory.
### Allocations vs n
<img width="977" height="575" alt="Снимок экрана 2025-09-27 111757" src="https://github.com/user-attachments/assets/3303f707-11ed-4a18-9421-de19d81c7d8f" />

MergeSort requires significant memory allocations due to the buffer, which is evident from the linear growth of allocations. QuickSort and Select allocate almost no memory, making them more efficient. Closest Pair allocates minimal memory, as the algorithm primarily operates on existing structures.

### Constant factors
- QuickSort is faster than MergeSort on random data (better cache).
- MergeSort is more stable over time.
- Select is slower in practice than sort+pick, but guarantees O(n).
- Closest Pair outperforms O(n²) already for n > ~2000.

## Conclusions
- Theoretical estimates match practice.
- Constant factors (cache, GC, buffers) affect actual results.
- QuickSort is the best choice in practice, MergeSort is reliable and stable.
- Deterministic Select is useful for worst-case guarantees, but in practice, sorting is faster.
- Closest Pair divide-and-conquer provides a huge improvement over naive O(n²).
