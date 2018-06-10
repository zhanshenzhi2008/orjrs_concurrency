package com.orjrs.concurrency.action.lock;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Lock demo
 *
 * @author orjrs
 * @date 2018-06-1014:43
 */
@Slf4j
public class LockDemo {
    // 请求总数
    public static final int CLIENT_TOTAL = 5000;
    // 同时并发执行的线程数
    public static final int THREAD_TOTAL = 200;
    private static int count = 0;
    private static Lock lock = new ReentrantLock();

    public static void main(String[] args) throws Exception {
        ExecutorService executor = Executors.newCachedThreadPool();
        CountDownLatch countDownLatch = new CountDownLatch(CLIENT_TOTAL);
        Semaphore semaphore = new Semaphore(THREAD_TOTAL);
        for (int i = 0; i < CLIENT_TOTAL; i++) {
            executor.execute(() -> {
                try {
                    semaphore.acquire();
                    add();
                    semaphore.release();
                } catch (InterruptedException e) {
                    log.error("exception", e);
                }
                countDownLatch.countDown();
            });
        }
        countDownLatch.await();
        executor.shutdown();
        log.info("count:{}", count);
    }

    public static void add() {
        lock.lock();
        try {
            count++;
        } finally {
            lock.unlock();
        }

    }
}
