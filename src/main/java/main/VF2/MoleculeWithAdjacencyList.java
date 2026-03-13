package main.VF2;

import main.BondType;
import main.Bytes;
import main.Molecule;

import java.util.ArrayList;
import java.util.Arrays;

public class MoleculeWithAdjacencyList implements Molecule {

    Bytes atoms = new Bytes();
    public ArrayList<ArrayList<Integer>> bonds = new ArrayList<>();
    public byte[][] bondTypes = new byte[50][50];
    public Bytes getAtoms() {
        return atoms;
    }
    public byte[][] getTypes() {
        return bondTypes;
    }
    public ArrayList<ArrayList<Integer>> getBonds(){
        return bonds;
    }
    public void addAtom(byte atomType) {
        atoms.add(atomType);
        bonds.add(new ArrayList<>());
    }

    public void addBond(int i, int j) {
        addBond(i, j, BondType.SINGLE);
    }

    public void addBond(int i, int j, byte type) {
        bonds.get(i).add(j);
        bonds.get(j).add(i);
        bondTypes[i][j] = type;
        bondTypes[j][i] = type;
    }

    public int size() {
        return atoms.size();
    }

    public byte getAtom(int index) {
        return atoms.get(index);
    }

    public ArrayList<Integer> getIndexes(byte atomType) {
        ArrayList<Integer> result = new ArrayList<>();
        for (int i = 0; i < atoms.size(); i++) {
            if (atoms.get(i) == atomType) {
                result.add(i);
            }
        }
        return result;
    }

    public boolean isConnected(int i, int j) {
        return bonds.get(i).contains(j);
    }

    public byte getBondType(int i, int j) {
        return bondTypes[i][j];
    }


}