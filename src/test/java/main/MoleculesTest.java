package main;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


public class MoleculesTest {
    public static void main(String[] args) {
        Molecules m = new Molecules();
        for (int i = 0; i < 10_000_000; i++) {
            m.parse("H3PO4Cl100OOH");
        }
    }
    @Test
    public void parseElementWithNegativeNumber() {
        Molecules m = new Molecules();
        m.parse("H-1");
        System.out.println(m);
    }
    @Test
    public void parseElementWith0() {
        Molecules m = new Molecules();
        Assertions.assertThrows(IllegalArgumentException.class, () -> m.parse("H0"));
    }
    @Test
    public void parseOneElement() {
        Molecules m = new Molecules();
        m.parse("O");
        Assertions.assertEquals("{8=1}", m.toString());
    }
//
    @Test
    public void parseElementWithTwoLetters() {
        Molecules m = new Molecules();
        m.parse("Cl");
        Assertions.assertEquals("{17=1}", m.toString());
    }
    @Test
    public void parseMoleculeWithoutRepeatingElements() {
        Molecules m = new Molecules();
        m.parse("H2SPO4");
        Assertions.assertEquals("{16=1, 1=2, 8=4, 15=1}", m.toString());
    }
    @Test
    public void parseMolecules() {
        Molecules m = new Molecules();
        m.parse("H2SPO4");
        m.parse("Cl1Br8");

        Assertions.assertEquals("{16=1, 1=2, 17=1, 35=8, 8=4, 15=1}", m.toString());
    }
    @Test
    public void parseSameElement2Times() {
        Molecules m = new Molecules();
        m.parse("Cl2");
        m.parse("Cl");

        Assertions.assertEquals("{17=3}", m.toString());
    }
    @Test
    public void parseElementWith2DigitNumber() {
        Molecules m = new Molecules();
        m.parse("Cl12");
        Assertions.assertEquals("{17=12}", m.toString());
    }
    @Test
    public void emptyMoleculeToStringReturnsEmptyString() {
        Molecules m = new Molecules();
        Assertions.assertEquals("{}", m.toString());
    }
    @Test
    public void parseNullAndEmptyString() {
        Molecules m = new Molecules();
        Assertions.assertThrows(IllegalArgumentException.class, () -> m.parse(""));
        Assertions.assertThrows(IllegalArgumentException.class, () -> m.parse(null));
    }
    @Test
    public void findsElement() {
        Molecules m = new Molecules();
        m.parse("Cl");
        m.parse("H");
        m.parse("Zn");
        m.parse("Pt");
        Assertions.assertEquals("{17=1, 1=1, 30=1, 78=1}", m.toString());
    }
    @Test
    public void doesntStoreNotExistingElements() {
        Molecules m = new Molecules();
        m.parse("Q");
        m.parse("Hi");
        m.parse("Ok");
        m.parse("L");
        Assertions.assertEquals("{}", m.toString());
    }

}