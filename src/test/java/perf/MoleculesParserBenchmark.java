package perf;

import main.Molecule;
import main.Parser;
import main.VF2.MoleculeWithAdjacencyList;
import org.openjdk.jmh.annotations.*;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.silent.Atom;
import org.openscience.cdk.silent.AtomContainer;

import java.io.IOException;

public class MoleculesParserBenchmark {

    public static void main(String[] args) throws IOException {
        org.openjdk.jmh.Main.main(new String[]{"MoleculesParserBenchmark"});
    }

    @Benchmark
    @Warmup(iterations = 3, time = 5)
    @Measurement(iterations = 3, time = 5)
    @Fork(1)
        public IAtomContainer cdkImplementation() {
            IAtomContainer mol = new AtomContainer();

            for (int i = 0; i < 5; i++) {
                mol.addAtom(new Atom("C"));
            }

            for (int i = 0; i < 5 - 1; i++) {
                mol.addBond(i, i + 1, org.openscience.cdk.interfaces.IBond.Order.SINGLE);
            }

            return mol;
        }
    @Benchmark
    @Warmup(iterations = 3, time = 5)
    @Measurement(iterations = 3, time = 5)
    @Fork(1)
    public Molecule myImplementation() {
        Molecule mol = new MoleculeWithAdjacencyList();

        for (int i = 0; i < 5; i++) {
            mol.addAtom((byte) 0);
        }

        for (int i = 0; i < 5 - 1; i++) {
            mol.addBond(i, i + 1, (byte) 0);
        }
        return mol;
    }
    }

