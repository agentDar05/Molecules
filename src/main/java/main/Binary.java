package main;

public class Binary {
        public static int binaryToDecimal(String binary) {
            int result = 0;
            int length = binary.length();
            for (int i = 0; i < length; i++) {
                char bit = binary.charAt(i);
                if (bit == '1') {
                    int power = length - 1 - i;
                    result += (1 << power);
                }
            }
            return result;
        }
        public static class Person {

            public static final int MALE_MASK    = 1 << 5;
            public static final int AGE18_MASK   = 1 << 4;
            public static final int STUDENT_MASK = 1 << 3;
            public static final int ALIVE_MASK   = 1 << 2;

            public static final int BLACK = 0b00;
            public static final int BLUE  = 0b01;
            public static final int GREEN = 0b10;
            public static final int GRAY  = 0b11;

            private int data;

            public Person(boolean male, boolean age18, boolean student, boolean alive, int eyeColor) {
                if (male)    data |= MALE_MASK;
                if (age18)   data |= AGE18_MASK;
                if (student) data |= STUDENT_MASK;
                if (alive)   data |= ALIVE_MASK;

                data |= (eyeColor & 0b11);
            }

            public Person(int packedData) {
                this.data = packedData;
            }

            public boolean isMale()    { return (data & MALE_MASK) != 0; }
            public boolean isAge18()   { return (data & AGE18_MASK) != 0; }
            public boolean isStudent() { return (data & STUDENT_MASK) != 0; }
            public boolean isAlive()   { return (data & ALIVE_MASK) != 0; }

            public String getEyeColor() {
                int c = data & 0b11;
                return switch (c) {
                    case BLACK -> "BLACK";
                    case BLUE  -> "BLUE";
                    case GREEN -> "GREEN";
                    case GRAY  -> "GRAY";
                    default    -> "UNKNOWN";
                };
            }

            public static void main(String[] args) {
                int flags = 0;
                flags |= Person.MALE_MASK;
                flags |= Person.AGE18_MASK;
                flags |= Person.ALIVE_MASK;
                flags |= Person.GREEN;

                Person p = new Person(flags);

            }
        }

        public class Utf16Counter {

        public static int countUtf16Chars(byte[] data) {
            int count = 0;
            int i = 0;
            while (i + 1 < data.length) {
                int a = (data[i] & 0xFF) | ((data[i + 1] & 0xFF) << 8);
                i += 2;
                if (a >= 0xD800 && a <= 0xDBFF) {
                    if (i + 1 < data.length) {
                        int nextUnit = (data[i] & 0xFF) | ((data[i + 1] & 0xFF) << 8);
                        if (nextUnit >= 0xDC00 && nextUnit <= 0xDFFF) {
                            i += 2;
                        }
                    }
                    count++;
                } else {
                    count++;
                }
            }
            return count;
        }

        public static void main(String[] args) throws Exception {
            String text = "A✨Б";
            byte[] bytes = text.getBytes("UTF-16LE");
            System.out.println("UTF16 chars: " + countUtf16Chars(bytes)); // 3
        }
    }

}
