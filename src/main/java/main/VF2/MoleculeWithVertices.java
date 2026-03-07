package main.VF2;

import main.BondType;
import main.Bytes;
import main.Ints;
import main.Molecule;

import java.util.ArrayList;
import java.util.Arrays;

public class MoleculeWithVertices implements Molecule {
    Bytes vertices = new Bytes();
    Ints bonds = new Ints();
    Bytes types = new Bytes();

    public void addAtom(byte atom) {
        vertices.add(atom);
    }

    public void addBond(int i, int j) {
        addBond(i, j, BondType.SINGLE);
    }

    public void addBond(int i, int j, byte type) {
        bonds.add(i);
        bonds.add(j);
        types.add(type);
    }

    public int size() {
        return vertices.size();
    }

    public byte getAtom(int i) {
        return vertices.get(i);
    }

    public ArrayList<Integer> getIndexes(byte atom) {
        ArrayList<Integer> result = new ArrayList<>();
        for (int i = 0; i < vertices.size(); i++)
            if (vertices.get(i) == atom) result.add(i);
        return result;
    }

    public boolean isConnected(int a, int b) {
        for (int i = 0; i < bonds.size(); i += 2)
            if ((bonds.get(i) == a && bonds.get(i + 1) == b) || (bonds.get(i) == b && bonds.get(i + 1) == a))
                return true;
        return false;
    }

    public byte getBondType(int a, int b) {
        for (int i = 0; i < bonds.size(); i += 2)
            if ((bonds.get(i) == a && bonds.get(i + 1) == b) || (bonds.get(i) == b && bonds.get(i + 1) == a))
                return types.get(i / 2);
        return 0;
    }

    public static boolean isSubgraph(MoleculeWithVertices query, MoleculeWithVertices target) {
        int querySize = query.size(), targetSize = target.size();
        int[] mapping = new int[querySize];
        int[] reverse = new int[targetSize];
        int[] nextCandidate = new int[querySize];
        Arrays.fill(mapping, -1);
        Arrays.fill(reverse, -1);
        Arrays.fill(nextCandidate, 0);
        int depth = 0;

        while (depth >= 0) {
            if (depth == querySize) return true;
            boolean found = false;
            for (int candidate = nextCandidate[depth]; candidate < targetSize; candidate++) {
                nextCandidate[depth] = candidate + 1;
                if (reverse[candidate] != -1) continue;
                if (query.getAtom(depth) != target.getAtom(candidate)) continue;
                if (!feasibleVertices(query, target, mapping, depth, candidate)) continue;
                mapping[depth] = candidate;
                reverse[candidate] = depth;
                depth++;
                if (depth < querySize) nextCandidate[depth] = 0;
                found = true;
                break;
            }
            if (!found) {
                depth--;
                if (depth >= 0) {
                    reverse[mapping[depth]] = -1;
                    mapping[depth] = -1;
                }
            }
        }
        return false;
    }

    private static boolean feasibleVertices(MoleculeWithVertices query, MoleculeWithVertices target, int[] mapping, int queryVertex, int targetVertex) {
        for (int i = 0; i < query.bonds.size(); i += 2) {
            int a = query.bonds.get(i);
            int b = query.bonds.get(i + 1);
            if (a != queryVertex && b != queryVertex) continue;
            int other = (a == queryVertex) ? b : a;
            if (mapping[other] == -1) continue;
            if (!target.isConnected(targetVertex, mapping[other])) return false;
            if (query.types.get(i / 2) != target.getBondType(targetVertex, mapping[other])) return false;
        }
        return true;
    }
}