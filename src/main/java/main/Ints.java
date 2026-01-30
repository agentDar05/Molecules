package main;

public class Ints {
    private int[] data = new int[10];
    private int size = 0;

    public void add(int x) {
        if (size == data.length) {
            int[] n = new int[data.length * 2];
            System.arraycopy(data, 0, n, 0, data.length);
            data = n;
        }
        data[size++] = x;
    }

    public int get(int i) {
        return data[i];
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }
}
