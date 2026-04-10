package main;

import java.util.ArrayList;

public interface Molecule {
    ArrayList<ArrayList<Integer>> bonds = new ArrayList<>();
    Bytes getAtoms();

    ArrayList<Integer> getBonds(int atom);
    void addAtom(byte a);
    void addBond(int i, int j, byte type);
    void addBond(int i, int j);
    byte getAtom(int i);
    ArrayList<Integer> getIndexes(byte element);
    boolean isConnected(int i, int j);
    byte getBondType(int i, int j);

    int size();

}