package main.Ullman;

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

    public static boolean isSubgraph(MoleculeWithMatrix pattern, MoleculeWithMatrix target) {
        int pSize = pattern.size();
        int tSize = target.size();

        int[] mapping = new int[pSize];
        boolean[] used = new boolean[tSize];

        for (int start = 0; start < tSize; start++) {
            if (target.getAtom(start) != pattern.getAtom(0)) continue;

            Arrays.fill(mapping, -1);
            Arrays.fill(used, false);

            int[] nextCandidate = new int[pSize];
            Arrays.fill(nextCandidate, 0);

            mapping[0] = start;
            used[start] = true;

            int depth = 1;

            while (depth >= 0) {

                if (depth == pSize) return true;

                boolean found = false;

                for (int cand = nextCandidate[depth]; cand < tSize; cand++) {
                    nextCandidate[depth] = cand + 1;

                    if (used[cand]) continue;
                    if (target.getAtom(cand) != pattern.getAtom(depth)) continue;

                    boolean ok = true;

                    for (int prev = 0; prev < depth; prev++) {
                        if (pattern.isConnected(depth, prev)) {
                            if (!target.isConnected(cand, mapping[prev]) ||
                                    pattern.getBondType(depth, prev) != target.getBondType(cand, mapping[prev])) {
                                ok = false;
                                break;
                            }
                        }
                    }

                    if (!ok) continue;

                    mapping[depth] = cand;
                    used[cand] = true;
                    depth++;
                    if (depth < pSize) nextCandidate[depth] = 0;
                    found = true;
                    break;
                }

                if (!found) {
                    depth--;
                    if (depth >= 0) {
                        used[mapping[depth]] = false;
                        mapping[depth] = -1;
                    }
                }
            }
        }

        return false;
    }
}
