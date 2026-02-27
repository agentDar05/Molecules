package main.VF2;

import main.BondType;
import main.Bytes;
import main.Molecule;

import java.util.ArrayList;
import java.util.Arrays;

public class MoleculeWithAdjacencyList implements Molecule {

    Bytes atoms = new Bytes();
    ArrayList<ArrayList<Integer>> bonds = new ArrayList<>();
    byte[][] bondTypes = new byte[50][50];

    public void addAtom(byte atomType) {
        atoms.add(atomType);
        bonds.add(new ArrayList<>());
    }

    public void addBond(int i, int j) {
        addBond(i, j, BondType.SINGLE);
    }

    public void addBond(int i, int j, byte type) {
        bonds.get(i).add(j);
        bonds.get(j).add(i);
        bondTypes[i][j] = type;
        bondTypes[j][i] = type;
    }

    public int size() {
        return atoms.size();
    }

    public byte getAtom(int index) {
        return atoms.get(index);
    }

    public ArrayList<Integer> getIndexes(byte atomType) {
        ArrayList<Integer> result = new ArrayList<>();
        for (int i = 0; i < atoms.size(); i++) {
            if (atoms.get(i) == atomType) {
                result.add(i);
            }
        }
        return result;
    }

    public boolean isConnected(int i, int j) {
        return bonds.get(i).contains(j);
    }

    public byte getBondType(int i, int j) {
        return bondTypes[i][j];
    }

    // mapping[i] = target vertex matched to pattern vertex i
    // reverse[j] = pattern vertex matched to target vertex j
    public static boolean isSubgraph(MoleculeWithAdjacencyList pattern,
                                     MoleculeWithAdjacencyList target) {

        int patternSize = pattern.size();
        int targetSize = target.size();

        int[] mapping = new int[patternSize];
        int[] reverse = new int[targetSize];
        int[] next = new int[patternSize];

        Arrays.fill(mapping, -1);
        Arrays.fill(reverse, -1);
        Arrays.fill(next, 0);

        int depth = 0;

        while (depth >= 0) {

            if (depth == patternSize)
                return true;

            boolean found = false;

            for (int candidate = next[depth];
                 candidate < targetSize;
                 candidate++) {

                next[depth] = candidate + 1;

                if (reverse[candidate] != -1)
                    continue;

                if (pattern.getAtom(depth) != target.getAtom(candidate))
                    continue;

                if (!isFeasible(pattern, target,
                        mapping, depth, candidate))
                    continue;

                mapping[depth] = candidate;
                reverse[candidate] = depth;

                depth++;
                if (depth < patternSize)
                    next[depth] = 0;

                found = true;
                break;
            }

            if (!found) {
                depth--;
                if (depth >= 0) {
                    int mapped = mapping[depth];
                    reverse[mapped] = -1;
                    mapping[depth] = -1;
                }
            }
        }

        return false;
    }

    private static boolean isFeasible(
            MoleculeWithAdjacencyList pattern,
            MoleculeWithAdjacencyList target,
            int[] mapping,
            int uPattern,
            int uTarget) {

        for (int vPattern = 0; vPattern < pattern.size(); vPattern++) {

            if (mapping[vPattern] == -1)
                continue;

            boolean edgePattern =
                    pattern.isConnected(uPattern, vPattern);

            boolean edgeTarget =
                    target.isConnected(uTarget,
                            mapping[vPattern]);

            if (edgePattern != edgeTarget)
                return false;

            if (edgePattern) {
                if (pattern.getBondType(uPattern, vPattern) !=
                        target.getBondType(uTarget,
                                mapping[vPattern]))
                    return false;
            }
        }

        return true;
    }
}