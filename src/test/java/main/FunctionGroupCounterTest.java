package main;

import main.VF2.MoleculeWithAdjacencyList;
import org.junit.jupiter.api.Test;

import java.io.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class FunctionGroupCounterTest {
    @Test
    void countSubgraphs_noMatch() {
        MoleculeWithAdjacencyList target = new MoleculeWithAdjacencyList();
        int c = target.addAtom((byte) 6);

        MoleculeWithAdjacencyList query = new MoleculeWithAdjacencyList();
        int o = query.addAtom((byte) 8);

        assertEquals(0, FunctionGroupCounter.countSubgraphs(target, query));
    }
    @Test
    void countSubgraphs_twoOxygenAtoms() {
        MoleculeWithAdjacencyList target = new MoleculeWithAdjacencyList();
        target.addAtom((byte) 8);
        target.addAtom((byte) 8);

        MoleculeWithAdjacencyList query = new MoleculeWithAdjacencyList();
        query.addAtom((byte) 8);

        assertEquals(2, FunctionGroupCounter.countSubgraphs(target, query));
    }
    @Test
    void countSubgraphs_anyAtomMatchesCarbon() {
        MoleculeWithAdjacencyList target = new MoleculeWithAdjacencyList();
        target.addAtom((byte) 6);

        MoleculeWithAdjacencyList query = new MoleculeWithAdjacencyList();
        query.addAtom((byte) 0);

        assertEquals(1, FunctionGroupCounter.countSubgraphs(target, query));
    }
    @Test
    void countSubgraphs_anyAtomMatchesAllAtoms() {
        MoleculeWithAdjacencyList target = new MoleculeWithAdjacencyList();
        target.addAtom((byte) 6);
        target.addAtom((byte) 8);
        target.addAtom((byte) 1);

        MoleculeWithAdjacencyList query = new MoleculeWithAdjacencyList();
        query.addAtom((byte) 0);

        assertEquals(3, FunctionGroupCounter.countSubgraphs(target, query));
    }
    @Test
    void countSubgraphs_anyAtomInBond() {
        MoleculeWithAdjacencyList target = new MoleculeWithAdjacencyList();
        int c = target.addAtom((byte) 6);
        int o = target.addAtom((byte) 8);
        target.addBond(c, o);

        MoleculeWithAdjacencyList query = new MoleculeWithAdjacencyList();
        int any = query.addAtom((byte) 0);
        int oxygen = query.addAtom((byte) 8);
        query.addBond(any, oxygen);

        assertEquals(1, FunctionGroupCounter.countSubgraphs(target, query));
    }
    @Test
    void countSubgraphs_anyAtomButNoBond() {
        MoleculeWithAdjacencyList target = new MoleculeWithAdjacencyList();
        target.addAtom((byte) 6);
        target.addAtom((byte) 8);

        MoleculeWithAdjacencyList query = new MoleculeWithAdjacencyList();
        int a = query.addAtom((byte) 0);
        int o = query.addAtom((byte) 8);
        query.addBond(a, o);

        assertEquals(0, FunctionGroupCounter.countSubgraphs(target, query));
    }
    @Test
    void countSubgraphs_twoOHGroups() {
        MoleculeWithAdjacencyList target = new MoleculeWithAdjacencyList();

        int o1 = target.addAtom((byte) 8);
        int h1 = target.addAtom((byte) 1);
        int o2 = target.addAtom((byte) 8);
        int h2 = target.addAtom((byte) 1);

        target.addBond(o1, h1);
        target.addBond(o2, h2);

        MoleculeWithAdjacencyList query = new MoleculeWithAdjacencyList();
        int o = query.addAtom((byte) 8);
        int h = query.addAtom((byte) 1);
        query.addBond(o, h);

        assertEquals(2, FunctionGroupCounter.countSubgraphs(target, query));
    }
    @Test
    void countSubgraphs_moleculeMatchesItself() {
        MoleculeWithAdjacencyList m = new MoleculeWithAdjacencyList();

        int c = m.addAtom((byte) 6);
        int o = m.addAtom((byte) 8);
        int h = m.addAtom((byte) 1);

        m.addBond(c, o);
        m.addBond(o, h);

        assertEquals(1, FunctionGroupCounter.countSubgraphs(m, m));
    }
    @Test
    void countSubgraphs_emptyQuery() {
        MoleculeWithAdjacencyList target = new MoleculeWithAdjacencyList();
        target.addAtom((byte) 6);

        MoleculeWithAdjacencyList query = new MoleculeWithAdjacencyList();
        assertThrows(IllegalArgumentException.class, ()-> FunctionGroupCounter.countSubgraphs(target, query));
    }
    @Test
    void countAlcohol_singleOH() {
        MoleculeWithAdjacencyList m = new MoleculeWithAdjacencyList();
        MoleculeWithAdjacencyList ohGroup = new MoleculeWithAdjacencyList();
        int c = m.addAtom((byte) 6);
        int o = m.addAtom((byte) 8);
        int h = m.addAtom((byte) 1);
        int o2 = ohGroup.addAtom((byte) 8);
        int h2 = ohGroup.addAtom((byte) 1);
        ohGroup.addBond(o2, h2);
        m.addBond(c, o);
        m.addBond(o, h);
        int result = FunctionGroupCounter.countSubgraphs(m, ohGroup);
        assertEquals(1, result);
    }

    @Test
    void countAlcohol_twoOH() {
        MoleculeWithAdjacencyList m = new MoleculeWithAdjacencyList();
        MoleculeWithAdjacencyList ohGroup = new MoleculeWithAdjacencyList();
        int c1 = m.addAtom((byte) 6);
        int c2 = m.addAtom((byte) 6);
        int o1 = m.addAtom((byte) 8);
        int o2 = m.addAtom((byte) 8);
        int h1 = m.addAtom((byte) 1);
        int h2 = m.addAtom((byte) 1);
        m.addBond(c1, c2);
        m.addBond(c1, o1);
        m.addBond(o1, h1);
        m.addBond(c2, o2);
        m.addBond(o2, h2);
        int c = ohGroup.addAtom((byte) 6);
        int o = ohGroup.addAtom((byte) 8);
        int h = ohGroup.addAtom((byte) 1);
        ohGroup.addBond(c, o);
        ohGroup.addBond(o, h);
        int result = FunctionGroupCounter.countSubgraphs(m, ohGroup);
        assertEquals(2, result);
    }

    @Test
    void countAlcohol_none() {
        MoleculeWithAdjacencyList m = new MoleculeWithAdjacencyList();
        MoleculeWithAdjacencyList ohGroup = new MoleculeWithAdjacencyList();
        int c = m.addAtom((byte) 6);
        int h1 = m.addAtom((byte) 1);
        int h2 = m.addAtom((byte) 1);
        int h3 = m.addAtom((byte) 1);
        int h4 = m.addAtom((byte) 1);
        m.addBond(c, h1);
        m.addBond(c, h2);
        m.addBond(c, h3);
        m.addBond(c, h4);
        int qc = ohGroup.addAtom((byte) 6);
        int qo = ohGroup.addAtom((byte) 8);
        int qh = ohGroup.addAtom((byte) 1);
        ohGroup.addBond(qc, qo);
        ohGroup.addBond(qo, qh);
        int result = FunctionGroupCounter.countSubgraphs(m, ohGroup);
        assertEquals(0, result);
    }

    @Test
    void countCarboxylicAcid_single() {
        MoleculeWithAdjacencyList m = new MoleculeWithAdjacencyList();
        MoleculeWithAdjacencyList cooh = new MoleculeWithAdjacencyList();
        int c1 = m.addAtom((byte) 6);
        int c2 = m.addAtom((byte) 6);
        int o1 = m.addAtom((byte) 8);
        int o2 = m.addAtom((byte) 8);
        int h = m.addAtom((byte) 1);
        m.addBond(c1, c2);
        m.addBond(c2, o1, BondType.DOUBLE);
        m.addBond(c2, o2);
        m.addBond(o2, h);
        int qc = cooh.addAtom((byte) 6);
        int qo1 = cooh.addAtom((byte) 8);
        int qo2 = cooh.addAtom((byte) 8);
        int qh = cooh.addAtom((byte) 1);
        cooh.addBond(qc, qo1, BondType.DOUBLE);
        cooh.addBond(qc, qo2);
        cooh.addBond(qo2, qh);
        int result = FunctionGroupCounter.countSubgraphs(m, cooh);
        assertEquals(1, result);
    }

    @Test
    void throwsErrorIfQueryIsBigger() {
        MoleculeWithAdjacencyList m = new MoleculeWithAdjacencyList();
        MoleculeWithAdjacencyList cooh = new MoleculeWithAdjacencyList();
        int c = m.addAtom((byte) 6);
        int o = m.addAtom((byte) 8);
        m.addBond(c, o);
        int qc = cooh.addAtom((byte) 6);
        int qo1 = cooh.addAtom((byte) 8);
        int qo2 = cooh.addAtom((byte) 8);
        int qh = cooh.addAtom((byte) 1);
        cooh.addBond(qc, qo1, BondType.DOUBLE);
        cooh.addBond(qc, qo2);
        cooh.addBond(qo2, qh);
        assertThrows(IllegalArgumentException.class, () -> FunctionGroupCounter.countSubgraphs(m, cooh));
    }

    @Test
    void double_single_phenyl() {
        MoleculeWithAdjacencyList phenyl = new MoleculeWithAdjacencyList();
        int c1_phenyl = phenyl.addAtom((byte) 6);
        int c2_phenyl = phenyl.addAtom((byte) 6);
        int c3_phenyl = phenyl.addAtom((byte) 6);
        int c4_phenyl = phenyl.addAtom((byte) 6);
        int c5_phenyl = phenyl.addAtom((byte) 6);
        int c6_phenyl = phenyl.addAtom((byte) 6);
        int h7_phenyl = phenyl.addAtom((byte) 1);
        int h8_phenyl = phenyl.addAtom((byte) 1);
        int h9_phenyl = phenyl.addAtom((byte) 1);
        int h10_phenyl = phenyl.addAtom((byte) 1);
        int h11_phenyl = phenyl.addAtom((byte) 1);
        int a_phenyl = phenyl.addAtom((byte) 0);
        phenyl.addBond(c3_phenyl, c1_phenyl, BondType.DOUBLE);
        phenyl.addBond(c1_phenyl, c5_phenyl, BondType.SINGLE);
        phenyl.addBond(c5_phenyl, c6_phenyl, BondType.DOUBLE);
        phenyl.addBond(c6_phenyl, c4_phenyl, BondType.SINGLE);
        phenyl.addBond(c4_phenyl, c2_phenyl, BondType.DOUBLE);
        phenyl.addBond(c2_phenyl, c3_phenyl, BondType.SINGLE);
        phenyl.addBond(c1_phenyl, h7_phenyl, BondType.SINGLE);
        phenyl.addBond(c2_phenyl, h8_phenyl, BondType.SINGLE);
        phenyl.addBond(c3_phenyl, h9_phenyl, BondType.SINGLE);
        phenyl.addBond(c4_phenyl, h10_phenyl, BondType.SINGLE);
        phenyl.addBond(c5_phenyl, h11_phenyl, BondType.SINGLE);
        phenyl.addBond(c6_phenyl, a_phenyl, BondType.SINGLE);
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        assertEquals(1, FunctionGroupCounter.countPhenyl(phenyl));
    }
    @Test
    void single_double_phenyl(){
        MoleculeWithAdjacencyList phenyl = new MoleculeWithAdjacencyList();
        int c1_phenyl = phenyl.addAtom((byte) 6);
        int c2_phenyl = phenyl.addAtom((byte) 6);
        int c3_phenyl = phenyl.addAtom((byte) 6);
        int c4_phenyl = phenyl.addAtom((byte) 6);
        int c5_phenyl = phenyl.addAtom((byte) 6);
        int c6_phenyl = phenyl.addAtom((byte) 6);
        int h7_phenyl = phenyl.addAtom((byte) 1);
        int h8_phenyl = phenyl.addAtom((byte) 1);
        int h9_phenyl = phenyl.addAtom((byte) 1);
        int h10_phenyl = phenyl.addAtom((byte) 1);
        int h11_phenyl = phenyl.addAtom((byte) 1);
        int a_phenyl = phenyl.addAtom((byte) 0);
        phenyl.addBond(c3_phenyl, c1_phenyl, BondType.SINGLE);
        phenyl.addBond(c1_phenyl, c5_phenyl, BondType.DOUBLE);
        phenyl.addBond(c5_phenyl, c6_phenyl, BondType.SINGLE);
        phenyl.addBond(c6_phenyl, c4_phenyl, BondType.DOUBLE);
        phenyl.addBond(c4_phenyl, c2_phenyl, BondType.SINGLE);
        phenyl.addBond(c2_phenyl, c3_phenyl, BondType.DOUBLE);
        phenyl.addBond(c1_phenyl, h7_phenyl, BondType.SINGLE);
        phenyl.addBond(c2_phenyl, h8_phenyl, BondType.SINGLE);
        phenyl.addBond(c3_phenyl, h9_phenyl, BondType.SINGLE);
        phenyl.addBond(c4_phenyl, h10_phenyl, BondType.SINGLE);
        phenyl.addBond(c5_phenyl, h11_phenyl, BondType.SINGLE);
        phenyl.addBond(c6_phenyl, a_phenyl, BondType.SINGLE);
        assertEquals(1, FunctionGroupCounter.countPhenyl(phenyl));

    }
    @Test
    void primary_amine() throws IOException {
        InputStream is = FunctionGroupCounter.class
                .getClassLoader()
                .getResourceAsStream("primary.mol");

        assertEquals(1, FunctionGroupCounter.isPrimaryAmine((MoleculeWithAdjacencyList) MolV3000.read(is)));

    }
    @Test
    void secondary_amine() throws IOException {
        InputStream is = FunctionGroupCounter.class
                .getClassLoader()
                .getResourceAsStream("secondary.mol");

        assertEquals(1, FunctionGroupCounter.isSecondaryAmine((MoleculeWithAdjacencyList) MolV3000.read(is)));

    }
    @Test
    void tertiary_amine() throws IOException {
        InputStream is = FunctionGroupCounter.class
                .getClassLoader()
                .getResourceAsStream("tertiary.mol");

        assertEquals(1, FunctionGroupCounter.isTertiaryAmine((MoleculeWithAdjacencyList) MolV3000.read(is)));

    }
    @Test
    void primary_aromatic_amine() throws IOException {
        InputStream is = FunctionGroupCounter.class
                .getClassLoader()
                .getResourceAsStream("primary_aromatic.mol");
        assertEquals(1, FunctionGroupCounter.isPrimaryAromaticAmine((MoleculeWithAdjacencyList) MolV3000.read(is)));

    }
    @Test
    void secondary_aromatic_amine() throws IOException {
        InputStream is = FunctionGroupCounter.class
                .getClassLoader()
                .getResourceAsStream("secondary_aromatic.mol");

        assertEquals(1, FunctionGroupCounter.isSecondaryAromaticAmine((MoleculeWithAdjacencyList) MolV3000.read(is)));

    }
    @Test
    void tertiary_aromatic_amine() throws IOException {
        InputStream is = FunctionGroupCounter.class
                .getClassLoader()
                .getResourceAsStream("tertiary_aromatic.mol");

        assertEquals(1, FunctionGroupCounter.isTertiaryAromaticAmine((MoleculeWithAdjacencyList) MolV3000.read(is)));
    }
}
