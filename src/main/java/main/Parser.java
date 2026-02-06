package main;

import java.util.*;

public class Parser {
    Map<Integer, Integer> storage = new HashMap<>();
    ArrayList<Integer> offset = new ArrayList<>();
    static final byte C = 6;
    static final byte O = 8;
    static final byte H = 1;

    public void parse(String molecule) {
        if (molecule == null || molecule.isBlank()) {
            throw new IllegalArgumentException("Invalid molecule: " + molecule);
        }
        if (!Utils.isValid(molecule)) {
            return;
        }
        int i = 0;
        while (i < molecule.length()) {
            char c = molecule.charAt(i);
            if (c == '(') {
                int close = findClosingBracket(molecule, i);
                String inside = molecule.substring(i + 1, close);
                Parser sub = new Parser();
                sub.parse(inside);
                int j = close + 1;
                int mul = 0;
                while (j < molecule.length() && Utils.isDigit(molecule.charAt(j))) {
                    mul = mul * 10 + (molecule.charAt(j) - '0');
                    j++;
                }
                if (mul == 0) mul = 1;
                for (Map.Entry<Integer, Integer> e : sub.storage.entrySet()) {
                    storage.merge(e.getKey(), e.getValue() * mul, Integer::sum);
                }

                i = j;
            } else if (Character.isUpperCase(c)) {
                int start = i;
                do i++;
                while (i < molecule.length() && Utils.isLetter(molecule.charAt(i)));
                while (i < molecule.length() && Utils.isDigit(molecule.charAt(i))) i++;
                int length = i - start;

                store(molecule, start, length);
                offset.add(start);
            } else {
                throw new IllegalArgumentException("Invalid character at position " + i + ": " + c);
            }
        }
    }

    public void store(String mf, int offset, int length) {
        if (mf == null || length <= 0 || offset < 0 || offset + length > mf.length()) {
            throw new IllegalArgumentException("Invalid input for store");
        }
        int pos = offset;
        int end = offset + length;
        int nameStart = pos;
        do pos++;
        while (pos < end && Utils.isLetter(mf.charAt(pos)));
        int nameEnd = pos;
        int nameLen = nameEnd - nameStart;
        char[] nameChars = new char[nameLen];
        mf.getChars(nameStart, nameEnd, nameChars, 0);
        String name = new String(nameChars);
        if (!Utils.existsInPTable(name)) {
            return;
        }
        int number = 0;
        if (pos == end) {
            number = 1;
        } else {
            if (mf.charAt(pos) == '0') {
                throw new IllegalArgumentException("Number of element cannot be 0: " + name);
            }
            while (pos < end && Utils.isDigit(mf.charAt(pos))) {
                number = number * 10 + (mf.charAt(pos) - '0');
                pos++;
            }
            if (number == 0) number = 1;
        }
        int atomicNumber = Utils.numberInPTable(name);
        Integer element = storage.get(atomicNumber);
        if (element != null) {
            number += element;
        }
        storage.put(atomicNumber, number);
    }

    public double calculateMW() {
        return Utils.calculateMW(storage);
    }

    public double calculateEMW() {
        return Utils.calculateMonoisotopic(storage);
    }

    @Override
    public String toString() {
        if (storage.isEmpty()) return "";
        StringBuilder sb = new StringBuilder();
        storage.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .forEach(e -> {
                    sb.append(Utils.SYMBOLS[e.getKey()]);
                    int cnt = e.getValue();
                    if (cnt > 1) sb.append(cnt);
                });
        return sb.toString();
    }

    static class Element {
        String name;
        int number;

        public Element(String name, int number) {
            this.name = name;
            this.number = number;
        }

        @Override
        public String toString() {
            return name + number;
        }
    }

