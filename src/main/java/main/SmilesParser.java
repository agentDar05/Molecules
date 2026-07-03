package main;

import main.VF2.MoleculeWithAdjacencyList;

public class SmilesParser {
    public static String read(String s) {
        int start = 0;
        int end = 0;
        byte currBondType = 1;
        int atomCount = 0;
        MoleculeWithAdjacencyList molecule = new MoleculeWithAdjacencyList();
        for (int i = 0; i < s.length(); i++, end++) {
            char c = s.charAt(i);
            if (c == '-') {
                end--;
                currBondType = 1;
                continue;
            }
            if (c == '=') {
                end--;
                currBondType = 2;
                continue;
            }
            if (c == '#') {
                end--;
                currBondType = 3;
                continue;
            }
            if (c >= 'A' && c <= 'Z') {
                String atom = s.substring(start, end);
                if (!atom.isEmpty()) {
                    molecule.addAtom((byte) Parser.Utils.numberInPTable(atom));
                    atomCount++;
                    if (atomCount > 1) {
                        molecule.addBond(atomCount - 2, atomCount - 1, currBondType);
                    }
                    currBondType = 1;
                }
                start = i;
            }
        }
        String atom = s.substring(start);
        molecule.addAtom((byte) Parser.Utils.numberInPTable(atom));
        atomCount++;
        if (atomCount > 1) {
            molecule.addBond(atomCount - 2, atomCount - 1, currBondType);
        }
        return molecule.getAtoms().toString();
    }
}