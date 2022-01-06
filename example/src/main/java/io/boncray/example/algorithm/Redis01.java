package io.boncray.example.algorithm;

/**
 * @author changan
 * @version 1.0
 * @date 2022/1/4 14:23
 */
public class Redis01 {



    public static void main(String[] args) {
        for (int i = 0; i < 100000; i++) {
            increment();
        }
    }


    private static Integer counter = 0;

    /**
     * 缓慢非线性增长算法。
     *
     * 例如：
     *      0 0 1 1 1 2 2 3 3 3 3
     *   非线性递增
     *
     */
    private static void increment() {

        Integer baseVal = 5;
        Integer lfu_log_factor = 10;

        double random = Math.random();
        double p = 1.0 / (baseVal * lfu_log_factor + 1);
        if (random < p) {
            counter++;
        }
        System.out.println(counter);
    }


}
