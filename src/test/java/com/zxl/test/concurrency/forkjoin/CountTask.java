package com.zxl.test.concurrency.forkjoin;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.RecursiveTask;

public class CountTask extends RecursiveTask<Integer> {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private static final int THRESHOLD = 2;// 阀值
    private int start;
    private int end;

    public CountTask(int start, int end) {
        this.start = start;
        this.end = end;
    }

    @Override
    protected Integer compute() {

        int sum = 0;
        boolean directCompute = (end - start) <= THRESHOLD; // 能够直接运算吗
        if (directCompute) {
            for (int i = start; i <= end; i++) {
                sum += i;
            }

            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } else {
            int middle = (start + end) / 3;
            CountTask ct1 = new CountTask(start, middle);
            CountTask ct2 = new CountTask(middle + 1, end);
            CountTask ct3 = new CountTask(middle * 2 + 1, end);
            ct1.fork();
            ct2.fork();
            ct3.fork();
            int leftResult = ct1.join();
            int rightResult = ct2.join();
            int result3 = ct3.join();
            sum = leftResult + rightResult + result3;
        }
        return sum;
    }

    public static void main(String[] args) {
        ForkJoinPool fjPool = new ForkJoinPool();
        Future<Integer> result = fjPool.submit(new CountTask(1, 6));

        try {
            System.out.println("result:" + result.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

}
