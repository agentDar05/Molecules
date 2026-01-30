package main;

import java.util.ArrayList;
import java.util.Arrays;

public class MoleculeWithVertices implements Molecule {
    Bytes vertices = new Bytes();
    Ints bonds = new Ints();
    Bytes types = new Bytes();

    public void addAtom(byte a) {
        vertices.add(a);
    }

    public void addBond(int i, int j) {
        addBond(i, j, BondType.SINGLE);
    }

    public void addBond(int i, int j, byte t) {
        bonds.add(i);
        bonds.add(j);
        types.add(t);
    }

    public int size() {
        return vertices.size();
    }

    public byte getAtom(int i) {
        return vertices.get(i);
    }

    public ArrayList<Integer> getIndexes(byte e) {
        ArrayList<Integer> r = new ArrayList<>();
        for (int i = 0; i < vertices.size(); i++)
            if (vertices.get(i) == e) r.add(i);
        return r;
    }

    public boolean isConnected(int a, int b) {
        for (int i = 0; i < bonds.size(); i += 2)
            if ((bonds.get(i) == a && bonds.get(i + 1) == b) ||
                    (bonds.get(i) == b && bonds.get(i + 1) == a))
                return true;
        return false;
    }

    public byte getBondType(int a, int b) {
        for (int i = 0; i < bonds.size(); i += 2)
            if ((bonds.get(i) == a && bonds.get(i + 1) == b) ||
                    (bonds.get(i) == b && bonds.get(i + 1) == a))
                return types.get(i / 2);
        return 0;
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
            if (dfs(0, start, h, g, map, used)) return true;
        }
        return false;
    }
    private static boolean dfs(
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
    public static boolean isSubgraph(
            MoleculeWithVertices h,
            MoleculeWithVertices g
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
            MoleculeWithVertices h,
            MoleculeWithVertices g,
            int[] map,
            boolean[] used
    ) {
        map[uh] = ug;
        used[ug] = true;

        for (int i = 0; i < h.bonds.size(); i += 2) {
            int a = h.bonds.get(i);
            int b = h.bonds.get(i + 1);
            if (a != uh && b != uh) continue;

            int vh = (a == uh) ? b : a;
            byte t = h.types.get(i / 2);

            int mapped = map[vh];
            if (mapped != -1) {
                if (!g.isConnected(ug, mapped)) return false;
                if (g.getBondType(ug, mapped) != t) return false;
            } else {
                boolean ok = false;
                for (int j = 0; j < g.bonds.size(); j += 2) {
                    int x = g.bonds.get(j);
                    int y = g.bonds.get(j + 1);
                    int vg = (x == ug) ? y : (y == ug ? x : -1);
                    if (vg == -1) continue;
                    if (used[vg]) continue;
                    if (g.getAtom(vg) != h.getAtom(vh)) continue;
                    if (g.types.get(j / 2) != t) continue;
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
