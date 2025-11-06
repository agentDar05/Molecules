package main;
import java.util.*;

public class Molecules {
    Storage storage = new Storage();
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

        int i = 0;
        while (i < length && Utils.isLetter(mf.charAt(offset + i))) i++;

        String name = mf.substring(offset, offset + i);

        int number = 0;
        if (i >= length) {
            number = 1;
        } else {
            char currDigit = mf.charAt(offset + i);
            if (currDigit == '0') {
                throw new IllegalArgumentException("Number of element cannot be 0. Currently: "
                        + mf.substring(offset, offset + length));
            }
            while (i < length && Utils.isDigit(mf.charAt(offset + i))) {
                number = number * 10 + (mf.charAt(offset + i) - '0');
                i++;
            }
        }
        for (int j = 0; j < storage.size(); j++) {
            Element e = (Element) storage.get(j);
            if (e.name.equals(name)) {
                e.number += number;
                return;
            }
        }

        storage.add(new Element(name, number));
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

    static class Storage {
        private Object[] data;
        private int size;

        public Storage() {
            this.size = 0;
            data = new Object[5];
        }

        public void add(Element obj) {
            if (size == data.length) {
                resizeAndCopy();
            }
            data[size++] = obj;
        }

        private void resizeAndCopy() {
            Object[] newData = new Object[data.length * 2];
            System.arraycopy(data, 0, newData, 0, data.length);
            data = newData;
        }

        public String toString() {
            StringBuilder output = new StringBuilder();
            for (int i = 0; i < size; i++) {
                if (i > 0) output.append(" ");
                output.append(get(i));
            }
            return output.toString();
        }

        public Object get(int idx) {
            return data[idx];
        }

        public int size() {
            return size;
        }
    }
}