package main;

import java.util.ArrayList;
import java.util.Arrays;

public class MoleculeWithMatrix implements Molecule {
    Bytes atoms = new Bytes();
    boolean[][] m = new boolean[50][50];
    byte[][] types = new byte[50][50];

    public void addAtom(byte a) {
        atoms.add(a);
    }

    public void addBond(int i, int j) {
        addBond(i, j, BondType.SINGLE);
    }

    public void addBond(int i, int j, byte t) {
        m[i][j] = true;
        m[j][i] = true;
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
        return m[i][j];
    }

    public byte getBondType(int i, int j) {
        return types[i][j];
    }
    public static boolean isSubgraph(
            MoleculeWithMatrix h,
            MoleculeWithMatrix g
    ) {
        int[] map = new int[h.size()];
        boolean[] used = new boolean[g.size()];
        Arrays.fill(map, -1);

        for (int start = 0; start < g.size(); start++) {
            if (g.getAtom(start) != h.getAtom(0)) continue;
            Arrays.fill(map, -1);
            Arrays.fill(used, false);
            if (dfs(0, start, h, g, map, used)) return true;
        }
        return false;
    }
    private static boolean dfs(
            int uh, int ug,
            MoleculeWithMatrix h,
            MoleculeWithMatrix g,
            int[] map,
            boolean[] used
    ) {
        map[uh] = ug;
        used[ug] = true;

        for (int vh = 0; vh < h.size(); vh++) {
            if (!h.isConnected(uh, vh)) continue;

            int mapped = map[vh];
            if (mapped != -1) {
                if (!g.isConnected(ug, mapped)) return false;
                if (h.getBondType(uh, vh) != g.getBondType(ug, mapped)) return false;
            } else {
                boolean ok = false;
                for (int vg = 0; vg < g.size(); vg++) {
                    if (used[vg]) continue;
                    if (!g.isConnected(ug, vg)) continue;
                    if (g.getAtom(vg) != h.getAtom(vh)) continue;
                    if (h.getBondType(uh, vh) != g.getBondType(ug, vg)) continue;
                    if (dfs(vh, vg, h, g, map, used)) {
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
/*
 m = new Molecule([6, 8, 8, 1])
m[1].addBond(2);
m[0].addBond(1);
* */
