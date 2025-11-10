package main;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


public class MoleculesTest {
    public static void main(String[] args) {
        Molecules m = new Molecules();
        for (int i = 0; i<1000; i++)
            m.parse("HCl");
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
    public void a() {
        Molecules m = new Molecules();
        for (int i = 0; i < 10_000_000; i++) {
            m.parse("H3PO4Cl100OOH");
        }
    }
    @Test
    public void parseOneElement() {
        Molecules m = new Molecules();
        m.parse("O");
        Assertions.assertEquals("O1", m.toString());
    }
//
    @Test
    public void parseElementWithTwoLetters() {
        Molecules m = new Molecules();
        m.parse("Cl");
        Assertions.assertEquals("Cl1", m.toString());
    }
    @Test
    public void parseMoleculeWithoutRepeatingElements() {
        Molecules m = new Molecules();
        m.parse("H2SPO4");
        Assertions.assertEquals("H2 S1 P1 O4", m.toString());
    }
    @Test
    public void parseMolecules() {
        Molecules m = new Molecules();
        m.parse("H2SPO4");
        m.parse("Cl1Br8");

        Assertions.assertEquals("H2 S1 P1 O4 Cl1 Br8", m.toString());
    }
    @Test
    public void parseSameElement2Times() {
        Molecules m = new Molecules();
        m.parse("Cl2");
        m.parse("Cl");

        Assertions.assertEquals("Cl3", m.toString());
    }
    @Test
    public void parseElementWith2DigitNumber() {
        Molecules m = new Molecules();
        m.parse("Cl12");
        Assertions.assertEquals("Cl12", m.toString());
    }
    @Test
    public void emptyMoleculeToStringReturnsEmptyString() {
        Molecules m = new Molecules();
        Assertions.assertEquals("", m.toString());
    }
    @Test
    public void parseNullAndEmptyString() {
        Molecules m = new Molecules();
        Assertions.assertThrows(IllegalArgumentException.class, () -> m.parse(""));
        Assertions.assertThrows(IllegalArgumentException.class, () -> m.parse(null));
    }
}