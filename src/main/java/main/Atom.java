package main;

public class Atom {
    public byte atom;
    public boolean aromatic;
    public byte charge;
    public Atom(byte atom) {
        this.atom = atom;
    }
    public void makeAromantic(){
        aromatic = true;
    }
    public boolean isAromatic(){
        return aromatic;
    }
    public void setCharge(byte charge){
        this.charge = charge;
    }

}
