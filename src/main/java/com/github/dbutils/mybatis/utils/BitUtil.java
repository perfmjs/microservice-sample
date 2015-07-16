package com.github.dbutils.mybatis.utils;

import java.io.IOException;
import java.util.BitSet;

public class BitUtil {
    
    public static BitSet convert(long value) {
        BitSet bits = new BitSet();
        int index = 0;
        while (value != 0L) {
            if (value % 2L != 0) {
                bits.set(index);
            }
            ++index;
            value = value >>> 1;
        }
        return bits;
    }

    public static long convert(BitSet bits) {
        long value = 0L;
        for (int i = 0; i < bits.length(); ++i) {
            value += bits.get(i) ? (1L << i) : 0L;
        }
        return value;
    }

    public static String formatToStr(BitSet bits) {
        StringBuilder sb = new StringBuilder();
        for (int i = Long.SIZE-1; i >= 0; --i) {
            sb.append(bits.get(i) ?"1":"0");
        }
        return sb.toString();
    }
    
    public static void main(String[] argv) throws IOException {
        BitSet set = new BitSet(3);
        set.set(0);
        set.set(1);
//        System.out.println(set.get(0) + "-------" + convert(set) + "/" + set.get(3));
        System.out.println(convert(1+2*Long.MAX_VALUE) + "/" + formatToStr(convert(100)) + "/" + convert(100));
    }

}
