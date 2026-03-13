package main.VF2;
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
        Assertions.assertTrue(m.bonds[0][1]);
        Assertions.assertTrue(m.bonds[1][0]);
    }

    @Test
    public void noBondInitially() {
        MoleculeWithMatrix m = new MoleculeWithMatrix();
        m.addAtom((byte)1);
        Assertions.assertFalse(m.bonds[0][0]);
    }

    @Test
    public void chain() {
        MoleculeWithMatrix m = new MoleculeWithMatrix();
        m.addAtom((byte) 1);
        m.addAtom((byte)1);
        m.addAtom((byte)1);
        m.addBond(0, 1);
        m.addBond(1, 2);
        Assertions.assertTrue(m.bonds[1][2]);
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
        Assertions.assertFalse(m.bonds[0][1]);
    }

    @Test
    public void reverseBondAccess() {
        MoleculeWithMatrix m = new MoleculeWithMatrix();
        m.addAtom((byte) 6);
        m.addAtom((byte) 1);
        m.addBond(1, 0);
        Assertions.assertTrue(m.bonds[0][1]);
    }

}