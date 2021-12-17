package com.company.dymrin17;

import java.util.Arrays;

public class ValueCalculator {
    private static final int SIZE = 1000000;
    private static final int HALF_OF_SIZE = SIZE / 2;
    private final float[] array = new float[SIZE];

    public void doCalc() {
        long start = System.currentTimeMillis();
        Arrays.fill(array, 2);

        float[] a1 = new float[HALF_OF_SIZE];
        float[] a2 = new float[HALF_OF_SIZE];


        System.arraycopy(array, 0, a1, 0, HALF_OF_SIZE);
        System.arraycopy(array, HALF_OF_SIZE, a2, 0, HALF_OF_SIZE);

        Thread t0 = new Thread(getCalcOperation(a1));
        Thread t1 = new Thread(getCalcOperation(a2));

        t0.start();
        t1.start();

        try {
            t0.join();
            t1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.arraycopy(a1, 0, array, 0, HALF_OF_SIZE);
        System.arraycopy(a2, 0, array, HALF_OF_SIZE, HALF_OF_SIZE);

        long end = System.currentTimeMillis() - start;
        System.out.println("Operation took: " + end);
    }


    private Runnable getCalcOperation(float[] array) {
        return new Runnable() {
            @Override
            public void run() {
                doFormulaCalc(array);
            }
        };
    }

    private void doFormulaCalc(float[] array) {
        for (int i = 0; i < array.length; i++) {
            array[i] = (float) (array[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
        }
    }
}
