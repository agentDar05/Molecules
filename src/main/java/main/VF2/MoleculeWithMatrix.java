package main.VF2;

import main.BondType;
import main.Bytes;
import main.Molecule;

import java.util.ArrayList;
import java.util.Arrays;

public class MoleculeWithMatrix implements Molecule {
    Bytes atoms = new Bytes();
    boolean[][] bonds = new boolean[50][50];
    byte[][] bondTypes = new byte[50][50];

    public void addAtom(byte atom) {
        atoms.add(atom);
    }

    public void addBond(int i, int j) {
        addBond(i, j, BondType.SINGLE);
    }

    public void addBond(int i, int j, byte type) {
        bonds[i][j] = bonds[j][i] = true;
        bondTypes[i][j] = bondTypes[j][i] = type;
    }

    public int size() {
        return atoms.size();
    }

    public byte getAtom(int i) {
        return atoms.get(i);
    }

    public ArrayList<Integer> getIndexes(byte atom) {
        ArrayList<Integer> result = new ArrayList<>();
        for (int i = 0; i < atoms.size(); i++)
            if (atoms.get(i) == atom) result.add(i);
        return result;
    }

    public boolean isConnected(int i, int j) {
        return bonds[i][j];
    }

    public byte getBondType(int i, int j) {
        return bondTypes[i][j];
    }

    public static boolean isSubgraph(MoleculeWithMatrix query, MoleculeWithMatrix target) {
        int querySize = query.size();
        int targetSize = target.size();
        int[] queryToTarget = new int[querySize];
        int[] targetToQuery = new int[targetSize];
        int[] nextCandidate = new int[querySize];
        Arrays.fill(queryToTarget, -1);
        Arrays.fill(targetToQuery, -1);
        Arrays.fill(nextCandidate, 0);
        int depth = 0;

        while (depth >= 0) {
            if (depth == querySize) return true;
            boolean found = false;
            for (int candidate = nextCandidate[depth]; candidate < targetSize; candidate++) {
                nextCandidate[depth] = candidate + 1;
                if (targetToQuery[candidate] != -1) continue;
                if (query.getAtom(depth) != target.getAtom(candidate)) continue;
                if (!isFeasible(query, target, queryToTarget, depth, candidate)) continue;
                queryToTarget[depth] = candidate;
                targetToQuery[candidate] = depth;
                depth++;
                if (depth < querySize) nextCandidate[depth] = 0;
                found = true;
                break;
            }
            if (!found) {
                depth--;
                if (depth >= 0) {
                    int mapped = queryToTarget[depth];
                    targetToQuery[mapped] = -1;
                    queryToTarget[depth] = -1;
                }
            }
        }
        return false;
    }

    private static boolean isFeasible(MoleculeWithMatrix query, MoleculeWithMatrix target, int[] queryToTarget, int queryAtom, int targetAtom) {
        for (int otherQueryAtom = 0; otherQueryAtom < query.size(); otherQueryAtom++) {
            if (queryToTarget[otherQueryAtom] == -1) continue;
            boolean bondInQuery = query.isConnected(queryAtom, otherQueryAtom);
            boolean bondInTarget = target.isConnected(targetAtom, queryToTarget[otherQueryAtom]);
            if (bondInQuery != bondInTarget) return false;
            if (bondInQuery) {
                if (query.getBondType(queryAtom, otherQueryAtom) != target.getBondType(targetAtom, queryToTarget[otherQueryAtom]))
                    return false;
            }
        }
        return true;
    }
}