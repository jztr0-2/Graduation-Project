package com.poly.jztr.ecommerce.utils;

import java.util.concurrent.ThreadLocalRandom;

public class RandomUtils {
       /**
     * random a int value
     *
     * @param min include
     * @param max exclude
     * @return value in [min, max)
     */
    public static int randomInt(int min, int max) {
        return ThreadLocalRandom.current().nextInt(max - min) + min;
    }

    /**
     * random a long value
     *
     * @param min include
     * @param max exclude
     * @return value in [min, max)
     */
    public static Long randomLong(long min, long max) {
        return ThreadLocalRandom.current().nextLong() * (max - min) + min;
    }
}
