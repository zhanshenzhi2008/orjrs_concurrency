package com.orjrs.concurrency.action.atomic;

import com.orjrs.concurrency.annoations.ThreadSafe;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

/**
 * 并发测试
 *
 * @author orjrs
 * @date 2018-04-0822:00
 */
@Slf4j
@ThreadSafe
public class ConcurrencyTest6 {
    /** 请求总数 */
    private static int clientTotal = 5000;

    /** 同时并发数 */
    private static int threadTotal = 5000;
    /***/
    private static AtomicBoolean isABoolean =
            new AtomicBoolean(false);

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executor = Executors.newCachedThreadPool();
        final Semaphore semaphore = new Semaphore(threadTotal);
        final CountDownLatch countDownLatch = new CountDownLatch(clientTotal);
        for (int i = 0; i < clientTotal; i++) {
            executor.execute(() -> {
                try {
                    semaphore.acquire();
                    test();
                    semaphore.release();
                    countDownLatch.countDown();
                } catch (InterruptedException e) {
                    log.info("异常", e);
                }
            });
        }

        countDownLatch.await();
        executor.shutdown();
        log.info("count:{}", isABoolean.get());
    }

    private static void test() {
        if (isABoolean.compareAndSet(false, true)) {
            log.info("excute");
        }
    }

}
