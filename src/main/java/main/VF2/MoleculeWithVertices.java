package main.VF2;

import main.BondType;
import main.Bytes;
import main.Ints;
import main.Molecule;

import java.util.ArrayList;

public class MoleculeWithVertices implements Molecule {
    Bytes vertices = new Bytes();
    Ints bonds = new Ints();
    Bytes types = new Bytes();
    public Bytes getAtoms() {
        return vertices;
    }
    public int addAtom(byte atom) {
        vertices.add(atom);
        return vertices.size()-1;
    }

    public void addBond(int i, int j) {
        if(bondCheck(i, j))
            addBond(i, j, BondType.SINGLE);
    }
public boolean bondCheck(int i, int j){
            for (int k = 0; k < bonds.size(); k += 2) {
                int a = bonds.get(k);
                int b = bonds.get(k + 1);

                if (a == i) {
                    if (b == j) return false;
                } else if (b == i) {
                    if (a == j) return false;
                }
            }
            return true;
    }
    public void addBond(int i, int j, byte type) {
        if(bondCheck(i, j)){
            bonds.add(i);
            bonds.add(j);
            types.add(type);
        }
    }
    @Override
    public ArrayList<Integer> getBonds(int atom) {
        ArrayList<Integer> list = new ArrayList<>();

        for (int k = 0; k < bonds.size(); k += 2) {
            int a = bonds.get(k);
            int b = bonds.get(k + 1);

            if (a == atom) {
                list.add(b);
            } else if (b == atom) {
                list.add(a);
            }
        }

        return list;
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