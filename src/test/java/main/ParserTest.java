package main;

import main.VF2.MoleculeWithAdjacencyList;
import org.junit.jupiter.api.Test;

import java.io.*;

import static main.Parser.isSubgraph;
import static org.junit.jupiter.api.Assertions.*;

public class ParserTest {
//    @Test
//    void alcoholAdjacency_positive() {
//        MoleculeWithAdjacencyList m = new MoleculeWithAdjacencyList();
//        m.addAtom((byte) 1); // 0
//        m.addAtom((byte) 8); // 1
//        m.addAtom((byte) 6); // 2
//        m.addBond(1, 0);
//        m.addBond(2, 1);
//
//        assertTrue(Parser.isAlcoholAdjacency(m));
//    }
//
//    @Test
//    void alcoholAdjacency_noBond() {
//        MoleculeWithAdjacencyList m = new MoleculeWithAdjacencyList();
//        m.addAtom((byte) 1); // 0
//        m.addAtom((byte) 8); // 1
//        m.addAtom((byte) 6); // 2
//        assertFalse(Parser.isAlcoholAdjacency(m));
//
//
//    }
//
//    @Test
//    void alcoholAdjacency_differentBond() {
//        MoleculeWithAdjacencyList m = new MoleculeWithAdjacencyList();
//        m.addAtom((byte) 1); // 0
//        m.addAtom((byte) 8); // 1
//        m.addAtom((byte) 6); // 2
//        m.addBond(1, 0);
//        m.addBond(0, 2);
//        assertFalse(Parser.isAlcoholAdjacency(m));
//    }
//    @Test
//    void carboxyAdjacency_positive() {
//        MoleculeWithAdjacencyList m = new MoleculeWithAdjacencyList();
//        m.addAtom((byte) 6);
//        m.addAtom((byte) 8);
//        m.addAtom((byte) 8);
//        m.addAtom((byte) 1);
//        m.addBond(0, 1, BondType.DOUBLE);
//        m.addBond(0, 2, BondType.SINGLE);
//        m.addBond(2, 3, BondType.SINGLE);
//        assertTrue(Parser.isCarboxylicAcidAdjacency(m));
//    }
//
//    @Test
//    void carboxyAdjacency_noBond() {
//        MoleculeWithAdjacencyList m = new MoleculeWithAdjacencyList();
//        m.addAtom((byte) 6);
//        m.addAtom((byte) 8);
//        m.addAtom((byte) 8);
//        m.addAtom((byte) 1);
//        assertFalse(Parser.isCarboxylicAcidAdjacency(m));
//    }
//
//    @Test
//    void carboxyAdjacency_incompleteBond() {
//        MoleculeWithAdjacencyList m = new MoleculeWithAdjacencyList();
//        m.addAtom((byte) 6);
//        m.addAtom((byte) 8);
//        m.addAtom((byte) 8);
//        m.addAtom((byte) 1);
//        m.addBond(0, 1, BondType.SINGLE);
//        m.addBond(0, 2, BondType.SINGLE);
//        m.addBond(2, 3, BondType.SINGLE);
//        assertFalse(Parser.isCarboxylicAcidAdjacency(m));
//    }
//    @Test
//    public void isAdjacencySubgraphTrue() {
//        main.VF2.MoleculeWithAdjacencyList m = new main.VF2.MoleculeWithAdjacencyList();
//        m.addAtom((byte) 6); // c // 0
//        m.addAtom((byte) 1);//h1 // 1
//        m.addAtom((byte) 1);//h2 // 2
//        m.addAtom((byte) 1);//h3 // 3
//        m.addAtom((byte) 1);//h4 // 4
//        m.addAtom((byte) 7); //n1 // 5
//        m.addAtom((byte) 7); //n2 // 6
//        m.addAtom((byte) 8); // o // 7
//        m.addBond(0, 5); // cn1
//        m.addBond(0, 6); // cn2
//        m.addBond(0, 7); // co
//        m.addBond(5, 3); // nh1
//        m.addBond(5, 4); // nh2
//        m.addBond(6, 1); // n2h3
//        m.addBond(6, 2); // n2h4
//        main.VF2.MoleculeWithAdjacencyList n = new main.VF2.MoleculeWithAdjacencyList();
//        n.addAtom((byte) 1);//h1 // 0
//        n.addAtom((byte) 1);//h2 // 1
//        n.addAtom((byte) 7); //n1 // 2
//        n.addBond(2, 0); // nh1
//        n.addBond(2, 1); // nh2
//        System.out.println(isSubgraph(n, m));
//    }
    @Test
    public void throwsErrorIfSizeOfMoleculesAreNotEqual() {
        MoleculeWithAdjacencyList m1 = new MoleculeWithAdjacencyList();
        MoleculeWithAdjacencyList m2 = new MoleculeWithAdjacencyList();
        m2.addAtom((byte) 1);
        assertThrows(RuntimeException.class, ()->Parser.compareMolecules(m1, m2), "Size of molecules are not equal: 0, 1");
    }
    @Test
    public void throwsErrorIfAtomsInMoleculesAreDifferent() {
        MoleculeWithAdjacencyList m1 = new MoleculeWithAdjacencyList();
        MoleculeWithAdjacencyList m2 = new MoleculeWithAdjacencyList();
        m1.addAtom((byte) 1);
        m1.addAtom((byte) 3);

        m2.addAtom((byte) 1);
        m2.addAtom((byte) 2);

        assertThrows(RuntimeException.class, ()->Parser.compareMolecules(m1, m2), "Atoms in molecules are not equal: [0, 0, 0, 0, 0, 0, 0, 0, 1, 3] [0, 0, 0, 0, 0, 0, 0, 0, 1, 2]");
    }
    @Test
    public void throwsErrorIfAmountOfBondsIsDifferent() {
        MoleculeWithAdjacencyList m1 = new MoleculeWithAdjacencyList();
        MoleculeWithAdjacencyList m2 = new MoleculeWithAdjacencyList();
        m1.addAtom((byte) 1);
        m1.addAtom((byte) 1);
        m1.addAtom((byte) 1);
        m1.addBond(0, 1, BondType.SINGLE);

        m2.addAtom((byte) 1);
        m2.addAtom((byte) 1);
        m2.addAtom((byte) 1);
        m2.addBond(0, 1, BondType.SINGLE);
        m2.addBond(1, 2, BondType.SINGLE);

        assertThrows(RuntimeException.class, ()->Parser.compareMolecules(m1, m2), "Size of molecules are not equal: 0, 1");
    }
}