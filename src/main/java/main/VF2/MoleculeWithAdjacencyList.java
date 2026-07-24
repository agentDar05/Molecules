package main.VF2;

import main.Atom;
import main.BondType;
import main.Bytes;
import main.Molecule;

import java.util.ArrayList;

public class MoleculeWithAdjacencyList implements Molecule {

    Bytes atoms = new Bytes();
    ArrayList<Atom> atomArray = new ArrayList<>();
    public ArrayList<ArrayList<Integer>> bonds = new ArrayList<>();
    public byte[][] bondTypes = new byte[50][50];
    public Bytes getAtoms() {
        return atoms;
    }
    public byte[][] getTypes() {
        return bondTypes;
    }
    public ArrayList<ArrayList<Integer>> getAllBonds(){
        return bonds;
    }
    public int addAtom(byte atomType) {
        atomArray.add(new Atom(atomType));
        atoms.add(atomType);
        bonds.add(new ArrayList<>());
        return atoms.size()-1;
    }
    public int addAtom(byte atomType, byte atomCharge) {
        atomArray.add(new Atom(atomType));
        atoms.add(atomType);
        bonds.add(new ArrayList<>());
        return atoms.size()-1;
    }
    public void setAtomCharge(int idx, byte atomCharge){
        atomArray.get(idx).setCharge(atomCharge);
    }
    public ArrayList<Integer> getBonds(int atom) {return bonds.get(atom);}
    public void addBond(int i, int j) {
        addBond(i, j, BondType.SINGLE);
    }

    public void addBond(int i, int j, byte type) {
        bonds.get(i).add(j);
        bonds.get(j).add(i);
        makeAromatic(i);
        makeAromatic(j);
        bondTypes[i][j] = type;
        bondTypes[j][i] = type;
    }

    public void makeAromatic(int idx){
        atomArray.get(idx).makeAromantic();
    }
    public void isAromatic(int idx){
        atomArray.get(idx).isAromatic();
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