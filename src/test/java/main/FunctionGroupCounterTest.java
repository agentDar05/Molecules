package main;

import main.VF2.MoleculeWithAdjacencyList;
import org.junit.jupiter.api.Test;

import java.io.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FunctionGroupCounterTest {
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
        int result = FunctionGroupCounter.countSubgraphs(ohGroup, m);
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
        int result = FunctionGroupCounter.countSubgraphs(ohGroup, m);
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
        int result = FunctionGroupCounter.countSubgraphs(ohGroup, m);
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
        int result = FunctionGroupCounter.countSubgraphs(cooh, m);
        assertEquals(1, result);
    }

    @Test
    void countCarboxylicAcid_none() {
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
        int result = FunctionGroupCounter.countSubgraphs(cooh, m);
        assertEquals(0, result);
    }


}
