package main;

import main.VF2.MoleculeWithAdjacencyList;
import org.junit.jupiter.api.Test;
import perf.MoleculesParserBenchmark;

import java.io.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MolV3000Test {
    @Test
    void write_emptyMolecule_positive() throws IOException {
        Molecule m = new MoleculeWithAdjacencyList();
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        MolV3000.write(m, os);
        String actual = os.toString();
        String expected = "\n" +
                "  -MolV3000Writer\n" +
                "\n" +
                "  0  0  0  0  0  0  0  0  0  0  0 V3000\n" +
                "M  V30 BEGIN CTAB\n" +
                "M  V30 COUNTS 0 0 0 0 0\n" +
                "M  V30 BEGIN ATOM\n" +
                "M  V30 END ATOM\n" +
                "M  V30 BEGIN BOND\n" +
                "M  V30 END BOND\n" +
                "M  V30 END CTAB\n" +
                "M  END";

        assertEquals(expected, actual);
    }

    @Test
    void write_writesCorrectMolecule() throws IOException {
        Molecule m = new MoleculeWithAdjacencyList();
        OutputStream os = new FileOutputStream("output.mol");
        m.addAtom((byte) 15);
        m.addAtom((byte)7);
        m.addAtom((byte)7);
        m.addAtom((byte)7);
        m.addAtom((byte)0);
        m.addAtom((byte)0);
        m.addBond(0, 1, BondType.DOUBLE);
        m.addBond(0, 2);
        m.addBond(0, 3);
        m.addBond(2, 4);
        m.addBond(3, 5);
        MolV3000.write(m, os);
        java.nio.file.Files.lines(java.nio.file.Path.of("output.mol"))
                .forEach(System.out::println);
    }
    @Test
    void read_readMolecule() throws IOException {
        InputStream is = MoleculesParserBenchmark.class
                .getClassLoader()
                .getResourceAsStream("ketcher.mol");
        MoleculeWithAdjacencyList output = (MoleculeWithAdjacencyList) MolV3000.read(is);
        MoleculeWithAdjacencyList expected = new  MoleculeWithAdjacencyList();
        expected.addAtom((byte) 15);
        expected.addAtom((byte)7);
        expected.addAtom((byte)7);
        expected.addAtom((byte)7);
        expected.addAtom((byte)0);
        expected.addAtom((byte)0);
        expected.addBond(0, 1, BondType.DOUBLE);
        expected.addBond(0, 2);
        expected.addBond(0, 3);
        expected.addBond(2, 4);
        expected.addBond(3, 5);

    }
    @Test
    void readAndWriteMolecules() throws IOException {
        InputStream is = MoleculesParserBenchmark.class
                .getClassLoader()
                .getResourceAsStream("ketcher.mol");
        OutputStream os = new FileOutputStream("output.mol");
        MoleculeWithAdjacencyList expected = new  MoleculeWithAdjacencyList();
        expected.addAtom((byte) 15);
        expected.addAtom((byte)7);
        expected.addAtom((byte)7);
        expected.addAtom((byte)7);
        expected.addAtom((byte)0);
        expected.addAtom((byte)0);
        expected.addBond(0, 1, BondType.DOUBLE);
        expected.addBond(0, 2);
        expected.addBond(0, 3);
        expected.addBond(2, 4);
        expected.addBond(3, 5);
        MolV3000.write(MolV3000.read(is), os);
        java.nio.file.Files.lines(java.nio.file.Path.of("output.mol"))
                .forEach(System.out::println);
    }
    @Test
    void parsesCharge() throws IOException {
        InputStream is = MoleculesParserBenchmark.class
                .getClassLoader()
                .getResourceAsStream("nh2.mol");
        MoleculeWithAdjacencyList output = (MoleculeWithAdjacencyList) MolV3000.read(is);
        System.out.println(Parser.Utils.bytesToString(output.getCharges()));
    }
    @Test
    void ignoresCollection() throws IOException {
        InputStream is = MolV3000Test.class.getClassLoader().getResourceAsStream("h2so4.mol");
        MolV3000.read(is);
    }
}
