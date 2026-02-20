package main.Ullman;

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
    @Test
    public void isSubgraphTrue(){
        MoleculeWithVertices m = new MoleculeWithVertices();
        m.addAtom((byte)6); // c // 0
        m.addAtom((byte)1);//h1 // 1
        m.addAtom((byte)1);//h2 // 2
        m.addAtom((byte)1);//h3 // 3
        m.addAtom((byte)1);//h4 // 4
        m.addAtom((byte)7); //n1 // 5
        m.addAtom((byte)7); //n2 // 6
        m.addAtom((byte)8); // o // 7
        m.addBond(0, 5); // cn1
        m.addBond(0, 6); // cn2
        m.addBond(0, 7); // co
        m.addBond(5, 3); // nh1
        m.addBond(5, 4); // nh2
        m.addBond(6, 1); // n2h3
        m.addBond(6, 2); // n2h4
        MoleculeWithVertices n = new MoleculeWithVertices();
        n.addAtom((byte)1);//h1 // 0
        n.addAtom((byte)1);//h2 // 1
        n.addAtom((byte)7); //n1 // 2
        n.addBond(2, 0); // nh1
        n.addBond(2, 1); // nh2
        System.out.println(MoleculeWithVertices.isSubgraph(n, m));
    }
}
