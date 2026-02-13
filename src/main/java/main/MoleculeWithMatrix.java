package main;

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

    private static boolean dfs(MoleculeWithMatrix p, MoleculeWithMatrix t,
                               int[] mapping, boolean[] used, int uP, int uT) {
        mapping[uP] = uT;
        used[uT] = true;

        for (int vP = 0; vP < p.size(); vP++) {
            if (!p.isConnected(uP, vP)) continue;
            int mapped = mapping[vP];
            if (mapped != -1) {
                if (!t.isConnected(uT, mapped) || p.getBondType(uP, vP) != t.getBondType(uT, mapped))
                    return false;
            } else {
                boolean ok = false;
                for (int vg = 0; vg < t.size(); vg++) {
                    if (used[vg] || !t.isConnected(uT, vg)) continue;
                    if (t.getAtom(vg) != p.getAtom(vP)) continue;
                    if (p.getBondType(uP, vP) != t.getBondType(uT, vg)) continue;
                    if (dfs(p, t, mapping, used, vP, vg)) {
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
