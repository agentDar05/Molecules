package main;

import main.VF2.MoleculeWithAdjacencyList;

import java.util.Stack;
import java.util.HashMap;

public class SmilesParser {
    public static MoleculeWithAdjacencyList read(String s) {
        MoleculeWithAdjacencyList molecule = new MoleculeWithAdjacencyList();
        Stack<Integer> branches = new Stack<>();
        HashMap<Character, Integer> rings = new HashMap<>();
        int atomCount = 0;
        int currentAtom = -1;
        byte bondType = 1;
        boolean previousAromatic = false;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '-') {
                bondType = 1;
                continue;
            }
            if (c == '=') {
                bondType = 2;
                continue;
            }
            if (c == '#') {
                bondType = 3;
                continue;
            }
            if (c == ':') {
                bondType = 4;
                continue;
            }
            if (c == '~') {
                bondType = 7;
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
            if (c == '[') {
                int end = s.indexOf(']', i);
                if (end == -1) {
                    throw new IllegalArgumentException("Can't parse this SMILES file. Missing ]. Currently: " + s);
                }
                String inside = s.substring(i + 1, end);
                boolean[] aromatic = new boolean[1];
                byte newBond = bondType;
                if (previousAromatic && aromatic[0]) {
                    newBond = 4;
                }
                currentAtom = addBracketAtom(
                        inside,
                        molecule,
                        currentAtom,
                        atomCount,
                        newBond,
                        aromatic
                );
                atomCount++;
                previousAromatic = aromatic[0];
                i = end;
                bondType = 1;
                continue;
            }
            if (Character.isDigit(c)) {
                if (rings.containsKey(c)) {
                    molecule.addBond(
                            currentAtom,
                            rings.get(c),
                            bondType
                    );
                    rings.remove(c);
                } else {
                    rings.put(c, currentAtom);
                }
                bondType = 1;
                continue;
            }
            if (Character.isLetter(c)) {
                boolean aromatic = Character.isLowerCase(c);
                String atom;
                if (aromatic) {
                    atom = String.valueOf(
                            Character.toUpperCase(c)
                    );
                } else {
                    if (i + 1 < s.length()
                            && Character.isLowerCase(s.charAt(i + 1))) {
                        atom = s.substring(i, i + 2);
                        i++;
                    } else {
                        atom = String.valueOf(c);
                    }
                }
                byte newBond = bondType;
                if (previousAromatic && aromatic) {
                    newBond = 4;
                }
                currentAtom = addAtom(
                        molecule,
                        atom,
                        currentAtom,
                        atomCount,
                        newBond,
                        (byte) 0
                );
                atomCount++;
                previousAromatic = aromatic;
                bondType = 1;
            }
        }
        return molecule;
    }
    private static int addAtom(
            MoleculeWithAdjacencyList molecule,
            String atom,
            int currentAtom,
            int atomCount,
            byte bondType,
            byte charge
    ) {
        molecule.addAtom(
                (byte) Parser.Utils.numberInPTable(atom),
                charge
        );
        if (currentAtom != -1) {
            molecule.addBond(
                    currentAtom,
                    atomCount,
                    bondType
            );
        }
        return atomCount;
    }

    private static int addBracketAtom(
            String s,
            MoleculeWithAdjacencyList molecule,
            int currentAtom,
            int atomCount,
            byte bondType,
            boolean[] aromatic
    ) {
        String atom = "";
        byte charge = 0;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (Character.isLetter(c)) {
                aromatic[0] = Character.isLowerCase(c);
                if (aromatic[0]) {
                    atom = String.valueOf(
                            Character.toUpperCase(c)
                    );
                } else {
                    atom = String.valueOf(c);
                    if (i + 1 < s.length()
                            && Character.isLowerCase(s.charAt(i + 1))) {
                        atom += s.charAt(i + 1);
                    }
                }
            }
            if (c == '+') {
                charge++;
            }
            if (c == '-') {
                charge--;
            }
        }
        return addAtom(
                molecule,
                atom,
                currentAtom,
                atomCount,
                bondType,
                charge
        );
    }
}