package main.VF2;

import main.BondType;
import main.Bytes;
import main.Molecule;

import java.util.ArrayList;
import java.util.Arrays;

public class MoleculeWithMatrix implements Molecule {
    Bytes atoms = new Bytes();
    boolean[][] m = new boolean[50][50];
    byte[][] types = new byte[50][50];

    public void addAtom(byte a) { atoms.add(a); }
    public void addBond(int i, int j) { addBond(i, j, BondType.SINGLE); }
    public void addBond(int i, int j, byte t) {
        m[i][j] = m[j][i] = true;
        types[i][j] = types[j][i] = t;
    }

    public int size() { return atoms.size(); }
    public byte getAtom(int i) { return atoms.get(i); }

    public ArrayList<Integer> getIndexes(byte e) {
        ArrayList<Integer> r = new ArrayList<>();
        for (int i = 0; i < atoms.size(); i++)
            if (atoms.get(i) == e) r.add(i);
        return r;
    }

    public boolean isConnected(int i, int j) { return m[i][j]; }
    public byte getBondType(int i, int j) { return types[i][j]; }

    // mapping[i] = target vertex matched to pattern vertex i
    // reverse[j] = pattern vertex matched to target vertex j
    public static boolean isSubgraph(MoleculeWithMatrix pattern, MoleculeWithMatrix target) {

        int patternSize = pattern.size();
        int targetSize = target.size();

        int[] mapping = new int[patternSize];
        int[] reverse = new int[targetSize];
        int[] nextCandidate = new int[patternSize];

        Arrays.fill(mapping, -1);
        Arrays.fill(reverse, -1);
        Arrays.fill(nextCandidate, 0);

        int depth = 0;

        while (depth >= 0) {

            if (depth == patternSize)
                return true;

            boolean matchFound = false;

            for (int candidate = nextCandidate[depth];
                 candidate < targetSize;
                 candidate++) {

                nextCandidate[depth] = candidate + 1;

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
                    nextCandidate[depth] = 0;

                matchFound = true;
                break;
            }

            if (!matchFound) {
                depth--;
                if (depth >= 0) {
                    int mappedVertex = mapping[depth];
                    reverse[mappedVertex] = -1;
                    mapping[depth] = -1;
                }
            }
        }

        return false;
    }

    private static boolean isFeasible(MoleculeWithMatrix pattern, MoleculeWithMatrix target, int[] mapping, int patternVertex, int targetVertex) {

        for (int otherPatternVertex = 0;
             otherPatternVertex < pattern.size();
             otherPatternVertex++) {

            if (mapping[otherPatternVertex] == -1)
                continue;

            boolean edgeInPattern =
                    pattern.isConnected(patternVertex,
                            otherPatternVertex);

            boolean edgeInTarget =
                    target.isConnected(targetVertex,
                            mapping[otherPatternVertex]);

            if (edgeInPattern != edgeInTarget)
                return false;

            if (edgeInPattern) {
                if (pattern.getBondType(patternVertex,
                        otherPatternVertex) !=
                        target.getBondType(targetVertex,
                                mapping[otherPatternVertex]))
                    return false;
            }
        }

        return true;
    }
}
