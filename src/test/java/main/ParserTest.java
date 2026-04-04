package main;

import main.VF2.MoleculeWithMatrix;
import main.VF2.MoleculeWithVertices;
import main.VF2.MoleculeWithAdjacencyList;
import org.junit.jupiter.api.Test;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import static main.Parser.isSubgraph;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ParserTest {
    public static void main(String[] args) throws IOException {
        MoleculeWithAdjacencyList m = new MoleculeWithAdjacencyList();
        OutputStream os = new FileOutputStream("output.mol");

        m.addAtom((byte) 5);
        m.addAtom((byte) 5);
        m.addAtom((byte) 5);
        m.addAtom((byte) 6);
        m.addAtom((byte) 0);
        m.addAtom((byte) 0);
        m.addAtom((byte) 0);
        m.addAtom((byte) 0);
        m.addAtom((byte) 7);
        m.addAtom((byte) 0);
        m.addAtom((byte) 0);
        m.addAtom((byte) 7);
        m.addAtom((byte) 0);

        m.addBond(0, 1);
        m.addBond(1, 2);
        m.addBond(2, 3);
        m.addBond(3, 4);
        m.addBond(4, 5);
        m.addBond(5, 6);
        m.addBond(6, 7);
        m.addBond(7, 8);
        m.addBond(8, 9);
        m.addBond(9, 10);
        m.addBond(10, 11);
        m.addBond(11, 12);
        Parser.MolV3000Writer(m, os);
        java.nio.file.Files.lines(java.nio.file.Path.of("output.mol"))
                .forEach(System.out::println);
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
//    @Test
//    void alcoholMatrix_positive() {
//        MoleculeWithMatrix m = new MoleculeWithMatrix();
//        m.addAtom((byte)1); // 0
//        m.addAtom((byte)8); // 1
//        m.addAtom((byte)6); // 2
//        m.addBond(1, 0);
//        m.addBond(2, 1);
//
//        assertTrue(Parser.isAlcoholMatrix(m));
//    }
//
//    @Test
//    void alcoholMatrix_noBond() {
//        MoleculeWithMatrix m = new MoleculeWithMatrix();
//        m.addAtom((byte)1); // 0
//        m.addAtom((byte)8); // 1
//        m.addAtom((byte)6); // 2
//        assertFalse(Parser.isAlcoholMatrix(m));
//
//
//    }
//
//    @Test
//    void alcoholMatrix_differentBond() {
//        MoleculeWithMatrix m = new MoleculeWithMatrix();
//        m.addAtom((byte)1); // 0
//        m.addAtom((byte)8); // 1
//        m.addAtom((byte)6); // 2
//        m.addBond(1, 0);
//        m.addBond(0, 2);
//        assertFalse(Parser.isAlcoholMatrix(m));
//    }
//
//    @Test
//    void alcoholVertices_positive() {
//        MoleculeWithVertices m = new MoleculeWithVertices();
//        m.addAtom((byte)1); // 0
//        m.addAtom((byte)8); // 1
//        m.addAtom((byte)6); // 2
//        m.addBond(1, 0);
//        m.addBond(2, 1);
//
//        assertTrue(Parser.isAlcoholVertices(m));
//    }
//
//    @Test
//    void alcoholVertices_noBond() {
//        MoleculeWithVertices m = new MoleculeWithVertices();
//        m.addAtom((byte)1); // 0
//        m.addAtom((byte)8); // 1
//        m.addAtom((byte)6); // 2
//        assertFalse(Parser.isAlcoholVertices(m));
//
//
//    }
//
//    @Test
//    void alcoholVertices_differentBond() {
//        MoleculeWithVertices m = new MoleculeWithVertices();
//        m.addAtom((byte)1); // 0
//        m.addAtom((byte)8); // 1
//        m.addAtom((byte)6); // 2
//        m.addBond(1, 0);
//        m.addBond(0, 2);
//        assertFalse(Parser.isAlcoholVertices(m));
//    }


    @Test
    void carboxyAdjacency_positive() {
        MoleculeWithAdjacencyList m = new MoleculeWithAdjacencyList();
        m.addAtom((byte) 6);
        m.addAtom((byte) 8);
        m.addAtom((byte) 8);
        m.addAtom((byte) 1);
        m.addBond(0, 1, BondType.DOUBLE);
        m.addBond(0, 2, BondType.SINGLE);
        m.addBond(2, 3, BondType.SINGLE);
        assertTrue(Parser.isCarboxylicAcidAdjacency(m));
    }

    @Test
    void carboxyAdjacency_noBond() {
        MoleculeWithAdjacencyList m = new MoleculeWithAdjacencyList();
        m.addAtom((byte) 6);
        m.addAtom((byte) 8);
        m.addAtom((byte) 8);
        m.addAtom((byte) 1);
        assertFalse(Parser.isCarboxylicAcidAdjacency(m));
    }

    @Test
    void carboxyAdjacency_incompleteBond() {
        MoleculeWithAdjacencyList m = new MoleculeWithAdjacencyList();
        m.addAtom((byte) 6);
        m.addAtom((byte) 8);
        m.addAtom((byte) 8);
        m.addAtom((byte) 1);
        m.addBond(0, 1, BondType.SINGLE);
        m.addBond(0, 2, BondType.SINGLE);
        m.addBond(2, 3, BondType.SINGLE);
        assertFalse(Parser.isCarboxylicAcidAdjacency(m));
    }

    //
//    @Test
//    void carboxyMatrix_positive() {
//        MoleculeWithMatrix m = new MoleculeWithMatrix();
//        m.addAtom((byte)6);
//        m.addAtom((byte)8);
//        m.addAtom((byte)8);
//        m.addAtom((byte)1);
//        m.addBond(0, 1, BondType.DOUBLE);
//        m.addBond(0, 2, BondType.SINGLE);
//        m.addBond(2, 3, BondType.SINGLE);
//        assertTrue(Parser.isCarboxylicAcidMatrix(m));
//    }
//
//    @Test
//    void carboxyMatrix_noBond() {
//        MoleculeWithMatrix m = new MoleculeWithMatrix();
//        m.addAtom((byte)6);
//        m.addAtom((byte)8);
//        m.addAtom((byte)8);
//        m.addAtom((byte)1);
//        assertFalse(Parser.isCarboxylicAcidMatrix(m));
//    }
//
//    @Test
//    void carboxyMatrix_incompleteBond() {
//        MoleculeWithMatrix m = new MoleculeWithMatrix();
//        m.addAtom((byte)6);
//        m.addAtom((byte)8);
//        m.addAtom((byte)8);
//        m.addAtom((byte)1);
//        m.addBond(0, 1);
//        m.addBond(0, 2);
//        assertFalse(Parser.isCarboxylicAcidMatrix(m));
//    }
//
//    @Test
//    void carboxyVertices_positive() {
//        MoleculeWithVertices m = new MoleculeWithVertices();
//        m.addAtom((byte)6);
//        m.addAtom((byte)8);
//        m.addAtom((byte)8);
//        m.addAtom((byte)1);
//        m.addBond(0, 1, BondType.DOUBLE);
//        m.addBond(0, 2, BondType.SINGLE);
//        m.addBond(2, 3, BondType.SINGLE);
//        assertTrue(Parser.isCarboxylicAcidVertices(m));
//    }
//
//    @Test
//    void carboxyVertices_noBond() {
//        MoleculeWithVertices m = new MoleculeWithVertices();
//        m.addAtom((byte)6);
//        m.addAtom((byte)8);
//        m.addAtom((byte)8);
//        m.addAtom((byte)1);
//        assertFalse(Parser.isCarboxylicAcidVertices(m));
//    }
//
//    @Test
//    void carboxyVertices_incompleteBond() {
//        MoleculeWithVertices m = new MoleculeWithVertices();
//        m.addAtom((byte)6);
//        m.addAtom((byte)8);
//        m.addAtom((byte)8);
//        m.addAtom((byte)1);
//        m.addBond(0, 1, BondType.SINGLE);
//        m.addBond(0, 2, BondType.SINGLE);
//        m.addBond(2, 3, BondType.SINGLE);
//        assertFalse(Parser.isCarboxylicAcidVertices(m));
//    }
    @Test
    public void isAdjacencySubgraphTrue() {
        main.VF2.MoleculeWithAdjacencyList m = new main.VF2.MoleculeWithAdjacencyList();
        m.addAtom((byte) 6); // c // 0
        m.addAtom((byte) 1);//h1 // 1
        m.addAtom((byte) 1);//h2 // 2
        m.addAtom((byte) 1);//h3 // 3
        m.addAtom((byte) 1);//h4 // 4
        m.addAtom((byte) 7); //n1 // 5
        m.addAtom((byte) 7); //n2 // 6
        m.addAtom((byte) 8); // o // 7
        m.addBond(0, 5); // cn1
        m.addBond(0, 6); // cn2
        m.addBond(0, 7); // co
        m.addBond(5, 3); // nh1
        m.addBond(5, 4); // nh2
        m.addBond(6, 1); // n2h3
        m.addBond(6, 2); // n2h4
        main.VF2.MoleculeWithAdjacencyList n = new main.VF2.MoleculeWithAdjacencyList();
        n.addAtom((byte) 1);//h1 // 0
        n.addAtom((byte) 1);//h2 // 1
        n.addAtom((byte) 7); //n1 // 2
        n.addBond(2, 0); // nh1
        n.addBond(2, 1); // nh2
        System.out.println(isSubgraph(n, m));
    }
//    @Test
//    public void isMatrixSubgraphTrue(){
//        main.VF2.MoleculeWithMatrix m = new main.VF2.MoleculeWithMatrix();
//        m.addAtom((byte)6); // c // 0
//        m.addAtom((byte)1);//h1 // 1
//        m.addAtom((byte)1);//h2 // 2
//        m.addAtom((byte)1);//h3 // 3
//        m.addAtom((byte)1);//h4 // 4
//        m.addAtom((byte)7); //n1 // 5
//        m.addAtom((byte)7); //n2 // 6
//        m.addAtom((byte)8); // o // 7
//        m.addBond(0, 5); // cn1
//        m.addBond(0, 6); // cn2
//        m.addBond(0, 7); // co
//        m.addBond(5, 3); // nh1
//        m.addBond(5, 4); // nh2
//        m.addBond(6, 1); // n2h3
//        m.addBond(6, 2); // n2h4
//        main.VF2.MoleculeWithMatrix n = new main.VF2.MoleculeWithMatrix();
//        n.addAtom((byte)1);//h1 // 0
//        n.addAtom((byte)1);//h2 // 1
//        n.addAtom((byte)7); //n1 // 2
//        n.addBond(2, 0); // nh1
//        n.addBond(2, 1); // nh2
//        System.out.println(isSubgraph(n, m));
//    }
//    @Test
//    public void isVerticesSubgraphTrue(){
//        main.VF2.MoleculeWithVertices m = new main.VF2.MoleculeWithVertices();
//        m.addAtom((byte)6); // c // 0
//        m.addAtom((byte)1);//h1 // 1
//        m.addAtom((byte)1);//h2 // 2
//        m.addAtom((byte)1);//h3 // 3
//        m.addAtom((byte)1);//h4 // 4
//        m.addAtom((byte)7); //n1 // 5
//        m.addAtom((byte)7); //n2 // 6
//        m.addAtom((byte)8); // o // 7
//        m.addBond(0, 5); // cn1
//        m.addBond(0, 6); // cn2
//        m.addBond(0, 7); // co
//        m.addBond(5, 3); // nh1
//        m.addBond(5, 4); // nh2
//        m.addBond(6, 1); // n2h3
//        m.addBond(6, 2); // n2h4
//        main.VF2.MoleculeWithVertices n = new main.VF2.MoleculeWithVertices();
//        n.addAtom((byte)1);//h1 // 0
//        n.addAtom((byte)1);//h2 // 1
//        n.addAtom((byte)7); //n1 // 2
//        n.addBond(2, 0); // nh1
//        n.addBond(2, 1); // nh2
//        System.out.println(isSubgraph(n, m));
//    }
//}
}