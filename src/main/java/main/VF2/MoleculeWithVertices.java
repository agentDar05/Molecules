package main.VF2;

import main.BondType;
import main.Bytes;
import main.Ints;
import main.Molecule;

import java.util.ArrayList;
import java.util.Arrays;

public class MoleculeWithVertices implements Molecule {
    Bytes vertices = new Bytes();
    Ints bonds = new Ints();
    Bytes types = new Bytes();

    public void addAtom(byte a) { vertices.add(a); }
    public void addBond(int i, int j) { addBond(i, j, BondType.SINGLE); }
    public void addBond(int i, int j, byte t) { bonds.add(i); bonds.add(j); types.add(t); }

    public int size() { return vertices
            .size(); }
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
    public static boolean isSubgraph(main.VF2.MoleculeWithVertices p,
                                     main.VF2.MoleculeWithVertices t) {

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
                if (!feasibleVertices(p, t, mapping, depth, cand)) continue;

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
    private static boolean feasibleVertices(main.VF2.MoleculeWithVertices p,
                                            main.VF2.MoleculeWithVertices t,
                                            int[] mapping,
                                            int uP, int uT) {

        for (int i = 0; i < p.bonds.size(); i += 2) {
            int a = p.bonds.get(i);
            int b = p.bonds.get(i + 1);

            if (a != uP && b != uP) continue;

            int other = (a == uP) ? b : a;
            if (mapping[other] == -1) continue;

            if (!t.isConnected(uT, mapping[other])) return false;

            if (p.types.get(i / 2) !=
                    t.getBondType(uT, mapping[other]))
                return false;
        }
        return true;
    }

}
