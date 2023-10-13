package test.clevertec.course.stream.benchmark;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import ru.clevertec.course.stream.util.CustomUtils;

import java.util.concurrent.TimeUnit;

@State(Scope.Thread)
@BenchmarkMode({Mode.AverageTime})
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Warmup(iterations = 2)
@Measurement(iterations = 2)
@Fork(value = 1, warmups = 1)
public class RandomSumBenchmark {
    private static final CustomUtils utils = CustomUtils.getInstance();

    @Param({"1000000", "10000000", "100000000"})
    static int iterations;


    @Benchmark
    public void benchmarkTwoParallelSum(Blackhole blackhole) {
        long sum = utils.parallelSumOfRandomLong(iterations, 2);
        blackhole.consume(sum);
    }

    @Benchmark
    public void benchmarkFourParallelSum(Blackhole blackhole) {
        long sum = utils.parallelSumOfRandomLong(iterations, 4);
        blackhole.consume(sum);
    }


    @Benchmark
    public void benchmarkSequenceSum(Blackhole blackhole) {
        long sum = utils.sequenceSumOfRandomLong(iterations);
        blackhole.consume(sum);
    }
}
