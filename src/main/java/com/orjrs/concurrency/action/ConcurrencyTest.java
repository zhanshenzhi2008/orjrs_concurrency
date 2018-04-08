package com.orjrs.concurrency.action;

import com.orjrs.concurrency.annoations.UnThreadSafe;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * 并发测试
 *
 * @author orjrs
 * @date 2018-04-0822:00
 */
@Slf4j
@UnThreadSafe
public class ConcurrencyTest {
    /** 请求总数 */
    private static int clientTotal = 5000;

    /** 同时并发数 */
    private static int threadTotal = 5000;
    /***/
    private static int count = 0;

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newCachedThreadPool();
        final Semaphore semaphore = new Semaphore(threadTotal);
        final CountDownLatch countDownLatch = new CountDownLatch(clientTotal);
        try {
            for (int i = 0; i < clientTotal; i++) {
                executorService.execute(() -> {
                    add();
                    semaphore.release();
                    countDownLatch.countDown();
                });
            }
            semaphore.acquire();
        } catch (InterruptedException e) {
            log.info("异常", e);
        }
        countDownLatch.await();
        executorService.shutdown();
        log.info("count:{}", count);
    }

    private static void add() {
        count++;
    }
}
