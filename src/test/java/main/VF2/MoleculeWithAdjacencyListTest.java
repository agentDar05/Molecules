package main.VF2;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
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
        Assertions.assertEquals(2, m.bonds.get(0).size());
    }

    @Test
    public void noBondInitially() {
        MoleculeWithAdjacencyList m = new MoleculeWithAdjacencyList();
        m.addAtom((byte)1);
        Assertions.assertTrue(m.bonds.get(0).isEmpty());
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

}