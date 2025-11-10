package main;
import java.util.*;

public class Molecules {
    Map<String, Element> storage = new HashMap<>();
    ArrayList<Integer> offset = new ArrayList<>();
    public void parse(String molecule) {
        if (molecule == null || molecule.isBlank()) {
            throw new IllegalArgumentException("Invalid molecule: " + molecule);
        }
        offset.clear();
        int start = 0;
        for (int i = 0; i < molecule.length(); i++) {
            char a = molecule.charAt(i);
            if (Character.isUpperCase(a)) {
                if (i != 0) {
                    store(molecule, start, i - start);
                    offset.add(start);
                }
                start = i;
            }
        }
        store(molecule, start, molecule.length() - start);
    }
    public void store(String mf, int offset, int length) {
        if (mf == null || length <= 0 || offset < 0 || offset + length > mf.length()) {
            throw new IllegalArgumentException("Invalid input for store");
        }

        int pos = offset;
        int end = offset + length;

        int nameStart = pos;
        pos++;
        while (pos < end && Utils.isLetter(mf.charAt(pos))) pos++;
        int nameEnd = pos;

        int nameLen = nameEnd - nameStart;
        char[] nameChars = new char[nameLen];
        mf.getChars(nameStart, nameEnd, nameChars, 0);
        String name = new String(nameChars);

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
        Element foundElement = storage.get(name);
        if (foundElement != null) {
            foundElement.number += number;
        }
        else {
            storage.put(name, new Element(name, number));
        }
    }

    public String toString() {
        return storage.toString();
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
        public static boolean isDigit(char ch) {
            return ch >= '0' && ch <= '9';
        }

        public static boolean isLetter(char ch) {
            return (ch >= 'A' && ch <= 'Z') || (ch >= 'a' && ch <= 'z');
        }
    }
}