package main;

import main.VF2.MoleculeWithAdjacencyList;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class SmartsParser {

    public static Molecule read(String smarts) {
        MoleculeWithAdjacencyList m = new MoleculeWithAdjacencyList();
        Stack<Integer> branchStack = new Stack<>();
        Map<Integer, Integer> ringMap = new HashMap<>();
        int currentAtom = -1;
        int bondType = 0;
        for (int i = 0; i < smarts.length(); i++) {
            char c = smarts.charAt(i);
            if (c == '-') {
                bondType = 1;
                continue;
            } else if (c == '=') {
                bondType = 2;
                continue;
            } else if (c == '#') {
                bondType = 3;
                continue;
            }
            if (c == '(') {
                branchStack.push(currentAtom);
                continue;
            }
            if (c == ')') {
                currentAtom = branchStack.pop();
                continue;
            }
            if (Character.isDigit(c)) {
                int ringNum = c - '0';
                if (ringMap.containsKey(ringNum)) {
                    int otherAtom = ringMap.get(ringNum);
                    m.addBond((byte) currentAtom, (byte) otherAtom, (byte) bondType);
                    ringMap.remove(ringNum);
                } else {
                    ringMap.put(ringNum, currentAtom);
                }
                bondType = 1;
                continue;
            }
            if (Character.isLetter(c)) {
                String symbol;
                if (i + 1 < smarts.length() && Character.isLowerCase(smarts.charAt(i + 1))) {
                    symbol = "" + c + smarts.charAt(i + 1);
                    i++;
                } else {
                    symbol = "" + c;
                }
                int atomType = Parser.Utils.numberInPTable(symbol);
                int newAtom = m.addAtom((byte) atomType);
                if (currentAtom != -1) {
                    m.addBond((byte) currentAtom, (byte) newAtom, (byte) bondType);
                }
                currentAtom = newAtom;
                bondType = 1;
            }
        }
        return m;
    }
}