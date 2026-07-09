package main;

import main.VF2.MoleculeWithAdjacencyList;

import java.util.Stack;

public class SmilesParser {
    public static MoleculeWithAdjacencyList read(String s) {
        byte currBondType = 1;
        int atomCount = 0;
        int currentAtom = -1;
        Stack<Integer> branches = new Stack<>();
        MoleculeWithAdjacencyList molecule = new MoleculeWithAdjacencyList();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '-') {
                currBondType = 1;
                continue;
            }
            if (c == '=') {
                currBondType = 2;
                continue;
            }
            if (c == '#') {
                currBondType = 3;
                continue;
            }
            if (c == '(') {
                branches.push(currentAtom);
                continue;
            }
            if (c == ')') {
                currentAtom = branches.pop();
                continue;
            }
            if (c >= 'A' && c <= 'Z') {
                String atom;
                if (i + 1 < s.length() && s.charAt(i + 1) >= 'a' && s.charAt(i + 1) <= 'z') {
                    atom = s.substring(i, i + 2);
                    i++;
                } else {
                    atom = String.valueOf(c);
                }
                molecule.addAtom((byte) Parser.Utils.numberInPTable(atom));
                if (currentAtom != -1) {
                    molecule.addBond(currentAtom, atomCount, currBondType);
                }
                currentAtom = atomCount;
                atomCount++;
                currBondType = 1;
            }
        }
        return molecule;
    }
}