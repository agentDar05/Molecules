package main;

import main.VF2.MoleculeWithAdjacencyList;

import java.util.*;

public class SmilesParser {
    public static String read(String s) {
        int start = 0;
        int end = 0;
        byte currBondType = 1;
        int atomCount = 0;
        MoleculeWithAdjacencyList molecule = new MoleculeWithAdjacencyList();
        for (int i = 0; i < s.length(); i++, end++) {
            if (s.charAt(i) == '-') {
                end--;
                continue;
            }
            if (s.charAt(i) == '=') {
                end--;
                currBondType = 2;
                continue;
            }
            if (s.charAt(i) == '#') {
                end--;
                currBondType = 3;
                continue;
            }

            char c = s.charAt(i);
            if (c>'A' && c<'Z') {
                String substring = s.substring(start, end);
                if (!substring.isEmpty()) {
                    molecule.addAtom((byte) Parser.Utils.numberInPTable(substring));
                    atomCount++;
                    if (currBondType>1){
                        molecule.addBond(atomCount-1, atomCount,  currBondType);
                    }
                }
                start = i;
            }
        }
        molecule.addAtom((byte) Parser.Utils.numberInPTable(s.substring(start)));
        atomCount++;
        if (currBondType > 1) {
            molecule.addBond(atomCount-1, atomCount,  currBondType);
        }
        return molecule.getAtoms().toString();
    }
}