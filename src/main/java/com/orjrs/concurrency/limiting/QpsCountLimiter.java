package com.orjrs.concurrency.limiting;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * QPS限流：计数器
 *
 * @author orjrs
 * @create 2019-10-06 15:48
 * @since 1.0.0
 */
@Slf4j
public class QpsCountLimiter {

    /** 每秒请求数 */
    private static final int MAX_QPS = 10;

    /** 统计数 */
    private int count;

    /** 统计数 */
    private static long beginTime = System.currentTimeMillis();

    /** 时间间隔 */
    private static final int INTERVAL = 1000;

    public synchronized boolean grant() {
        long endTime = System.currentTimeMillis();
        // 隐患：临界值问题->比如最后1秒的后500毫秒来了50个请求，第二秒的前500毫秒来了50个请求，这时候一秒内count会变成100，结果就会出错，实际这时候的QPS为100
        // 解决方案：滑动窗口
        if (endTime - beginTime >= INTERVAL) {
            count = 1;
            beginTime = endTime;
            return true;
        }
        count++;
        return MAX_QPS > count;
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
