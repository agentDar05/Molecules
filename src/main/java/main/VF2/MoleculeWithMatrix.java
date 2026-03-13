package main.VF2;

import main.BondType;
import main.Bytes;
import main.Molecule;

import java.util.ArrayList;
import java.util.Arrays;

public class MoleculeWithMatrix implements Molecule {
    Bytes atoms = new Bytes();
    boolean[][] bonds = new boolean[50][50];
    byte[][] bondTypes = new byte[50][50];
    public Bytes getAtoms() {
        return atoms;
    }
    public byte[][] getTypes() {
        return bondTypes;
    }
    public boolean[][] getBonds(){
        return bonds;
    }
    public void addAtom(byte atom) {
        atoms.add(atom);
    }

    public void addBond(int i, int j) {
        addBond(i, j, BondType.SINGLE);
    }

    public void addBond(int i, int j, byte type) {
        bonds[i][j] = bonds[j][i] = true;
        bondTypes[i][j] = bondTypes[j][i] = type;
    }

    public int size() {
        return atoms.size();
    }

    public byte getAtom(int i) {
        return atoms.get(i);
    }

    public ArrayList<Integer> getIndexes(byte atom) {
        ArrayList<Integer> result = new ArrayList<>();
        for (int i = 0; i < atoms.size(); i++)
            if (atoms.get(i) == atom) result.add(i);
        return result;
    }

    public boolean isConnected(int i, int j) {
        return bonds[i][j];
    }

    public byte getBondType(int i, int j) {
        return bondTypes[i][j];
    }
}