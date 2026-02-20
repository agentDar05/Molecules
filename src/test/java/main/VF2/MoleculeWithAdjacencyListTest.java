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
    @Test
    public void isSubgraphTrue(){
        MoleculeWithAdjacencyList m = new MoleculeWithAdjacencyList();
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
        MoleculeWithAdjacencyList n = new MoleculeWithAdjacencyList();
        n.addAtom((byte)1);//h1 // 0
        n.addAtom((byte)1);//h2 // 1
        n.addAtom((byte)7); //n1 // 2
        n.addBond(2, 0); // nh1
        n.addBond(2, 1); // nh2
        System.out.println(MoleculeWithAdjacencyList.isSubgraph(n, m));
    }
}