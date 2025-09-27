package main;

import org.openjdk.jmh.annotations.*;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@State(Scope.Thread)
public class SelectBenchmark {

    private int[] data;
    private int k;
    private Random rnd = new Random();

    @Setup(Level.Invocation)
    public void setup() {
        data = rnd.ints(10_000, 0, 1_000_000).toArray();
        k = rnd.nextInt(data.length);
    }

    @Benchmark
    public int deterministicSelect() {
        Metrics m = new Metrics();
        return DeterministicSelect.select(data.clone(), k, m);
    }

    @Benchmark
    public int sortAndPick() {
        int[] copy = data.clone();
        Arrays.sort(copy);
        return copy[k];
    }
}

