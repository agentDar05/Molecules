package main.VF2;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class MoleculeWithAdjacencyListTest{
    @Test
    public void addSingleAtom() {
        MoleculeWithAdjacencyList m = new MoleculeWithAdjacencyList();
        m.addAtom((byte)6);
        Assertions.assertEquals(1, m.atoms.size());
    }

    @Test
    public void addTwoAtoms() {
        MoleculeWithAdjacencyList m = new MoleculeWithAdjacencyList();
        m.addAtom((byte)6);
        m.addAtom((byte)8);
        Assertions.assertEquals(2, m.atoms.size());
    }

    @Test
    public void addBondStoresBothSides() {
        MoleculeWithAdjacencyList m = new MoleculeWithAdjacencyList();
        m.addAtom((byte)6);
        m.addAtom((byte)8);
        m.addBond(0, 1);
        Assertions.assertTrue(m.bonds.get(0).contains(1));
        Assertions.assertTrue(m.bonds.get(1).contains(0));
    }

    @Test
    public void multipleBonds() {
        MoleculeWithAdjacencyList m = new MoleculeWithAdjacencyList();
        m.addAtom((byte)6);
        m.addAtom((byte)8);
        m.addAtom((byte)8);
        m.addBond(0, 1);
        m.addBond(0, 2);
        Assertions.assertEquals(2, m.bonds.getFirst().size());
    }

    @Test
    public void noBondInitially() {
        MoleculeWithAdjacencyList m = new MoleculeWithAdjacencyList();
        m.addAtom((byte)1);
        Assertions.assertTrue(m.bonds.getFirst().isEmpty());
    }

    @Test
    public void chainStructure() {
        MoleculeWithAdjacencyList m = new MoleculeWithAdjacencyList();
        m.addAtom((byte)1);
        m.addAtom((byte)1);
        m.addAtom((byte)1);
        m.addBond(0, 1);
        m.addBond(1, 2);
        Assertions.assertTrue(m.bonds.get(1).contains(0));
        Assertions.assertTrue(m.bonds.get(1).contains(2));
    }
    @Test
    public void atomValuesStored() {
        MoleculeWithAdjacencyList m = new MoleculeWithAdjacencyList();
        m.addAtom((byte)6);
        Assertions.assertEquals(6, m.atoms.get(0));
    }

    @Test
    public void twoDisconnectedAtoms() {
        MoleculeWithAdjacencyList m = new MoleculeWithAdjacencyList();
        m.addAtom((byte)6);
        m.addAtom((byte)8);
        Assertions.assertTrue(m.bonds.get(0).isEmpty());
        Assertions.assertTrue(m.bonds.get(1).isEmpty());
    }
    @Test
    public void returnBonds() {
        MoleculeWithAdjacencyList m = new MoleculeWithAdjacencyList();

        m.addAtom((byte) 6);
        m.addAtom((byte) 7);
        m.addAtom((byte) 5);
        m.addAtom((byte) 1);
        m.addAtom((byte) 2);
        m.addAtom((byte) 4);
        m.addAtom((byte) 3);

        m.addBond(1, 0);
        m.addBond(1, 2);
        m.addBond(1, 3);
        m.addBond(1, 4);
        m.addBond(2, 5);
        ArrayList<Integer> expected = new ArrayList<>();
        expected.add(0);
        expected.add(2);
        expected.add(3);
        expected.add(4);
        Assertions.assertEquals(m.getBonds(1),expected);
    }
}