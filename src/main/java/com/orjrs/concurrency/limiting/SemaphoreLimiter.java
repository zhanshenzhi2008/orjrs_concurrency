package com.orjrs.concurrency.limiting;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

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
    private static Semaphore semaphore = new Semaphore(MAX_REQ_COUNT);

    public void request() {
        if (!semaphore.tryAcquire()) {
            log.info("请求用户过多，请稍后在试！" + System.currentTimeMillis() / 1000);
            return;
        }
        try {
            // 让cpu休息会儿你，否则无法测试出结果
            TimeUnit.SECONDS.sleep(1);
            invokeService();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            semaphore.release();
        }
    }

    private void invokeService() {
        // ...
        log.info("{}恭喜你，访问成功...", Thread.currentThread().getName());
    }
}