    static class Utils {
        final static String[] SYMBOLS = new String[]{
                "H", "He",
                "Li", "Be", "B", "C", "N", "O", "F", "Ne",
                "Na", "Mg", "Al", "Si", "P", "S", "Cl", "Ar",
                "K", "Ca", "Sc", "Ti", "V", "Cr", "Mn", "Fe", "Co", "Ni", "Cu", "Zn",
                "Ga", "Ge", "As", "Se", "Br", "Kr",
                "Rb", "Sr", "Y", "Zr", "Nb", "Mo", "Tc", "Ru", "Rh", "Pd", "Ag", "Cd",
                "In", "Sn", "Sb", "Te", "I", "Xe",
                "Cs", "Ba", "La", "Ce", "Pr", "Nd", "Pm", "Sm", "Eu", "Gd", "Tb", "Dy",
                "Ho", "Er", "Tm", "Yb", "Lu", "Hf", "Ta", "W", "Re", "Os", "Ir", "Pt",
                "Au", "Hg", "Tl", "Pb", "Bi", "Po", "At", "Rn",
                "Fr", "Ra", "Ac", "Th", "Pa", "U", "Np", "Pu", "Am", "Cm", "Bk", "Cf",
                "Es", "Fm", "Md", "No", "Lr", "Rf", "Db", "Sg", "Bh", "Hs", "Mt", "Ds",
                "Rg", "Cn", "Nh", "Fl", "Mc"
        };

        final static double[] MW = new double[]{
                1.008, 4.0026,
                6.94, 9.0122,
                10.81, 12.011, 14.007, 15.999, 18.998, 20.180,
                22.990, 24.305, 26.982, 28.085, 30.974, 32.06, 35.45, 39.948,
                39.098, 40.078, 44.956, 47.867, 50.942, 51.996, 54.938, 55.845, 58.933, 58.693,
                63.546, 65.38, 69.723, 72.630, 74.922, 78.971, 79.904, 83.798,
                85.468, 87.62, 88.906, 91.224, 92.906, 95.95, 98.0, 101.07, 102.91, 106.42,
                107.87, 112.41, 114.82, 118.71, 121.76, 127.60, 126.90, 131.29,
                132.91, 137.33,
                138.91, 140.12, 140.91, 144.24,
                145.0, 150.36, 151.96, 157.25, 158.93, 162.50, 164.93, 167.26, 168.93, 173.05,
                174.97, 178.49, 180.95, 183.84, 186.21, 190.23, 192.22, 195.08, 196.97, 200.59,
                204.38, 207.2, 208.98, 209.0, 210.0, 222.0, 223.0, 226.0, 227.0,
                232.04, 231.04, 238.03, 237.0, 244.0,
                243.0, 247.0, 247.0, 251.0, 252.0, 257.0, 258.0, 259.0,
                262.0, 267.0, 268.0, 271.0, 270.0, 277.0, 278.0, 281.0, 282.0, 285.0, 286.0,
                289.0, 290.0, 293.0, 294.0, 294.0
        };
        final static double[] MONO = new double[]{
                1.00782503223, 4.00260325413, 7.0160034366, 9.012183065, 11.00930536, 12.00000000000, 14.00307400443, 15.99491461957,
                18.998403163, 19.9924401762, 22.9897692820, 23.985041697, 26.981538626, 27.9769265325, 30.97376199842, 31.9720711744,
                34.968852682, 39.9623831237, 38.9637064864, 39.962590863, 44.95590828, 47.94794628, 50.943959507, 51.94050623,
                54.93804391, 55.93493633, 58.93319429, 57.93534241, 62.92959772, 63.92914201, 68.9255735, 73.92117776,
                74.92159457, 78.9183376, 79.9165218, 82.91413012, 84.9117897379, 87.905612124, 88.9058403, 89.9046977,
                92.9063730, 97.90540482, 98.0, 101.904349, 102.905504, 105.903480, 106.905097, 113.903365,
                114.903878, 119.902202, 120.9038157, 126.904473, 131.9041535, 132.90545196, 137.9052472, 138.9063563,
                139.9054431, 140.9076576, 141.907729, 144.912749, 151.919739, 152.921238, 157.924112, 158.9253547,
                163.9291819, 164.9303288, 165.9302995, 168.9342179, 173.9388664, 174.9407752, 179.9465570, 180.947996,
                183.950930, 186.955750, 189.959217, 192.962982, 194.964774, 196.966569, 201.970643, 204.9744275,
                207.9766521, 208.9803991, 208.9824308, 209.9871479, 222.0175777, 223.019736, 226.0254098, 227.0277523,
                232.0380558, 231.03588, 238.0507882, 237.0481736, 244.064205, 243.0613813, 247.070307, 247.070299,
                251.0795886, 252.08298, 257.095106, 258.098431, 259.10103, 262.10961, 267.0, 268.0,
                271.0, 270.0, 276.0, 277.0, 281.0, 282.0, 285.0, 286.0,
                289.0, 290.0, 293.0, 294.0, 294.0
        };

