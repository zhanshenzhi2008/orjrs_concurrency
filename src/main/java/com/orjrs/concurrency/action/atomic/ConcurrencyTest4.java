package com.orjrs.concurrency.action.atomic;

import com.orjrs.concurrency.annoations.ThreadSafe;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.LongAdder;

/**
 * 并发测试
 *
 * @author orjrs
 * @date 2018-04-0822:00
 */
@Slf4j
@ThreadSafe
public class ConcurrencyTest4 {
    /** 请求总数 */
    private static int clientTotal = 5000;

    /** 同时并发数 */
    private static int threadTotal = 5000;
    /***/
    private static AtomicReference<Integer> count = new AtomicReference<>(0);

    public static void main(String[] args) throws InterruptedException {
        count.compareAndSet(0, 2);
        count.compareAndSet(0, 1);
        count.compareAndSet(1, 3);
        count.compareAndSet(2, 4);
        count.compareAndSet(3, 5);
        log.info("count {}", count.get());
    }

}
