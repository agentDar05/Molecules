package main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

    public int size() { return atoms.size(); }
    public byte getAtom(int i) { return atoms.get(i); }

    public ArrayList<Integer> getIndexes(byte e) {
        ArrayList<Integer> r = new ArrayList<>();
        for (int i = 0; i < atoms.size(); i++)
            if (atoms.get(i) == e) r.add(i);
        return r;
    }

    public boolean isConnected(int i, int j) { return bonds.get(i).contains(j); }
    public byte getBondType(int i, int j) { return types[i][j]; }

    public static boolean isSubgraph(MoleculeWithAdjacencyList pattern, MoleculeWithAdjacencyList target) {
        int[] mapping = new int[pattern.size()];
        boolean[] used = new boolean[target.size()];
        Arrays.fill(mapping, -1);

        for (int t = 0; t < target.size(); t++) {
            if (target.getAtom(t) != pattern.getAtom(0)) continue;
            Arrays.fill(mapping, -1);
            Arrays.fill(used, false);
            if (dfs(pattern, target, mapping, used, 0, t)) return true;
        }
        return false;
    }

    private static boolean dfs(MoleculeWithAdjacencyList p, MoleculeWithAdjacencyList t,
                               int[] mapping, boolean[] used, int uP, int uT) {
        mapping[uP] = uT;
        used[uT] = true;

        for (int vP : p.bonds.get(uP)) {
            int mapped = mapping[vP];
            if (mapped != -1) {
                if (!t.isConnected(uT, mapped) || p.getBondType(uP, vP) != t.getBondType(uT, mapped))
                    return false;
            } else {
                boolean ok = false;
                for (int cand : t.bonds.get(uT)) {
                    if (used[cand]) continue;
                    if (t.getAtom(cand) != p.getAtom(vP)) continue;
                    if (p.getBondType(uP, vP) != t.getBondType(uT, cand)) continue;
                    if (dfs(p, t, mapping, used, vP, cand)) {
                        ok = true;
                        break;
                    }
                }
                if (!ok) return false;
            }
        }
        return true;
    }
}
