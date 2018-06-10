package com.orjrs.concurrency.action.aqs;

import java.util.concurrent.RecursiveTask;

/**
 * ForkJoinTask
 *
 * @author orjrs
 * @date 2018-06-1017:35
 */
public class ForkJoinTask extends RecursiveTask<Integer> {
    private final int threshold = 2;
    private int start;
    private int end;

    ForkJoinTask(int start, int end) {
        this.start = start;
        this.end = end;
    }

    @Override
    protected Integer compute() {
        int sum = 0;
        if (end - start <= threshold) {
            for (int i = start; start <= end; i++) {
                sum += i;
            }
        } else {
            int mid = (start + end) / 2;
            ForkJoinTask leftFJTask = new ForkJoinTask(start, mid);
            ForkJoinTask rightFJTask = new ForkJoinTask(mid, end);

            // 执行子任务
            leftFJTask.fork();
            rightFJTask.fork();

            // 等待任务执行结束合并其结果
            int lResult = leftFJTask.join();
            int rResult = rightFJTask.join();

            sum = lResult + rResult;
        }
        return sum;
    }
}
