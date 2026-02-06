package main;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ParserTest {
    public static void main(String[] args) {
        Parser m = new Parser();
        for (int i = 0; i < 10_000_000; i++) {
            m.parse("H3PO4Cl100OOH");
        }
    }
    @Test
    void alcoholAdjacency_positive() {
        MoleculeWithAdjacencyList m = new MoleculeWithAdjacencyList();
        m.addAtom((byte) 1); // 0
        m.addAtom((byte) 8); // 1
        m.addAtom((byte) 6); // 2
        m.addBond(1, 0);
        m.addBond(2, 1);

        assertTrue(Parser.isAlcoholAdjacency(m));
    }

    @Test
    void alcoholAdjacency_noBond() {
        MoleculeWithAdjacencyList m = new MoleculeWithAdjacencyList();
        m.addAtom((byte) 1); // 0
        m.addAtom((byte) 8); // 1
        m.addAtom((byte) 6); // 2
        assertFalse(Parser.isAlcoholAdjacency(m));


    }

    @Test
    void alcoholAdjacency_differentBond() {
        MoleculeWithAdjacencyList m = new MoleculeWithAdjacencyList();
        m.addAtom((byte) 1); // 0
        m.addAtom((byte) 8); // 1
        m.addAtom((byte) 6); // 2
        m.addBond(1, 0);
        m.addBond(0, 2);
        assertFalse(Parser.isAlcoholAdjacency(m));
    }
    @Test
    void alcoholMatrix_positive() {
        MoleculeWithMatrix m = new MoleculeWithMatrix();
        m.addAtom((byte)1); // 0
        m.addAtom((byte)8); // 1
        m.addAtom((byte)6); // 2
        m.addBond(1, 0);
        m.addBond(2, 1);

        assertTrue(Parser.isAlcoholMatrix(m));
    }

    @Test
    void alcoholMatrix_noBond() {
        MoleculeWithMatrix m = new MoleculeWithMatrix();
        m.addAtom((byte)1); // 0
        m.addAtom((byte)8); // 1
        m.addAtom((byte)6); // 2
        assertFalse(Parser.isAlcoholMatrix(m));


    }

    @Test
    void alcoholMatrix_differentBond() {
        MoleculeWithMatrix m = new MoleculeWithMatrix();
        m.addAtom((byte)1); // 0
        m.addAtom((byte)8); // 1
        m.addAtom((byte)6); // 2
        m.addBond(1, 0);
        m.addBond(0, 2);
        assertFalse(Parser.isAlcoholMatrix(m));
    }



    @Test
    void alcoholVertices_positive() {
        MoleculeWithVertices m = new MoleculeWithVertices();
        m.addAtom((byte)1); // 0
        m.addAtom((byte)8); // 1
        m.addAtom((byte)6); // 2
        m.addBond(1, 0);
        m.addBond(2, 1);

        assertTrue(Parser.isAlcoholVertices(m));
    }

    @Test
    void alcoholVertices_noBond() {
        MoleculeWithVertices m = new MoleculeWithVertices();
        m.addAtom((byte)1); // 0
        m.addAtom((byte)8); // 1
        m.addAtom((byte)6); // 2
        assertFalse(Parser.isAlcoholVertices(m));


    }

    @Test
    void alcoholVertices_differentBond() {
        MoleculeWithVertices m = new MoleculeWithVertices();
        m.addAtom((byte)1); // 0
        m.addAtom((byte)8); // 1
        m.addAtom((byte)6); // 2
        m.addBond(1, 0);
        m.addBond(0, 2);
        assertFalse(Parser.isAlcoholVertices(m));
    }


    @Test
    void carboxyAdjacency_positive() {
        MoleculeWithAdjacencyList m = new MoleculeWithAdjacencyList();
        m.addAtom((byte)6);
        m.addAtom((byte)8);
        m.addAtom((byte)8);
        m.addAtom((byte)1);
        m.addBond(0, 1, BondType.DOUBLE);
        m.addBond(0, 2, BondType.SINGLE);
        m.addBond(2, 3, BondType.SINGLE);
        assertTrue(Parser.isCarboxylicAcidAdjacency(m));
    }

    @Test
    void carboxyAdjacency_noBond() {
        MoleculeWithAdjacencyList m = new MoleculeWithAdjacencyList();
        m.addAtom((byte)6);
        m.addAtom((byte)8);
        m.addAtom((byte)8);
        m.addAtom((byte)1);
        assertFalse(Parser.isCarboxylicAcidAdjacency(m));
    }

    @Test
    void carboxyAdjacency_incompleteBond() {
        MoleculeWithAdjacencyList m = new MoleculeWithAdjacencyList();
        m.addAtom((byte)6);
        m.addAtom((byte)8);
        m.addAtom((byte)8);
        m.addAtom((byte)1);
        m.addBond(0, 1, BondType.SINGLE);
        m.addBond(0, 2, BondType.SINGLE);
        m.addBond(2, 3, BondType.SINGLE);
        assertFalse(Parser.isCarboxylicAcidAdjacency(m));
    }

    @Test
    void carboxyMatrix_positive() {
        MoleculeWithMatrix m = new MoleculeWithMatrix();
        m.addAtom((byte)6);
        m.addAtom((byte)8);
        m.addAtom((byte)8);
        m.addAtom((byte)1);
        m.addBond(0, 1, BondType.DOUBLE);
        m.addBond(0, 2, BondType.SINGLE);
        m.addBond(2, 3, BondType.SINGLE);
        assertTrue(Parser.isCarboxylicAcidMatrix(m));
    }

    @Test
    void carboxyMatrix_noBond() {
        MoleculeWithMatrix m = new MoleculeWithMatrix();
        m.addAtom((byte)6);
        m.addAtom((byte)8);
        m.addAtom((byte)8);
        m.addAtom((byte)1);
        assertFalse(Parser.isCarboxylicAcidMatrix(m));
    }

    @Test
    void carboxyMatrix_incompleteBond() {
        MoleculeWithMatrix m = new MoleculeWithMatrix();
        m.addAtom((byte)6);
        m.addAtom((byte)8);
        m.addAtom((byte)8);
        m.addAtom((byte)1);
        m.addBond(0, 1);
        m.addBond(0, 2);
        assertFalse(Parser.isCarboxylicAcidMatrix(m));
    }

    @Test
    void carboxyVertices_positive() {
        MoleculeWithVertices m = new MoleculeWithVertices();
        m.addAtom((byte)6);
        m.addAtom((byte)8);
        m.addAtom((byte)8);
        m.addAtom((byte)1);
        m.addBond(0, 1, BondType.DOUBLE);
        m.addBond(0, 2, BondType.SINGLE);
        m.addBond(2, 3, BondType.SINGLE);
        assertTrue(Parser.isCarboxylicAcidVertices(m));
    }

    @Test
    void carboxyVertices_noBond() {
        MoleculeWithVertices m = new MoleculeWithVertices();
        m.addAtom((byte)6);
        m.addAtom((byte)8);
        m.addAtom((byte)8);
        m.addAtom((byte)1);
        assertFalse(Parser.isCarboxylicAcidVertices(m));
    }

    @Test
    void carboxyVertices_incompleteBond() {
        MoleculeWithVertices m = new MoleculeWithVertices();
        m.addAtom((byte)6);
        m.addAtom((byte)8);
        m.addAtom((byte)8);
        m.addAtom((byte)1);
        m.addBond(0, 1, BondType.SINGLE);
        m.addBond(0, 2, BondType.SINGLE);
        m.addBond(2, 3, BondType.SINGLE);
        assertFalse(Parser.isCarboxylicAcidVertices(m));
    }

}