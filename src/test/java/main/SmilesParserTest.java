package main;

import main.VF2.MoleculeWithAdjacencyList;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class SmilesParserTest {
    private static final byte H = 1;
    private static final byte C = 6;
    private static final byte N = 7;
    private static final byte Cl = 17;
    private static final byte Br = 18;


    @Test
    void parseAtoms() {
        SmilesParser.read("CCC");
        MoleculeWithAdjacencyList m = new MoleculeWithAdjacencyList();
        m.addAtom(C);
        m.addAtom(C);
        m.addAtom(C);
    }
    @Test
    void parseTwoLetterAtoms() {
        SmilesParser.read("ClBrCl");
        MoleculeWithAdjacencyList m = new MoleculeWithAdjacencyList();
        m.addAtom(Cl);
        m.addAtom(Br);
        m.addAtom(Cl);
    }
    @Test
    void parseSingleBonds() {
        SmilesParser.read("C-Cl-Br-C");
        MoleculeWithAdjacencyList m = new MoleculeWithAdjacencyList();
        m.addAtom(C);
        m.addAtom(Cl);
        m.addAtom(Br);
        m.addAtom(C);
    }
    @Test
    void parseDoubleBonds() {
        SmilesParser.read("C=Cl=Br=C");
        MoleculeWithAdjacencyList m = new MoleculeWithAdjacencyList();
        m.addAtom(C);
        m.addAtom(Cl);
        m.addAtom(Br);
        m.addAtom(C);
        m.addBond(0, 1, BondType.DOUBLE);
        m.addBond(1, 2, BondType.DOUBLE);
        m.addBond(2, 3, BondType.DOUBLE);


    }
    @Test
    void parseTripleBonds() {
        SmilesParser.read("C#Cl#Br#C");
    }
    @Test
    void parseDifferentBonds() {
        SmilesParser.read("C-Cl=Br#C");
    }
    @Test
    void parseBranchedStructure(){
        MoleculeWithAdjacencyList m = new MoleculeWithAdjacencyList();
        m.addAtom(C); // 0
        m.addAtom(C); // 1
        m.addAtom(C); // 2
        m.addAtom(C); // 3
        m.addAtom(C); // 4
        m.addAtom(C); // 5
        m.addBond(0, 1, BondType.SINGLE);
        m.addBond(0, 2, BondType.SINGLE);
        m.addBond(2, 3, BondType.SINGLE);
        m.addBond(3, 4, BondType.SINGLE);
        m.addBond(4, 5, BondType.SINGLE);
        Parser.compareMolecules(SmilesParser.read("C(C)CCCC"), m);
    }
    @Test
    void parseAromaticStructure(){
        MoleculeWithAdjacencyList m = new MoleculeWithAdjacencyList();
        m.addAtom(C); // 0
        m.addAtom(C); // 1
        m.addAtom(C); // 2
        m.addAtom(C); // 3
        m.addAtom(C); // 4
        m.addAtom(C); // 5
        m.addBond(0, 1, BondType.AROMATIC);
        m.addBond(0, 2, BondType.AROMATIC);
        m.addBond(2, 3, BondType.AROMATIC);
        m.addBond(3, 4, BondType.AROMATIC);
        m.addBond(4, 5, BondType.AROMATIC);
        Parser.compareMolecules(SmilesParser.read("cccccc"), m);
    }
    @Test
    void parseBracketStructure(){
        MoleculeWithAdjacencyList m = new MoleculeWithAdjacencyList();
        m.addAtom(N); // 0
        m.addAtom(H); // 1
        m.addAtom(H); // 2
        m.addAtom(H); // 3
        m.addAtom(H); // 4
        m.setAtomCharge(0, (byte) 1);
        m.addBond(0, 1);
        m.addBond(0, 2);
        m.addBond(0, 3);
        m.addBond(0, 4);
        Parser.compareMolecules(SmilesParser.read("[N+]([H])([H])([H])[H]"), m);
    }
    @Test
    void parseChirality(){
        MoleculeWithAdjacencyList mc = SmilesParser.read("[S@](O)(O)(=O)=O");
        MoleculeWithAdjacencyList mac = SmilesParser.read("[S@@](O)(O)(=O)=O");
        assertThrows(RuntimeException.class, ()-> Parser.compareMolecules(mc, mac));
    }
}
