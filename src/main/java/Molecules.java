import java.util.*;

public class Molecules {
    List<Integer> nums = new ArrayList<>(Collections.nCopies(118, 0));
    List<String> arrayOfElements = new ArrayList<>();
    static Map<String, Integer> elements = new HashMap<>();
    Molecules(){
        elements.put("H", 0);
        elements.put("He", 1);
        elements.put("Li", 2);
        elements.put("Be", 3);
        elements.put("B", 4);
        elements.put("C", 5);
        elements.put("N", 6);
        elements.put("O", 7);
        elements.put("F", 8);
        elements.put("Ne", 9);
        elements.put("Na", 10);
        elements.put("Mg", 11);
        elements.put("Al", 12);
        elements.put("Si", 13);
        elements.put("P", 14);
        elements.put("S", 15);
        elements.put("Cl", 16);
        elements.put("Ar", 17);
        elements.put("K", 18);
        elements.put("Ca", 19);
        elements.put("Sc", 20);
        elements.put("Ti", 21);
        elements.put("V", 22);
        elements.put("Cr", 23);
        elements.put("Mn", 24);
        elements.put("Fe", 25);
        elements.put("Co", 26);
        elements.put("Ni", 27);
        elements.put("Cu", 28);
        elements.put("Zn", 29);
        elements.put("Ga", 30);
        elements.put("Ge", 31);
        elements.put("As", 32);
        elements.put("Se", 33);
        elements.put("Br", 34);
        elements.put("Kr", 35);
        elements.put("Rb", 36);
        elements.put("Sr", 37);
        elements.put("Y", 38);
        elements.put("Zr", 39);
        elements.put("Nb", 40);
        elements.put("Mo", 41);
        elements.put("Tc", 42);
        elements.put("Ru", 43);
        elements.put("Rh", 44);
        elements.put("Pd", 45);
        elements.put("Ag", 46);
        elements.put("Cd", 47);
        elements.put("In", 48);
        elements.put("Sn", 49);
        elements.put("Sb", 50);
        elements.put("Te", 51);
        elements.put("I", 52);
        elements.put("Xe", 53);
        elements.put("Cs", 54);
        elements.put("Ba", 55);
        elements.put("La", 56);
        elements.put("Ce", 57);
        elements.put("Pr", 58);
        elements.put("Nd", 59);
        elements.put("Pm", 60);
        elements.put("Sm", 61);
        elements.put("Eu", 62);
        elements.put("Gd", 63);
        elements.put("Tb", 64);
        elements.put("Dv", 65);
        elements.put("Ho", 66);
        elements.put("Er", 67);
        elements.put("Tm", 68);
        elements.put("Yb", 69);
        elements.put("Lu", 70);
        elements.put("Hf", 71);
        elements.put("Ta", 72);
        elements.put("W", 73);
        elements.put("Re", 74);
        elements.put("Os", 75);
        elements.put("Ir", 76);
        elements.put("Pt", 77);
        elements.put("Au", 78);
        elements.put("Hg", 79);
        elements.put("Tl", 80);
        elements.put("Pb", 81);
        elements.put("Bi", 82);
        elements.put("Po", 83);
        elements.put("At", 84);
        elements.put("Rn", 85);
        elements.put("Fr", 86);
        elements.put("Ra", 87);
        elements.put("Ac", 88);
        elements.put("Th", 89);
        elements.put("Pa", 90);
        elements.put("U", 91);
        elements.put("Np", 92);
        elements.put("Pu", 93);
        elements.put("Am", 94);
        elements.put("Cm", 95);
        elements.put("Bk", 96);
        elements.put("Cf", 97);
        elements.put("Es", 98);
        elements.put("Fm", 99);
        elements.put("Md", 100);
        elements.put("No", 101);
        elements.put("Lr", 102);
        elements.put("Rf", 103);
        elements.put("Db", 104);
        elements.put("Sg", 105);
        elements.put("Bh", 106);
        elements.put("Hs", 107);
        elements.put("Mt", 108);
        elements.put("Ds", 109);
        elements.put("Rg", 110);
        elements.put("Cn", 111);
        elements.put("Nh", 112);
        elements.put("Fl", 113);
        elements.put("Mc", 114);
        elements.put("Lv", 115);
        elements.put("Ts", 116);
        elements.put("Og", 117);
    }
    public void parse(String molecule) {
        StringBuilder currEl = new StringBuilder();
        for (int i = 0; i < molecule.length(); i++) {
            char a = molecule.charAt(i);
            if (Character.isUpperCase(a)) {
                if (currEl.length() > 0) {
                    arrayOfElements.add(currEl.toString());
                    currEl.setLength(0);
                }
            }
            currEl.append(a);
        }
        if (currEl.length() > 0) {
            arrayOfElements.add(currEl.toString());
        }
        for (String el : arrayOfElements) {
            store(el);
            System.out.println(el);
        }
    }
    public void store(String el){
        if (Character.isLetter(el.charAt(el.length()-1))){
            el=el+"1";
        }
        StringBuilder currElement = new StringBuilder();
        StringBuilder numberOfElements = new StringBuilder();
        for (int i = 0; i < el.length(); i++) {
            char a = el.charAt(i);
            if (Character.isLetter(a)){
                currElement.append(a);
            } else if (Character.isDigit(a)) {
                numberOfElements.append(a);
            }
        }
        int index = elements.get(currElement.toString());
        nums.add(index, Integer.parseInt(numberOfElements.toString()));
    }
    public List<Integer> get(){
        return nums;
    }
}

