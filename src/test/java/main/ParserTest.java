package main;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


public class ParserTest {
    public static void main(String[] args) {
        Parser m = new Parser();
        for (int i = 0; i < 10_000_000; i++) {
            m.parse("H3PO4Cl100OOH");
        }
    }
    @Test
    public void parseElementWithNegativeNumber() {
        Parser m = new Parser();
        m.parse("H-1");
        System.out.println(m);
    }
    @Test
    public void parseElementWith0() {
        Parser m = new Parser();
        Assertions.assertThrows(IllegalArgumentException.class, () -> m.parse("H0"));
    }
    @Test
    public void parseOneElement() {
        Parser m = new Parser();
        m.parse("O");
        Assertions.assertEquals("{8=1}", m.toString());
    }
    //
    @Test
    public void parseElementWithTwoLetters() {
        Parser m = new Parser();
        m.parse("Cl");
        Assertions.assertEquals("{17=1}", m.toString());
    }
    @Test
    public void parseMoleculeWithoutRepeatingElements() {
        Parser m = new Parser();
        m.parse("H2SPO4");
        Assertions.assertEquals("{16=1, 1=2, 8=4, 15=1}", m.toString());
    }
    @Test
    public void parseMolecules() {
        Parser m = new Parser();
        m.parse("H2SPO4");
        m.parse("Cl1Br8");

        Assertions.assertEquals("{16=1, 1=2, 17=1, 35=8, 8=4, 15=1}", m.toString());
    }
    @Test
    public void parseSameElement2Times() {
        Parser m = new Parser();
        m.parse("Cl2");
        m.parse("Cl");

        Assertions.assertEquals("{17=3}", m.toString());
    }
    @Test
    public void parseElementWith2DigitNumber() {
        Parser m = new Parser();
        m.parse("Cl12");
        Assertions.assertEquals("{17=12}", m.toString());
    }
    @Test
    public void emptyMoleculeToStringReturnsEmptyString() {
        Parser m = new Parser();
        Assertions.assertEquals("{}", m.toString());
    }
    @Test
    public void parseNullAndEmptyString() {
        Parser m = new Parser();
        Assertions.assertThrows(IllegalArgumentException.class, () -> m.parse(""));
        Assertions.assertThrows(IllegalArgumentException.class, () -> m.parse(null));
    }
    @Test
    public void findsElement() {
        Parser m = new Parser();
        m.parse("Cl");
        m.parse("H");
        m.parse("Zn");
        m.parse("Pt");
        Assertions.assertEquals("{17=1, 1=1, 30=1, 78=1}", m.toString());
    }
    @Test
    public void doesntStoreNotExistingElements() {
        Parser m = new Parser();
        m.parse("Q");
        m.parse("Hi");
        m.parse("Ok");
        m.parse("L");
        Assertions.assertEquals("{}", m.toString());
    }

}