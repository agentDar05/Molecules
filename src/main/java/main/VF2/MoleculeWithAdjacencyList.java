package main.VF2;

import main.Atom;
import main.BondType;
import main.Bytes;
import main.Molecule;

import java.util.ArrayList;

public class MoleculeWithAdjacencyList implements Molecule {

    Bytes atoms = new Bytes();
    public ArrayList<ArrayList<Integer>> bonds = new ArrayList<>();
    public byte[][] bondTypes = new byte[50][50];
    public byte[] charge = {(byte) -1, (byte) 0,(byte) -1,(byte)  0, (byte) -1, (byte) 0, (byte) -1,(byte) 0,(byte)  -1, (byte) 0, (byte) -1, (byte) 0,(byte) -1,(byte)  0, (byte) -1, (byte) 0, (byte) -1,(byte) 0,(byte)  -1, (byte) 0};
    public byte[] aromatic = new byte[20];
    public Bytes getAtoms() {
        return atoms;
    }
    public byte[][] getTypes() {
        return bondTypes;
    }
    public byte[] getCharges() {return charge;}
    public ArrayList<ArrayList<Integer>> getAllBonds(){
        return bonds;
    }
    public boolean isChiral =false;
    public boolean isChiralClockwise = false;

    public int addAtom(byte atomType) {
        atoms.add(atomType);
        bonds.add(new ArrayList<>());
        return atoms.size()-1;
    }
    public int addAtom(byte atomType, byte atomCharge) {
        atoms.add(atomType);
        bonds.add(new ArrayList<>());
        if (atomCharge != 0){
            for (int i = 0; i < charge.length; i+=2) {
                if (charge[i] == -1) {}
                charge[i] = (byte) (atoms.size()-1);
                charge[i+1] = atomCharge;
                break;
            }
        }
        return atoms.size()-1;
    }
    public void setAtomCharge(int idx, byte atomCharge){
        for (int i = 0; i < charge.length; i+=2) {
            byte currEl = charge[i];
            if (currEl == idx) {
                charge[i+1] =  atomCharge;
            }
            if (currEl == -1) {
                currEl  = (byte) (atoms.size()-1);
                charge[i+1] = atomCharge;
            }

        }
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
        for (int i = 0; i < aromatic.length; i++) {
            if (aromatic[i] == -1) {
                aromatic[i] = (byte) idx;
            }
        }
    }
    public boolean isAromatic(int idx){
        for (int i = 0; i < aromatic.length; i++) {
            if (aromatic[i] == idx) {
                return true;
            }
        }
        return false;
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

    public void setChiral(boolean isClockwise) {
        isChiral = true;
        isChiralClockwise = isClockwise;
    }
    public boolean isChiral() {
        return isChiralClockwise;
    }
    public boolean isChiralClockwise() {
        return isChiralClockwise;
    }

}