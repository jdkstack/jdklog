package org.jdkstack.jdklog.jmh.log4j2;

import java.util.concurrent.TimeUnit;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.LifeCycle;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.TearDown;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

/**
 * How run this?
 *
 * <p>java -jar target/benchmarks.jar ".*Log4j2Benchmark.*" -f 1 -i 10 -wi 20 -bm sample -tu ns .
 *
 * @author admin
 */
@State(Scope.Benchmark)
@Warmup(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
public class Log4j2Benchmark {
  private static final Logger LOGGER = LogManager.getLogger(Log4j2Benchmark.class);

  public Log4j2Benchmark() {
    //
  }

  @Setup(Level.Trial)
  public void up() {
    //
  }

  @TearDown(Level.Trial)
  public void down() {
    ((LifeCycle) LogManager.getContext(false)).stop();
  }

  @Benchmark
  @BenchmarkMode(Mode.Throughput)
  @OutputTimeUnit(TimeUnit.SECONDS)
  public void throughputSimple() {
    LOGGER.info("BenchmarkMessageParams.TEST");
  }

  @Benchmark
  @BenchmarkMode(Mode.Throughput)
  @OutputTimeUnit(TimeUnit.SECONDS)
  public void throughput1Param() {
    LOGGER.info("p1={}", "one");
  }

  @Benchmark
  @BenchmarkMode(Mode.Throughput)
  @OutputTimeUnit(TimeUnit.SECONDS)
  public void throughput2Params() {
    LOGGER.info("p1={}, p2={}", "one", "two");
  }

  @Benchmark
  @BenchmarkMode(Mode.Throughput)
  @OutputTimeUnit(TimeUnit.SECONDS)
  public void throughput3Params() {
    LOGGER.info("p1={}, p2={}, p3={}", "one", "two", "three");
  }

  @Benchmark
  @BenchmarkMode(Mode.Throughput)
  @OutputTimeUnit(TimeUnit.SECONDS)
  public void throughput4Params() {
    LOGGER.info("p1={}, p2={}, p3={}, p4={}", "one", "two", "three", "four");
  }

  @Benchmark
  @BenchmarkMode(Mode.Throughput)
  @OutputTimeUnit(TimeUnit.SECONDS)
  public void throughput5Params() {
    LOGGER.info("p1={}, p2={}, p3={}, p4={}, p5={}", "one", "two", "three", "four", "five");
  }

  @Benchmark
  @BenchmarkMode(Mode.Throughput)
  @OutputTimeUnit(TimeUnit.SECONDS)
  public void throughput6Params() {
    LOGGER.info(
        "p1={}, p2={}, p3={}, p4={}, p5={}, p6={}", "one", "two", "three", "four", "five", "six");
  }

  @Benchmark
  @BenchmarkMode(Mode.Throughput)
  @OutputTimeUnit(TimeUnit.SECONDS)
  public void throughput7Params() {
    LOGGER.info(
        "p1={}, p2={}, p3={}, p4={}, p5={}, p6={}, p7={}",
        "one",
        "two",
        "three",
        "four",
        "five",
        "six",
        "seven");
  }

  @Benchmark
  @BenchmarkMode(Mode.Throughput)
  @OutputTimeUnit(TimeUnit.SECONDS)
  public void throughput8Params() {
    LOGGER.info(
        "p1={}, p2={}, p3={}, p4={}, p5={}, p6={}, p7={}, p8={}",
        "one",
        "two",
        "three",
        "four",
        "five",
        "six",
        "seven",
        "eight");
  }

  @Benchmark
  @BenchmarkMode(Mode.Throughput)
  @OutputTimeUnit(TimeUnit.SECONDS)
  public void throughput9Params() {
    LOGGER.info(
        "p1={}, p2={}, p3={}, p4={}, p5={}, p6={}, p7={}, p8={}, p9={}",
        "one",
        "two",
        "three",
        "four",
        "five",
        "six",
        "seven",
        "eight",
        "nine");
  }

  @Benchmark
  @BenchmarkMode(Mode.Throughput)
  @OutputTimeUnit(TimeUnit.SECONDS)
  public void throughput10Params() {
    LOGGER.info(
        "p1={}, p2={}, p3={}, p4={}, p5={}, p6={}, p7={}, p8={}, p9={}, p10={}",
        "one",
        "two",
        "three",
        "four",
        "five",
        "six",
        "seven",
        "eight",
        "nine",
        "ten");
  }

  @Benchmark
  @BenchmarkMode(Mode.Throughput)
  @OutputTimeUnit(TimeUnit.SECONDS)
  public void throughput11Params() {
    LOGGER.info(
        "p1={}, p2={}, p3={}, p4={}, p5={}, p6={}, p7={}, p8={}, p9={}, p10={}, p11={}",
        "one",
        "two",
        "three",
        "four",
        "five",
        "six",
        "seven",
        "eight",
        "nine",
        "ten",
        "eleven");
  }

  public static void main(String[] args) {
    Options opt =
        new OptionsBuilder()
            .include(Log4j2Benchmark.class.getSimpleName())
            .threads(1)
            .forks(1)
            .build();
    try {
      new Runner(opt).run();
    } catch (RunnerException e) {
      // Conversion into unchecked exception is also allowed.
      throw new RuntimeException(e);
    }
  }
}
