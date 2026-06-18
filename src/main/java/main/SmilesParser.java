package main;

import main.VF2.MoleculeWithAdjacencyList;

import java.util.*;

public class SmilesParser {
    static class State {
        int atom;
        int bond;
        State(int atom, int bond) {
            this.atom = atom;
            this.bond = bond;
        }
    }
    public static Molecule read(String smi) {
        MoleculeWithAdjacencyList mol = new MoleculeWithAdjacencyList();
        Stack<Integer> branchStack = new Stack<>();
        Map<Integer, Integer> ringAtoms = new HashMap<>();
        int currentAtom = -1;
        int bondType = 1;
        for (int i = 0; i < smi.length(); i++) {
            char c = smi.charAt(i);
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
            if (c == '(') {
                branchStack.push(currentAtom);
                continue;
            }
            if (c == ')') {
                if (!branchStack.isEmpty()) {
                    currentAtom = branchStack.pop();
                }
                continue;
            }
            if (Character.isDigit(c)) {
                int ringNum = c - '0';

                if (ringAtoms.containsKey(ringNum)) {
                    int other = ringAtoms.remove(ringNum);
                    mol.addBond((byte) currentAtom, (byte) other, (byte) bondType);
                } else {
                    ringAtoms.put(ringNum, currentAtom);
                }
                bondType = 1;
                continue;
            }
            if (Character.isLetter(c)) {
                String symbol;
                if (i + 1 < smi.length() && Character.isLowerCase(smi.charAt(i + 1))) {
                    symbol = "" + c + smi.charAt(i + 1);
                    i++;
                } else {
                    symbol = "" + c;
                }
                int atomType = Parser.Utils.numberInPTable(symbol);
                int newAtom = mol.addAtom((byte) atomType);
                if (currentAtom != -1) {
                    mol.addBond((byte) currentAtom, (byte) newAtom, (byte) bondType);
                }
                currentAtom = newAtom;
                bondType = 1;
            }
        }
        return mol;
    }
}