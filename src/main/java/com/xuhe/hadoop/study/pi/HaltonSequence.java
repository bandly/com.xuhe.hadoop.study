package com.xuhe.hadoop.study.pi;

/**
 * 哈尔顿序列
 * @Company 杭州木瓜科技有限公司
 * @className: HaltonSequence.java
 * @author xuhe@amugua.com 
 * @date 2017年4月26日 下午1:49:47
 */
public class HaltonSequence {

    int[] baseVector;

    /**
     * Construct a Halton sequence with d = base.length dimensions where the i-th component uses base[i] as base.
     * 
     * @param base Vector of base integers for each component.
     */
    public HaltonSequence(int[] baseVector) {
        // Check base
        for (int base : baseVector) {
            if (base < 2)
                throw new RuntimeException(
                    "Cannot create Halton sequence with base less than two.");
        }

        this.baseVector = baseVector;
    }

    /**
     * Construct a one dimensional Halton sequence (Van der Corput sequence) with given base.
     * 
     * @param base Base of the sequence.
     */
    public HaltonSequence(int base) {
        // Check base
        if (base < 2)
            throw new RuntimeException("Cannot create Halton sequence with base less than two.");

        this.baseVector = new int[] { base };
    }

    /**
     * Print the first 1000 Halton numbers for base b = (2,3).
     * 
     * @param args
     */
    public static void main(String[] args) {
        System.out.println("Halton sequence (base b = (2,3)):");
        for (int i = 0; i < 1000; i++) {
            System.out.println("" + getHaltonNumber(i, 2) + "\t" + getHaltonNumber(i, 3));
        }
    }

    /**
     * Get Halton number for given index.
     * 
     * @param index Index of the Halton number.
     * @return Halton number (vector).
     */
    public double[] getHaltonNumber(int index) {
        double[] x = new double[baseVector.length];
        for (int baseIndex = 0; baseIndex < baseVector.length; baseIndex++) {
            x[baseIndex] = getHaltonNumber(index, baseVector[baseIndex]);
        }
        return x;
    }

    /**
     * Get Halton number for given index and base.
     * 
     * @param index Index of the Halton number (starting at 0).
     * @param base Base of the Halton number.
     * @return Halton number.
     */
    static double getHaltonNumber(int index, int base) {
        // Check base
        if (base < 2)
            throw new RuntimeException("Cannot create Halton number with base less than two.");
        if (index < 0)
            throw new RuntimeException("Cannot create Halton number with index less than zero.");

        // Index shift: counting of the function start at 0, algorithm below start at 1.
        index++;

        // Calculate Halton number x
        double x = 0;
        double factor = 1.0 / base;
        while (index > 0) {
            x += (index % base) * factor;
            factor /= base;
            index /= base;
        }
        return x;
    }
}
