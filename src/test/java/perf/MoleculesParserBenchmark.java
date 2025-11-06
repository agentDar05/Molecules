package perf;

import main.Molecules;
import org.openjdk.jmh.annotations.*;

import java.io.IOException;

public class MoleculesParserBenchmark {

    public static void main(String[] args) throws IOException {
        org.openjdk.jmh.Main.main(new String[]{"MoleculesParserBenchmark"});
    }

    @Benchmark
    @Warmup(iterations = 3, time = 5)
    @Measurement(iterations = 3, time = 5)
    @Fork(1)
    public void test1(Data data) {
        data.m.parse("H3PO4Cl100OOH");
    }

    @State(Scope.Benchmark)
    public static class Data {
        final Molecules m = new Molecules();
    }
}
