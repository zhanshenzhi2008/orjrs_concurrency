package com.orjrs.concurrency.action.aqs;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * CountDownLatch
 *
 * @author orjrs
 * @date 2018-06-0521:29
 */
@Slf4j
public class CountDownLatchDemo {
    /** 线程总数 */
    private final static int THREAD_COUNT = 200;

    public static void main(String[] args) {
        ExecutorService exec = Executors.newCachedThreadPool();
        final CountDownLatch countDownLatch = new CountDownLatch(THREAD_COUNT);
        for (int i = 0; i < THREAD_COUNT; i++) {
            final int threadNum = i;
            exec.execute(() -> {
                try {
                    test(threadNum);
                } catch (Exception e) {
                    log.error("exception", e);
                } finally {
                    countDownLatch.countDown();
                }
            });
        }
        try {
            countDownLatch.await();
            //countDownLatch.await(10, TimeUnit.MILLISECONDS);// 加上这个时间，执行结果不一样
        } catch (InterruptedException e) {
            log.error("exception", e);
        }
        log.info("finish");
        exec.shutdown();
    }

    public static void test(int threadNum) throws Exception {
        Thread.sleep(100);
        log.info("{}", threadNum);
        Thread.sleep(100);
    }
}
