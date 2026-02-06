package main;

import java.util.ArrayList;
import java.util.Arrays;

public class MoleculeWithAdjacencyList implements Molecule {
    Bytes atoms = new Bytes();
    ArrayList<ArrayList<Integer>> bonds = new ArrayList<>();
    byte[][] types = new byte[50][50];

    public void addAtom(byte a) {
        atoms.add(a);
        bonds.add(new ArrayList<>());
    }

    public void addBond(int i, int j) {
        addBond(i, j, BondType.SINGLE);
    }

    public void addBond(int i, int j, byte t) {
        bonds.get(i).add(j);
        bonds.get(j).add(i);
        types[i][j] = t;
        types[j][i] = t;
    }

    public int size() {
        return atoms.size();
    }

    public byte getAtom(int i) {
        return atoms.get(i);
    }

    public ArrayList<Integer> getIndexes(byte e) {
        ArrayList<Integer> r = new ArrayList<>();
        for (int i = 0; i < atoms.size(); i++)
            if (atoms.get(i) == e) r.add(i);
        return r;
    }

    public boolean isConnected(int i, int j) {
        return bonds.get(i).contains(j);
    }

    public byte getBondType(int i, int j) {
        return types[i][j];
    }

    // check if molecule pattern is a subgraph of molecule target (Ullman algorithm)
    public static boolean isSubgraph(
            MoleculeWithAdjacencyList pattern,
            MoleculeWithAdjacencyList target
    ) {
        // patternIndex -> targetIndex mapping
        int[] mapping = new int[pattern.size()];
        // marks which target vertices are already used
        boolean[] targetUsed = new boolean[target.size()];

        Arrays.fill(mapping, -1);

        // try to match pattern[0] with every compatible target vertex
        for (int targetStart = 0; targetStart < target.size(); targetStart++) {
            if (target.getAtom(targetStart) != pattern.getAtom(0)) continue;

            Arrays.fill(mapping, -1);
            Arrays.fill(targetUsed, false);

            if (dfsMatch(0, targetStart, pattern, target, mapping, targetUsed)) {
                return true;
            }
        }
        return false;
    }

    private static boolean dfsMatch(
            int patternVertex,
            int targetVertex,
            MoleculeWithAdjacencyList pattern,
            MoleculeWithAdjacencyList target,
            int[] mapping,
            boolean[] targetUsed
    ) {
        // assign mapping
        mapping[patternVertex] = targetVertex;
        targetUsed[targetVertex] = true;

        // check all neighbors of patternVertex
        for (int patternNeighbor : pattern.bonds.get(patternVertex)) {

            int mappedNeighbor = mapping[patternNeighbor];

            // neighbor already matched - check the edge exists and matches

            if (mappedNeighbor != -1) {
                // must exist edge in target
                if (!target.isConnected(targetVertex, mappedNeighbor))
                    return false;

                // bond types must match
                if (pattern.getBondType(patternVertex, patternNeighbor)
                        != target.getBondType(targetVertex, mappedNeighbor))
                    return false;
            }
            // neighbor not mapped - try candidates
            else {
                boolean matched = false;

                for (int candidate : target.bonds.get(targetVertex)) {
                    if (targetUsed[candidate]) continue;

                    // atom labels check
                    if (target.getAtom(candidate) != pattern.getAtom(patternNeighbor))
                        continue;

                    // bond type check
                    if (pattern.getBondType(patternVertex, patternNeighbor)
                            != target.getBondType(targetVertex, candidate))
                        continue;

                    // recursion
                    if (dfsMatch(patternNeighbor, candidate,
                            pattern, target, mapping, targetUsed)) {
                        matched = true;
                        break;
                    }
                }

                if (!matched) return false;
            }
        }
        return true;
    }


}
