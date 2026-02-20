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

    public static boolean isSubgraph(MoleculeWithMatrix p,
                                        MoleculeWithMatrix t) {
        int pSize = p.size(), tSize = t.size();
        int[] mapping = new int[pSize];
        int[] reverse = new int[tSize];
        int[] next = new int[pSize];
        Arrays.fill(mapping, -1);
        Arrays.fill(reverse, -1);
        Arrays.fill(next, 0);
        int depth = 0;
        while (depth >= 0) {
            if (depth == pSize) return true;
            boolean found = false;
            for (int cand = next[depth]; cand < tSize; cand++) {
                next[depth] = cand + 1;
                if (reverse[cand] != -1) continue;
                if (p.getAtom(depth) != t.getAtom(cand)) continue;
                if (!feasibleMatrix(p, t, mapping, depth, cand)) continue;
                mapping[depth] = cand;
                reverse[cand] = depth;
                depth++;
                if (depth < pSize) next[depth] = 0;

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
    private static boolean feasibleMatrix(MoleculeWithMatrix p,
                                          MoleculeWithMatrix t,
                                          int[] mapping,
                                          int uP, int uT) {

        for (int vP = 0; vP < p.size(); vP++) {
            if (mapping[vP] == -1) continue;

            boolean eP = p.isConnected(uP, vP);
            boolean eT = t.isConnected(uT, mapping[vP]);

            if (eP != eT) return false;
            if (eP &&
                    p.getBondType(uP, vP) !=
                            t.getBondType(uT, mapping[vP]))
                return false;
        }
        return true;
    }
}