        private static final Map<String, Integer> PT_MAP = new HashMap<>();

        static {
            for (int i = 0; i < SYMBOLS.length; i++) {
                PT_MAP.put(SYMBOLS[i], i);
            }
        }

        public static boolean isDigit(char ch) {
            return ch >= '0' && ch <= '9';
        }

        public static boolean isLetter(char ch) {
            return (ch >= 'A' && ch <= 'Z') || (ch >= 'a' && ch <= 'z');
        }

        public static boolean existsInPTable(String s) {
            return PT_MAP.containsKey(s); // O(1)
        }

        public static int numberInPTable(String s) {
            Integer e = PT_MAP.get(s);// O(1)
            if (e == null)
                throw new IllegalArgumentException("No such chemical element: " + s);
            return e;

        }

        public static double calculateMW(Map<Integer, Integer> elementsInMap) {
            double mw = 0.0;
            for (Map.Entry<Integer, Integer> element : elementsInMap.entrySet()) {
                int key = element.getKey();
                int value = element.getValue();
                double m = Utils.MW[key];
                mw += m * value;
            }
            return mw;
        }

        /**
         * EMW is the average mass of a molecule based on the natural mix of isotopes.
         * Monoisotopic mass is the mass of the same molecule if you pretend every atom is only the lightest, most common isotope.
         */
        public static double calculateMonoisotopic(Map<Integer, Integer> elementsInMap) {
            double mw = 0.0;
            for (Map.Entry<Integer, Integer> element : elementsInMap.entrySet()) {
                int key = element.getKey();
                int value = element.getValue();
                double m = Utils.MONO[key];
                mw += m * value;
            }
            return mw;
        }

        public static boolean isValid(String s) {
            int count = 0;
            for (int i = 0; i < s.length(); i++) {
                char c = s.charAt(i);
                if (c == '(') {
                    count++;
                } else if (c == ')') {
                    if (count == 0) return false;
                    count--;
                }
            }
            return count == 0;
        }

    }

    private int findClosingBracket(String s, int open) {
        int depth = 1;
        for (int i = open + 1; i < s.length(); i++) {
            if (s.charAt(i) == '(') depth++;
            else if (s.charAt(i) == ')') depth--;
            if (depth == 0) return i;
        }
        throw new IllegalArgumentException("Unmatched '(' at position " + open);
    }

