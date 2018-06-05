package com.orjrs.concurrency.action.aqs;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * Semaphore 信号量
 *
 * @author orjrs
 * @date 2018-06-0521:49
 */

@Slf4j
public class SemaphoreDemo {
    /** 线程总数 */
    private final static int THREAD_COUNT = 200;

    public static void main(String[] args) {
        ExecutorService exec = Executors.newCachedThreadPool();
        final Semaphore semaphore = new Semaphore(10);
        for (int i = 0; i < THREAD_COUNT; i++) {
            final int threadNum = i;
            exec.execute(() -> {
                try {
                    semaphore.acquire();// 获取许可
                    //semaphore.acquire(3);// 获取3个许可
                    test(threadNum);
                    semaphore.release();// 释放许可
                } catch (Exception e) {
                    log.error("exception", e);
                }
            });
        }
        log.info("finish");
        exec.shutdown();
    }

    public static void test(int threadNum) throws Exception {
        Thread.sleep(100);
        log.info("{}", threadNum);
    }
}
