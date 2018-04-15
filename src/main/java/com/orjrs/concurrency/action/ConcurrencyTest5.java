package com.orjrs.concurrency.action;

import com.orjrs.concurrency.annoations.ThreadSafe;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

/**
 * 并发测试
 *
 * @author orjrs
 * @date 2018-04-0822:00
 */
@Slf4j
@ThreadSafe
public class ConcurrencyTest5 {
    /** 请求总数 */
    private static int clientTotal = 5000;

    /** 同时并发数 */
    private static int threadTotal = 5000;
    /***/
    private static AtomicIntegerFieldUpdater<ConcurrencyTest5> updater =
            AtomicIntegerFieldUpdater.newUpdater(ConcurrencyTest5.class, "count");
    @Getter
    private volatile int count = 100;

    private static ConcurrencyTest5 test5 = new ConcurrencyTest5();

    public static void main(String[] args) {
        if (updater.compareAndSet(test5, 100, 120)) {
            log.info("update success 1: {}", test5.getCount());
        }

        if (updater.compareAndSet(test5, 100, 120)) {
            log.info("update success 2: {}", test5.getCount());
        } else {
            log.info("update failed: {}", test5.getCount());
        }
    }

}