    public static boolean isAlcoholAdjacency(MoleculeWithAdjacencyList m){
        MoleculeWithAdjacencyList oh = new MoleculeWithAdjacencyList();
        oh.addAtom(H); // 0
        oh.addAtom(O); // 1
        oh.addAtom(C); // 2
        oh.addBond(1, 0);
        oh.addBond(2, 1);
        return MoleculeWithAdjacencyList.isSubgraph(oh, m);

    }
    public static boolean isAlcoholMatrix(MoleculeWithMatrix m){
        MoleculeWithMatrix oh = new MoleculeWithMatrix();
        oh.addAtom(H); // 0
        oh.addAtom(O); // 1
        oh.addAtom(C); // 2
        oh.addBond(1, 0);
        oh.addBond(2, 1);
        return MoleculeWithMatrix.isSubgraph(oh, m);
    }
    public static boolean isAlcoholVertices(MoleculeWithVertices m){
        MoleculeWithVertices oh = new MoleculeWithVertices();
        oh.addAtom(H); // 0
        oh.addAtom(O); // 1
        oh.addAtom(C); // 2
        oh.addBond(1, 0);
        oh.addBond(2, 1);
        return MoleculeWithVertices.isSubgraph(oh, m);
    }
    public static boolean isCarboxylicAcidAdjacency(MoleculeWithAdjacencyList m){
        MoleculeWithAdjacencyList cooh = new MoleculeWithAdjacencyList();
        cooh.addAtom(H); // 0
        cooh.addAtom(O); // 1
        cooh.addAtom(O); // 2
        cooh.addAtom(C); // 3
        cooh.addBond(3, 1, BondType.DOUBLE);
        cooh.addBond(3, 2, BondType.SINGLE);
        cooh.addBond(2, 0, BondType.SINGLE);
        return MoleculeWithAdjacencyList.isSubgraph(cooh, m);
    }
    public static boolean isCarboxylicAcidMatrix(MoleculeWithMatrix m){
        MoleculeWithMatrix cooh = new MoleculeWithMatrix();
        cooh.addAtom(H); // 0
        cooh.addAtom(O); // 1
        cooh.addAtom(O); // 2
        cooh.addAtom(C); // 3
        cooh.addBond(3, 1, BondType.DOUBLE);
        cooh.addBond(3, 2, BondType.SINGLE);
        cooh.addBond(2, 0, BondType.SINGLE);
       return  MoleculeWithMatrix.isSubgraph(cooh, m);
    }
    public static boolean isCarboxylicAcidVertices(MoleculeWithVertices m){
        MoleculeWithVertices cooh = new MoleculeWithVertices();
        cooh.addAtom(H); // 0
        cooh.addAtom(O); // 1
        cooh.addAtom(O); // 2
        cooh.addAtom(C); // 3
        cooh.addBond(3, 1, BondType.DOUBLE);
        cooh.addBond(3, 2, BondType.SINGLE);
        cooh.addBond(2, 0, BondType.SINGLE);
        return MoleculeWithVertices.isSubgraph(cooh, m);
    }
//    public static boolean isAlcohol(Molecule m) {
//        for (int o : m.getIndexes((byte)8)) {
//            boolean hasH = false;
//            boolean hasC = false;
//
//            for (int i = 0; i < m.size(); i++) {
//                if (!m.isConnected(o, i)) continue;
//                if (m.getBondType(o, i) != BondType.SINGLE) continue;
//
//                byte e = m.getAtom(i);
//                if (e == 1) hasH = true;
//                if (e == 6) hasC = true;
//            }
//
//            if (hasH && hasC) return true;
//        }
//        return false;
//    }
//
//    public static boolean isAlcohol(MoleculeWithVertices m) {
//        for (int i = 0; i < m.bonds.size(); i += 2) {
//            int a = m.bonds.get(i);
//            int b = m.bonds.get(i + 1);
//            if (m.types.get(i / 2) != BondType.SINGLE) continue;
//
//            byte ea = m.vertices.get(a);
//            byte eb = m.vertices.get(b);
//
//            if ((ea == 8 && eb == 1) || (ea == 1 && eb == 8)) {
//                int o = ea == 8 ? a : b;
//                for (int j = 0; j < m.bonds.size(); j += 2) {
//                    int x = m.bonds.get(j);
//                    int y = m.bonds.get(j + 1);
//                    if (m.types.get(j / 2) != BondType.SINGLE) continue;
//
//                    if ((x == o && m.vertices.get(y) == 6) ||
//                            (y == o && m.vertices.get(x) == 6))
//                        return true;
//                }
//            }
//        }
//        return false;
//    }
//
//    public static boolean isAlcohol(MoleculeWithAdjacencyList m) {
//        for (int o : m.getIndexes((byte)8)) {
//            boolean h = false;
//            boolean c = false;
//
//            for (int n : m.bonds.get(o)) {
//                if (m.types[o][n] != BondType.SINGLE) continue;
//                if (m.atoms.get(n) == 1) h = true;
//                if (m.atoms.get(n) == 6) c = true;
//            }
//
//            if (h && c) return true;
//        }
//        return false;
//    }
//    public static boolean isAlcohol(MoleculeWithMatrix m) {
//        for (int o : m.getIndexes((byte)8)) {
//            boolean h = false;
//            boolean c = false;
//
//            for (int i = 0; i < m.size(); i++) {
//                if (!m.m[o][i]) continue;
//                if (m.types[o][i] != BondType.SINGLE) continue;
//
//                if (m.atoms.get(i) == 1) h = true;
//                if (m.atoms.get(i) == 6) c = true;
//            }
//
//            if (h && c) return true;
//        }
//        return false;
//    }

//    public static boolean isCarboxylicAcid(MoleculeWithAdjacencyList m) {
//        for (int c = 0; c < m.atoms.size(); c++) {
//            if (m.atoms.get(c) != 6) continue;
//            boolean hasDoubleO = false;
//            boolean hasSingleOwithH = false;
//            for (int i = 0; i < m.bonds.get(c).size(); i++) {
//                int o = m.bonds.get(c).get(i);
//                BondType bond = m.bondTypes.get(i);
//                if (m.atoms.get(o) != 8) continue;
//                if (bond == BondType.DOUBLE) hasDoubleO = true;
//                if (bond == BondType.SINGLE) {
//                    for (int j = 0; j < m.bonds.get(o).size(); j++) {
//                        int h = m.bonds.get(o).get(j);
//                        BondType ob = m.bondTypes.get(j);
//                        if (m.atoms.get(h) == 1 && ob == BondType.SINGLE) hasSingleOwithH = true;
//                    }
//                }
//            }
//            if (hasDoubleO && hasSingleOwithH) return true;
//        }
//        return false;
//    }
//
//    public static boolean isCarboxylicAcid(MoleculeWithMatrix m) {
//        int n = m.atoms.size();
//        for (int c = 0; c < n; c++) {
//            if (m.atoms.get(c) != 6) continue;
//            boolean hasDoubleO = false;
//            boolean hasSingleOwithH = false;
//            int oSingleIdx = -1;
//            for (int o = 0; o < n; o++) {
//                if (!m.m[c][o] || m.atoms.get(o) != 8) continue;
//                hasDoubleO = true;
//                for (int h = 0; h < n; h++) {
//                    if (m.m[o][h] && m.atoms.get(h) == 1) hasSingleOwithH = true;
//                }
//            }
//            if (hasDoubleO && hasSingleOwithH) return true;
//        }
//        return false;
//    }
//
//    public static boolean isCarboxylicAcid(MoleculeWithVertices m) {
//        for (int c = 0; c < m.vertices.size(); c++) {
//            if (m.vertices.get(c) != 6) continue;
//            boolean hasDoubleO = false;
//            boolean hasSingleOwithH = false;
//            int oSingleIdx = -1;
//            for (int i = 0; i < m.bonds.size(); i += 2) {
//                int a = m.bonds.get(i);
//                int b = m.bonds.get(i + 1);
//                BondType bond = m.bondTypes.get(i / 2);
//                if (a == c && m.vertices.get(b) == 8) {
//                    if (bond == BondType.DOUBLE) hasDoubleO = true;
//                    if (bond == BondType.SINGLE) oSingleIdx = b;
//                }
//                if (b == c && m.vertices.get(a) == 8) {
//                    if (bond == BondType.DOUBLE) hasDoubleO = true;
//                    if (bond == BondType.SINGLE) oSingleIdx = a;
//                }
//            }
//            if (oSingleIdx != -1) {
//                for (int i = 0; i < m.bonds.size(); i += 2) {
//                    int a = m.bonds.get(i);
//                    int b = m.bonds.get(i + 1);
//                    BondType bond = m.bondTypes.get(i / 2);
//                    if (bond != BondType.SINGLE) continue;
//                    if ((a == oSingleIdx && m.vertices.get(b) == 1) || (b == oSingleIdx && m.vertices.get(a) == 1)) {
//                        hasSingleOwithH = true;
//                    }
//                }
//            }
//            if (hasDoubleO && hasSingleOwithH) return true;
//        }
//        return false;
//    }
//
//    public boolean isCarboxylicAcid(Molecule m) {
//        ArrayList<Integer> C = m.getIndexes(6);
//        ArrayList<Integer> O = m.getIndexes(8);
//        ArrayList<Integer> H = m.getIndexes(1);
//        boolean[] isO = new boolean[50];
//        boolean[] isH = new boolean[50];
//        for (int o : O) isO[o] = true;
//        for (int h : H) isH[h] = true;
//        for (int c : C) {
//            int oSingleIdx = -1;
//            boolean hasDoubleO = false;
//            boolean hasSingleOwithH = false;
//            for (int o = 0; o < 50; o++) {
//                if (!isO[o] || !m.isConnected(c, o)) continue;
//                oSingleIdx = o;
//                hasDoubleO = true;
//            }
//            if (oSingleIdx != -1) {
//                for (int h = 0; h < 50; h++) {
//                    if (isH[h] && m.isConnected(oSingleIdx, h)) hasSingleOwithH = true;
//                }
//            }
//            if (hasDoubleO && hasSingleOwithH) return true;
//        }
//        return false;
//    }

}
