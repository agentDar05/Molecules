package main.VF2;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

public class MoleculeWithVerticesTest {

    @Test
    public void addAtom() {
        MoleculeWithVertices m = new MoleculeWithVertices();
        m.addAtom((byte)6);
        Assertions.assertEquals(1, m.vertices.size());
        Assertions.assertEquals(6, m.vertices.get(0));
    }

    @Test
    public void noNeighborsInitially() {
        MoleculeWithVertices m = new MoleculeWithVertices();
        m.addAtom((byte)1);
        Assertions.assertTrue(m.bonds.isEmpty());
    }

    @Test
    public void addBondAddsTwoIndices() {
        MoleculeWithVertices m = new MoleculeWithVertices();
        m.addAtom((byte)6);
        m.addAtom((byte)8);
        m.addBond(0, 1);

        Assertions.assertEquals(2, m.bonds.size());
        Assertions.assertEquals(0, m.bonds.get(0));
        Assertions.assertEquals(1, m.bonds.get(1));
    }


    @Test
    public void multipleDisconnectedAtoms() {
        MoleculeWithVertices m = new MoleculeWithVertices();
        m.addAtom((byte)6);
        m.addAtom((byte)8);

        Assertions.assertEquals(2, m.vertices.size());
        Assertions.assertTrue(m.bonds.isEmpty());
    }

}
