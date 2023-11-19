package com.loo.tp.utils;

public class ArrayUtils {
    public static boolean contains(long[] arr, long value) {
        for (long l : arr) {
            if (l == value) {
                return true;
            }
        }
        return false;
    }
}
