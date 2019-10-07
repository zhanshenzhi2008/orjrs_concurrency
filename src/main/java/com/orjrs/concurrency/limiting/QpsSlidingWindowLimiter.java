package com.orjrs.concurrency.limiting;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * QPS限流：滑动窗口
 *
 * @author orjrs
 * @create 2019-10-06 16:23
 * @since 1.0.0
 */
@Slf4j
public class QpsSlidingWindowLimiter {

    /** 每秒请求数 */
    private static final int MAX_QPS = 10;

    /** 开始时间 */
    private static long beginTime = System.currentTimeMillis();

    /** 时间间隔 */
    private static final int INTERVAL = 1000;

    /** 每个窗口的统计数，即 （1秒/数组长度10 = 100毫秒 ）的请求统计数 */
    private AtomicInteger[] count = new AtomicInteger[10];

    /** 每秒的统计数 */
    private AtomicInteger sum;

    /** 当前下标 */
    private volatile int index;

    public void init() {
        for (int i = 0, len = count.length; i < len; i++) {
            count[i] = new AtomicInteger(0);
        }
        sum = new AtomicInteger(0);
    }

    public synchronized boolean grant() {
        count[index].incrementAndGet();
        return MAX_QPS > sum.incrementAndGet();
    }

    /**
     * 每100毫秒执行一次
     */
    public void run() {
        int tail = (index + 1) % count.length;
        int valueByTail= count[tail].getAndSet(0);
        sum.addAndGet(-valueByTail);
        index = tail;
    }

    public void request() {
        if (!grant()) {
            log.info("请求用户过多，请稍后在试！" + System.currentTimeMillis() / 1000);
            return;
        }
        try {
            // 让cpu休息会儿你，否则无法测试出结果
            TimeUnit.SECONDS.sleep(1);
            invokeService();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void invokeService() {
        // ...
        log.info("{}恭喜你，访问成功...", Thread.currentThread().getName());
    }
}
