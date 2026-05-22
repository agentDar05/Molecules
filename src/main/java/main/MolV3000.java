package main;

import main.VF2.MoleculeWithAdjacencyList;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class MolV3000 {
    /**
     * Creates MolV3000 file of given Molecule
     *
     * @param m  Molecule
     * @param os OutputStream where the MolV3000 will be stored
     */
    public static void write(Molecule m, OutputStream os) throws IOException {
        StringBuilder sb = new StringBuilder();
        sb.append("\n");
        sb.append("  -MolV3000Writer\n\n");
        sb.append("  0  0  0  0  0  0  0  0  0  0  0 V3000\n");
        sb.append("M  V30 BEGIN CTAB\n");
        int atomCount = m.size();
        int bondCount = countBonds(m);
        sb.append("M  V30 COUNTS ").append(atomCount).append(" ").append(bondCount).append(" 0 0 0\n");
        sb.append("M  V30 BEGIN ATOM\n");
        for (int i = 0; i < atomCount; i++) {
            sb.append("M  V30 ").append(i + 1).append(" ").append(Parser.Utils.SYMBOLS[m.getAtom(i)]).append(" 0.0 0.0 0.0 0\n");
        }
        sb.append("M  V30 END ATOM\n");
        sb.append("M  V30 BEGIN BOND\n");
        int bondIndex = 1;
        for (int i = 0; i < atomCount; i++) {
            for (int j : m.getBonds(i)) {
                if (i < j) {
                    sb.append("M  V30 ")
                            .append(bondIndex++)
                            .append(" ")
                            .append(m.getBondType(i, j))
                            .append(" ")
                            .append(i + 1).append(" ")
                            .append(j + 1)
                            .append("\n");
                }
            }
        }
        sb.append("M  V30 END BOND\n");
        sb.append("M  V30 END CTAB\n");
        sb.append("M  END");
        os.write(sb.toString().getBytes(java.nio.charset.StandardCharsets.UTF_8));
    }

    /**
     * Reads MolV3000 file and transforms it in Molecule, storing atoms, bonds and bond types
     *
     * @param is InputStream, file that Reader will parse
     * @return Molecule
     *
     */

    public static Molecule read(InputStream is) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
        if (new String(is.readAllBytes(), StandardCharsets.UTF_8).length() < 170){
            throw new IOException("Invalid MolV3000 file: file too short");
        }
        MoleculeWithAdjacencyList m = new MoleculeWithAdjacencyList();
        boolean wasInAtomBlock = false;
        boolean wasInBondBlock = false;
        String line;

        while ((line = reader.readLine()) != null) {
            line = line.trim();

            if (line.equals("M  V30 BEGIN ATOM")) {
                wasInAtomBlock = true;
                parseInAtomBlock(m, reader);
            }

            if (line.equals("M  V30 BEGIN BOND")) {
                wasInBondBlock = true;
                parseInBondBlock(m, reader);
            }
        }
        if (!wasInAtomBlock) {
            throw new IllegalArgumentException("Cannot read InputStream, missing atom block");
        }
        if (!wasInBondBlock) {
            throw new IllegalArgumentException("Cannot read InputStream, missing bond block");
        }
        return m;

    }

    /**
     * Counts bonds in given Molecule
     *
     * @param m Molecule
     * @return number of bonds
     *
     */
    private static int countBonds(Molecule m) {
        int count = 0;
        for (int i = 0; i < m.size(); i++) {
            for (int j : m.getBonds(i)) {
                if (i < j) count++;
            }
        }
        return count;
    }

    /**
     * Parses atom block, adds atoms into a given molecule
     *
     * @param m     Molecule
     * @param lines BufferedReader
     *
     */
    private static void parseInAtomBlock(Molecule m, BufferedReader lines) throws IOException {
        String line;
        while ((line = lines.readLine()) != null) {
            line = line.trim();
            if (line.equals("M  V30 END ATOM")) {
                return;
            }
            m.addAtom((byte) parseAtomLine(line));
        }
        throw new IllegalArgumentException("Missing 'M  V30 END ATOM' block");
    }

    /**
     * Parses bond block, adds bonds into a given molecule
     *
     * @param m     Molecule
     * @param lines BufferedReader
     *
     */
    private static void parseInBondBlock(Molecule m, BufferedReader lines) throws IOException {
        String line;
        while ((line = lines.readLine()) != null) {
            line = line.trim();
            if (line.equals("M  V30 END BOND")) {
                return;
            }
            int[] bond = parseBondLine(line);
            m.addBond(
                    (byte) (bond[0]),
                    (byte) (bond[1]),
                    (byte) bond[2]
            );
        }
        throw new IllegalArgumentException("Missing 'M  V30 END ATOM' block");
    }

    /**
     * The method divides line into separate blocks and finds atom index
     *
     * @param line String - line that will be parsed
     * @return integer that represents index of an atom in periodic table
     */
    private static int parseAtomLine(String line) {
        int space0 = line.indexOf(' ');
        int space1 = line.indexOf(' ', space0 + 1);
        int space2 = line.indexOf(' ', space1 + 1);
        int space3 = line.indexOf(' ', space2 + 1);
        int space4 = line.indexOf(' ', space3 + 1);

        if (space0 == -1 || space1 == -1 || space2 == -1 || space3 == -1 || space4 == -1) {
            throw new IllegalArgumentException("Invalid atom line, not enough tokens: " + line);
        }
        String element = line.substring(space3 + 1, space4);
        return Parser.Utils.numberInPTable(element);
    }

    /**
     * Parses a bond line and extracts three integers:
     * the indices of two atoms and the bond type.
     * The method divides line into separate blocks of numbers and returns the 2nd, 3rd,
     * and 4th blocks of the line.
     * Example:
     * Input:  "1  2  1  3"
     * Output: [1, 3, 2]
     *
     * @param line a string representing a bond line
     * @return an array of three integers:
     * [atom1Index, atom2Index, bondType]
     */
    private static int[] parseBondLine(String line) {
        int space0 = line.indexOf(' ');
        int space1 = line.indexOf(' ', space0 + 1);
        int space2 = line.indexOf(' ', space1 + 1);
        int space3 = line.indexOf(' ', space2 + 1);
        int space4 = line.indexOf(' ', space3 + 1);
        int space5 = line.indexOf(' ', space4 + 1);
        int space6 = line.length();
        if (space0 == -1 || space1 == -1 || space2 == -1 || space3 == -1 || space4 == -1 || space5 == -1) {
            throw new IllegalArgumentException("Invalid atom line, not enough tokens: " + line);
        }
        int bondType = Integer.parseInt(line.substring(space3 + 1, space4));
        int element1 = Integer.parseInt(line.substring(space4 + 1, space5)) - 1;
        int element2 = Integer.parseInt(line.substring(space5 + 1, space6)) - 1;
        return new int[]{element1, element2, bondType};
    }
}
