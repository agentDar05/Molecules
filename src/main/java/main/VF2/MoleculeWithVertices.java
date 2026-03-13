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
    public Bytes getAtoms() {
        return vertices;
    }
    public Bytes getTypes() {
        return types;
    }
    public Ints getBonds(){
        return bonds;
    }
    public void addAtom(byte atom) {
        vertices.add(atom);
    }

    public void addBond(int i, int j) {
        addBond(i, j, BondType.SINGLE);
    }

    public void addBond(int i, int j, byte type) {
        bonds.add(i);
        bonds.add(j);
        types.add(type);
    }

    public int size() {
        return vertices.size();
    }

    public byte getAtom(int i) {
        return vertices.get(i);
    }

    public ArrayList<Integer> getIndexes(byte atom) {
        ArrayList<Integer> result = new ArrayList<>();
        for (int i = 0; i < vertices.size(); i++)
            if (vertices.get(i) == atom) result.add(i);
        return result;
    }

    public boolean isConnected(int a, int b) {
        for (int i = 0; i < bonds.size(); i += 2)
            if ((bonds.get(i) == a && bonds.get(i + 1) == b) || (bonds.get(i) == b && bonds.get(i + 1) == a))
                return true;
        return false;
    }

    public byte getBondType(int a, int b) {
        for (int i = 0; i < bonds.size(); i += 2)
            if ((bonds.get(i) == a && bonds.get(i + 1) == b) || (bonds.get(i) == b && bonds.get(i + 1) == a))
                return types.get(i / 2);
        return 0;
    }
}