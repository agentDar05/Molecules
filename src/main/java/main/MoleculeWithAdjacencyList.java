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

    public static boolean isSubgraph(
            MoleculeWithAdjacencyList h,
            MoleculeWithAdjacencyList g
    ) {
        int[] map = new int[h.size()];
        boolean[] used = new boolean[g.size()];
        Arrays.fill(map, -1);

        for (int start = 0; start < g.size(); start++) {
            if (g.getAtom(start) != h.getAtom(0)) continue;
            Arrays.fill(map, -1);
            Arrays.fill(used, false);
            if (dfsAdj(0, start, h, g, map, used)) return true;
        }
        return false;
    }

    private static boolean dfsAdj(
            int uh, int ug,
            MoleculeWithAdjacencyList h,
            MoleculeWithAdjacencyList g,
            int[] map,
            boolean[] used
    ) {
        map[uh] = ug;
        used[ug] = true;

        for (int vh : h.bonds.get(uh)) {
            int mapped = map[vh];
            if (mapped != -1) {
                if (!g.isConnected(ug, mapped)) return false;
                if (h.getBondType(uh, vh) != g.getBondType(ug, mapped)) return false;
            } else {
                boolean ok = false;
                for (int vg : g.bonds.get(ug)) {
                    if (used[vg]) continue;
                    if (g.getAtom(vg) != h.getAtom(vh)) continue;
                    if (h.getBondType(uh, vh) != g.getBondType(ug, vg)) continue;
                    if (dfsAdj(vh, vg, h, g, map, used)) {
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
