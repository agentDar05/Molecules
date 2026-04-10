package perf;

import main.MolV3000;
import main.Parser;
import org.openjdk.jmh.annotations.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@Warmup(iterations = 3, time = 5)
@Measurement(iterations = 3, time = 5)
@Fork(1)
public class MoleculesParserBenchmark {
    public static void main(String[] args) throws Exception {
        org.openjdk.jmh.Main.main(new String[]{"MoleculesParserBenchmark"});
    }
    @State(Scope.Benchmark)
    public static class BenchmarkState {
        byte[] data;
        @Setup
        public void setup() throws IOException {
            InputStream is = MoleculesParserBenchmark.class
                    .getClassLoader()
                    .getResourceAsStream("ketcher.mol");

            if (is == null) {
                throw new RuntimeException("File not found: ketcher.mol");
            }

            data = is.readAllBytes();
            is.close();
        }
    }
    @Benchmark
    public void MolV3000ReaderBench(BenchmarkState state) throws IOException {
        InputStream is = new ByteArrayInputStream(state.data);
        MolV3000.reader(is);
    }

}