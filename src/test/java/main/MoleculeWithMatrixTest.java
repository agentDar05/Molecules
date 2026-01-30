package main;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

public class MoleculeWithMatrixTest {
    @Test
    public void addAtom() {
        MoleculeWithMatrix m = new MoleculeWithMatrix();
        m.addAtom((byte)6);
        Assertions.assertEquals(1, m.atoms.size());
    }

    @Test
    public void addBondSymmetric() {
        MoleculeWithMatrix m = new MoleculeWithMatrix();
        m.addAtom((byte)6);
        m.addAtom((byte)8);
        m.addBond(0, 1);
        Assertions.assertTrue(m.m[0][1]);
        Assertions.assertTrue(m.m[1][0]);
    }

    @Test
    public void noBondInitially() {
        MoleculeWithMatrix m = new MoleculeWithMatrix();
        m.addAtom((byte)1);
        Assertions.assertFalse(m.m[0][0]);
    }

    @Test
    public void chain() {
        MoleculeWithMatrix m = new MoleculeWithMatrix();
        m.addAtom((byte) 1);
        m.addAtom((byte)1);
        m.addAtom((byte)1);
        m.addBond(0, 1);
        m.addBond(1, 2);
        Assertions.assertTrue(m.m[1][2]);
    }

    @Test
    public void atomValuesStored() {
        MoleculeWithMatrix m = new MoleculeWithMatrix();
        m.addAtom((byte) 8);
        Assertions.assertEquals(8, m.atoms.get(0));
    }

    @Test
    public void multipleAtomsNoBonds() {
        MoleculeWithMatrix m = new MoleculeWithMatrix();
        m.addAtom((byte) 1);
        m.addAtom((byte)1);
        Assertions.assertFalse(m.m[0][1]);
    }

    @Test
    public void reverseBondAccess() {
        MoleculeWithMatrix m = new MoleculeWithMatrix();
        m.addAtom((byte) 6);
        m.addAtom((byte) 1);
        m.addBond(1, 0);
        Assertions.assertTrue(m.m[0][1]);
    }
    @Test
    public void isSubgraphTrue(){
        MoleculeWithMatrix m = new MoleculeWithMatrix();
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
        MoleculeWithMatrix n = new MoleculeWithMatrix();
        n.addAtom((byte)1);//h1 // 0
        n.addAtom((byte)1);//h2 // 1
        n.addAtom((byte)7); //n1 // 2
        n.addBond(2, 0); // nh1
        n.addBond(2, 1); // nh2
        System.out.println(MoleculeWithMatrix.isSubgraph(n, m));
    }
}