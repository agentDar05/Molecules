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

    public static boolean isSubgraph(MoleculeWithAdjacencyList query, MoleculeWithAdjacencyList target) {

        int queryAtomCount = query.size();
        int targetAtomCount = target.size();

        int[] queryToTarget = new int[queryAtomCount];
        int[] targetToQuery = new int[targetAtomCount];
        int[] nextCandidate = new int[queryAtomCount];
        Arrays.fill(queryToTarget, -1);
        Arrays.fill(targetToQuery, -1);
        Arrays.fill(nextCandidate, 0);
        int queryDepth = 0;
        while (queryDepth >= 0) {
            if (queryDepth == queryAtomCount) return true;
            boolean found = false;
            for (int targetCandidate = nextCandidate[queryDepth]; targetCandidate < targetAtomCount; targetCandidate++) {
                nextCandidate[queryDepth] = targetCandidate + 1;
                if (targetToQuery[targetCandidate] != -1) continue;
                if (query.getAtom(queryDepth) != target.getAtom(targetCandidate)) continue;
                if (!isFeasible(query, target, queryToTarget, queryDepth, targetCandidate)) continue;

                queryToTarget[queryDepth] = targetCandidate;
                targetToQuery[targetCandidate] = queryDepth;

                queryDepth++;
                if (queryDepth < queryAtomCount) nextCandidate[queryDepth] = 0;

                found = true;
                break;
            }

            if (!found) {
                queryDepth--;
                if (queryDepth >= 0) {
                    int mapped = queryToTarget[queryDepth];
                    targetToQuery[mapped] = -1;
                    queryToTarget[queryDepth] = -1;
                }
            }
        }

        return false;
    }

    private static boolean isFeasible(MoleculeWithAdjacencyList query, MoleculeWithAdjacencyList target, int[] queryToTarget, int queryAtom, int targetAtom) {
        for (int otherQueryAtom = 0; otherQueryAtom < query.size(); otherQueryAtom++) {
            if (queryToTarget[otherQueryAtom] == -1)
                continue;
            boolean bondInQuery = query.isConnected(queryAtom, otherQueryAtom);
            boolean bondInTarget = target.isConnected(targetAtom, queryToTarget[otherQueryAtom]);
            if (bondInQuery != bondInTarget)
                return false;
            if (bondInQuery) {
                if (query.getBondType(queryAtom, otherQueryAtom) != target.getBondType(targetAtom, queryToTarget[otherQueryAtom]))
                    return false;
            }
        }
        return true;
    }
}