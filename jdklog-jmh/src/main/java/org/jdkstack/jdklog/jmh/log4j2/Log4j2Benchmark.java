package org.jdkstack.jdklog.jmh.log4j2;

import java.util.concurrent.TimeUnit;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.LifeCycle;
import org.jdkstack.jdklog.jmh.Constants;
import org.jdkstack.jdklog.logging.api.exception.StudyJuliRuntimeException;
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
@Warmup(iterations = Constants.LOOP, time = 1)
@Measurement(iterations = Constants.LOOP, time = 1)
public class Log4j2Benchmark {
  /** . */
  private static final Logger LOGGER = LogManager.getLogger(Log4j2Benchmark.class);

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @author admin
   */
  @Setup(Level.Trial)
  public final void setup() {
    //
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @author admin
   */
  @TearDown(Level.Trial)
  public final void down() {
    ((LifeCycle) LogManager.getContext(false)).stop();
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @author admin
   */
  @Benchmark
  @BenchmarkMode(Mode.Throughput)
  @OutputTimeUnit(TimeUnit.SECONDS)
  public final void throughputSimple() {
    LOGGER.info("BenchmarkMessageParams.TEST");
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @author admin
   */
  @Benchmark
  @BenchmarkMode(Mode.Throughput)
  @OutputTimeUnit(TimeUnit.SECONDS)
  public final void throughput1Param() {
    LOGGER.info("p1={}", Constants.ONE);
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @author admin
   */
  @Benchmark
  @BenchmarkMode(Mode.Throughput)
  @OutputTimeUnit(TimeUnit.SECONDS)
  public final void throughput2Params() {
    LOGGER.info("p1={}, p2={}", Constants.ONE, Constants.TWO);
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @author admin
   */
  @Benchmark
  @BenchmarkMode(Mode.Throughput)
  @OutputTimeUnit(TimeUnit.SECONDS)
  public final void throughput3Params() {
    LOGGER.info("p1={}, p2={}, p3={}", Constants.ONE, Constants.TWO, Constants.THREE);
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @author admin
   */
  @Benchmark
  @BenchmarkMode(Mode.Throughput)
  @OutputTimeUnit(TimeUnit.SECONDS)
  public final void throughput4Params() {
    LOGGER.info(
        "p1={}, p2={}, p3={}, p4={}",
        Constants.ONE,
        Constants.TWO,
        Constants.THREE,
        Constants.FOUR);
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @author admin
   */
  @Benchmark
  @BenchmarkMode(Mode.Throughput)
  @OutputTimeUnit(TimeUnit.SECONDS)
  public final void throughput5Params() {
    LOGGER.info(
        "p1={}, p2={}, p3={}, p4={}, p5={}",
        Constants.ONE,
        Constants.TWO,
        Constants.THREE,
        Constants.FOUR,
        Constants.FIVE);
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @author admin
   */
  @Benchmark
  @BenchmarkMode(Mode.Throughput)
  @OutputTimeUnit(TimeUnit.SECONDS)
  public final void throughput6Params() {
    LOGGER.info(
        "p1={}, p2={}, p3={}, p4={}, p5={}, p6={}",
        Constants.ONE,
        Constants.TWO,
        Constants.THREE,
        Constants.FOUR,
        Constants.FIVE,
        Constants.SIX);
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @author admin
   */
  @Benchmark
  @BenchmarkMode(Mode.Throughput)
  @OutputTimeUnit(TimeUnit.SECONDS)
  public final void throughput7Params() {
    LOGGER.info(
        "p1={}, p2={}, p3={}, p4={}, p5={}, p6={}, p7={}",
        Constants.ONE,
        Constants.TWO,
        Constants.THREE,
        Constants.FOUR,
        Constants.FIVE,
        Constants.SIX,
        Constants.SEVEN);
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @author admin
   */
  @Benchmark
  @BenchmarkMode(Mode.Throughput)
  @OutputTimeUnit(TimeUnit.SECONDS)
  public final void throughput8Params() {
    LOGGER.info(
        "p1={}, p2={}, p3={}, p4={}, p5={}, p6={}, p7={}, p8={}",
        Constants.ONE,
        Constants.TWO,
        Constants.THREE,
        Constants.FOUR,
        Constants.FIVE,
        Constants.SIX,
        Constants.SEVEN,
        Constants.EIGHT);
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @author admin
   */
  @Benchmark
  @BenchmarkMode(Mode.Throughput)
  @OutputTimeUnit(TimeUnit.SECONDS)
  public final void throughput9Params() {
    LOGGER.info(
        "p1={}, p2={}, p3={}, p4={}, p5={}, p6={}, p7={}, p8={}, p9={}",
        Constants.ONE,
        Constants.TWO,
        Constants.THREE,
        Constants.FOUR,
        Constants.FIVE,
        Constants.SIX,
        Constants.SEVEN,
        Constants.EIGHT,
        Constants.NINE);
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @author admin
   */
  @Benchmark
  @BenchmarkMode(Mode.Throughput)
  @OutputTimeUnit(TimeUnit.SECONDS)
  public final void throughput10Params() {
    LOGGER.info(
        "p1={}, p2={}, p3={}, p4={}, p5={}, p6={}, p7={}, p8={}, p9={}, p10={}",
        Constants.ONE,
        Constants.TWO,
        Constants.THREE,
        Constants.FOUR,
        Constants.FIVE,
        Constants.SIX,
        Constants.SEVEN,
        Constants.EIGHT,
        Constants.NINE,
        Constants.TEN);
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @author admin
   */
  @Benchmark
  @BenchmarkMode(Mode.Throughput)
  @OutputTimeUnit(TimeUnit.SECONDS)
  public final void throughput11Params() {
    LOGGER.info(
        "p1={}, p2={}, p3={}, p4={}, p5={}, p6={}, p7={}, p8={}, p9={}, p10={}, p11={}",
        Constants.ONE,
        Constants.TWO,
        Constants.THREE,
        Constants.FOUR,
        Constants.FIVE,
        Constants.SIX,
        Constants.SEVEN,
        Constants.EIGHT,
        Constants.NINE,
        Constants.TEN,
        Constants.ELEVEN);
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param args args.
   * @author admin
   */
  public static void main(final String... args) {
    final Options opt =
        new OptionsBuilder()
            .include(Log4j2Benchmark.class.getSimpleName())
            .threads(1)
            .forks(1)
            .build();
    try {
      new Runner(opt).run();
    } catch (final RunnerException e) {
      // Conversion into unchecked exception is also allowed.
      throw new StudyJuliRuntimeException(e);
    }
  }
}
