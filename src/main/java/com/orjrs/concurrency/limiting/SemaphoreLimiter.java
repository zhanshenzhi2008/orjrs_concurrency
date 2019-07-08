package com.orjrs.concurrency.limiting;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Semaphore;

/**
 * 信号量实现限流
 *
 * @author orjrs
 * @create 2019-07-07 15:34
 * @since 1.0.0
 */
@Slf4j
public class SemaphoreLimiter {
    /** 最大并发数 */
    private static final int MAX_REQ_COUNT = 10;
    /** 信号量 */
    private Semaphore semaphore = new Semaphore(MAX_REQ_COUNT);

    public void request() {
        if (!semaphore.tryAcquire()) {
            return;
        }
        try {
            invokeService();
        } finally {
            semaphore.release();
        }
    }

    private void invokeService() {
        // ...
        log.info("{}访问...", Thread.currentThread().getName());
    }
}
