package main.Ullman;

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
                    for (int i = 0; i < pattern.bonds.size(); i += 2) {
                        int a = pattern.bonds.get(i);
                        int b = pattern.bonds.get(i + 1);
                        if (a != depth && b != depth) continue;
                        int other = (a == depth) ? b : a;
                        int mapped = mapping[other];
                        if (mapped != -1) {
                            byte bondType = pattern.types.get(i / 2);
                            if (!target.isConnected(cand, mapped) ||
                                    target.getBondType(cand, mapped) != bondType) {
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
