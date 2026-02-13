package main;

import java.util.ArrayList;
import java.util.Arrays;

public class MoleculeWithVertices implements Molecule {
    Bytes vertices = new Bytes();
    Ints bonds = new Ints();
    Bytes types = new Bytes();

    public void addAtom(byte a) { vertices.add(a); }
    public void addBond(int i, int j) { addBond(i, j, BondType.SINGLE); }
    public void addBond(int i, int j, byte t) { bonds.add(i); bonds.add(j); types.add(t); }

    public int size() { return vertices.size(); }
    public byte getAtom(int i) { return vertices.get(i); }

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

    public static boolean isSubgraph(MoleculeWithVertices pattern, MoleculeWithVertices target) {
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

    private static boolean dfs(MoleculeWithVertices p, MoleculeWithVertices t,
                               int[] mapping, boolean[] used, int uP, int uT) {
        mapping[uP] = uT;
        used[uT] = true;

        for (int i = 0; i < p.bonds.size(); i += 2) {
            int a = p.bonds.get(i), b = p.bonds.get(i+1);
            if (a != uP && b != uP) continue;
            int vP = (a == uP) ? b : a;
            byte tBond = p.types.get(i/2);

            int mapped = mapping[vP];
            if (mapped != -1) {
                if (!t.isConnected(uT, mapped) || t.getBondType(uT, mapped) != tBond)
                    return false;
            } else {
                boolean ok = false;
                for (int j = 0; j < t.bonds.size(); j += 2) {
                    int x = t.bonds.get(j), y = t.bonds.get(j+1);
                    int vT = (x == uT) ? y : (y == uT ? x : -1);
                    if (vT == -1 || used[vT]) continue;
                    if (t.getAtom(vT) != p.getAtom(vP)) continue;
                    if (t.types.get(j/2) != tBond) continue;
                    if (dfs(p, t, mapping, used, vP, vT)) {
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
