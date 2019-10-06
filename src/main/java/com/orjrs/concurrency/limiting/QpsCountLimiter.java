package com.orjrs.concurrency.limiting;

/**
 * QPS限流：计数器
 *
 * @author orjrs
 * @create 2019-10-06 15:48
 * @since 1.0.0
 */
public class QpsCountLimiter {

    /** 每秒请求数：50 */
    private static final int MAX_QPS = 50;

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
}
