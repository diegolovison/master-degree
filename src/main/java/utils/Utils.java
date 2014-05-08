package utils;

import java.util.Random;

public class Utils {

    private static final Random rand = new Random();

    public static int random(int min, int max) {

        return rand.nextInt((max - min) + 1) + min;
    }

    public static int[][] copy(int[][] a) {

        int[][] b = new int[a.length][];
        for (int i=0; i<a.length; i++) {
            b[i] = copy(a[i]);
        }

        return b;
    }

    public static int[] copy(int[] a) {

        int[] copy = new int[a.length];
        System.arraycopy(a, 0, copy, 0, a.length);
        return copy;
    }
}
