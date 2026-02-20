package main;

public class Bytes {
    private byte[] data = new byte[10];
    private int size = 0;

    public void add(byte x) {
        if (size == data.length) {
            int newLength = Math.min(data.length * 2, Integer.MAX_VALUE-100);
            byte[] n = new byte[newLength];
            System.arraycopy(data, 0, n, 0, data.length);
            data = n;
        }
        data[size++] = x;
    }

    public byte get(int i) {
        return data[i];
    }

    public int size() {
        return size;
    }
}
