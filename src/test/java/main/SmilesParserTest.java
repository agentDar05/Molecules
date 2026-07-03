package main;

import main.VF2.MoleculeWithAdjacencyList;
import org.junit.jupiter.api.Test;

public class SmilesParserTest {
    @Test
    void parseAtoms() {
        SmilesParser.read("CCC");
        MoleculeWithAdjacencyList m = new MoleculeWithAdjacencyList();
        m.addAtom((byte)6);
        m.addAtom((byte)6);
        m.addAtom((byte)6);
    }
    @Test
    void parseTwoLetterAtoms() {
        SmilesParser.read("ClBrCl");
        MoleculeWithAdjacencyList m = new MoleculeWithAdjacencyList();
        m.addAtom((byte)17);
        m.addAtom((byte)35);
        m.addAtom((byte)17);
    }
    @Test
    void parseSingleBonds() {
        SmilesParser.read("C-Cl-Br-C");
        MoleculeWithAdjacencyList m = new MoleculeWithAdjacencyList();
        m.addAtom((byte)6);
        m.addAtom((byte)17);
        m.addAtom((byte)35);
        m.addAtom((byte)6);
    }
    @Test
    void parseDoubleBonds() {
        SmilesParser.read("C=Cl=Br=C");
        MoleculeWithAdjacencyList m = new MoleculeWithAdjacencyList();
        m.addAtom((byte)6);
        m.addAtom((byte)17);
        m.addAtom((byte)35);
        m.addAtom((byte)6);
        m.addBond(0, 1, BondType.DOUBLE);
        m.addBond(0, 1, BondType.DOUBLE);
        m.addBond(0, 1, BondType.DOUBLE);


    }
    @Test
    void parseTripleBonds() {
        SmilesParser.read("C#Cl#Br#C");
    }
    @Test
    void parseDifferentBonds() {
        SmilesParser.read("C-Cl=Br#C");
    }
}
