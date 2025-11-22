package main;
import java.util.*;

public class Molecules {
    Map<Integer, Integer> storage = new HashMap<>();
    ArrayList<Integer> offset = new ArrayList<>();

    public void parse(String molecule) {
        if (molecule == null || molecule.isBlank()) {
            throw new IllegalArgumentException("Invalid molecule: " + molecule);
        }
        if (!Utils.isValid(molecule)){
            return;
        }
        storage.clear();
        offset.clear();
        int i = 0;
        while (i < molecule.length()) {
            char c = molecule.charAt(i);
            if (c == '(') {
                int close = findClosingBracket(molecule, i);
                String inside = molecule.substring(i + 1, close);
                Molecules sub = new Molecules();
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
            }

            else if (Character.isUpperCase(c)) {
                int start = i;
                do i++;
                while (i < molecule.length() && Utils.isLetter(molecule.charAt(i)));
                while (i < molecule.length() && Utils.isDigit(molecule.charAt(i))) i++;
                int length = i - start;

                store(molecule, start, length);
                offset.add(start);
            }

            else {
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
        if (!Utils.existsInPTable(name)){
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
        Integer numberInPTable = Utils.numberInPTable(name);
        if (numberInPTable == null || numberInPTable < 0) {
            return;
        }
        Integer foundNumber = storage.get(numberInPTable);
        if (foundNumber != null) {
            number += foundNumber;
        }
        storage.put(numberInPTable, number);
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
        final static String[] SYMBOLS = new String[] {
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
        public static Integer numberInPTable(String s) {
            return PT_MAP.getOrDefault(s, -1); // O(1)
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
}
