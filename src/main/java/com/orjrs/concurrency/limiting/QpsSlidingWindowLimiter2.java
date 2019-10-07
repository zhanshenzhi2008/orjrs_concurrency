package com.orjrs.concurrency.limiting;

import lombok.extern.slf4j.Slf4j;

import java.util.LinkedList;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * QPS限流：滑动窗口
 *
 * @author orjrs
 * @create 2019-10-06 16:23
 * @since 1.0.0
 */
@Slf4j
public class QpsSlidingWindowLimiter2 {

    /** 每秒请求数 */
    private static final int MAX_QPS = 10;

    /** 时间间隔 */
    private static final int INTERVAL = 1000;

    /** 每个窗口的统计数，即 （1秒/数组长度10 = 100毫秒 ）的请求统计数 */
    private LinkedList<Long> count = new LinkedList<>();

    /** 每秒的统计数 服务访问次数，可以放在Redis中，实现分布式系统的访问计数 */
    private static AtomicLong sum = new AtomicLong(0L);

    public void request() throws InterruptedException {
        for (; ; ) {
            count.add(sum.incrementAndGet());
             if (count.size() > MAX_QPS) {
                log.info("请求用户过多，请稍后在试！" + System.currentTimeMillis() / 1000);
                count.removeFirst();
                return;
            }
            //比较最后一个和第一个，两者相差一秒
            long diff = count.peekLast() - count.peekFirst();
            if (diff > (INTERVAL + 1) / MAX_QPS) {
                break;
            }
            TimeUnit.MILLISECONDS.sleep(100);
        }

        // 让cpu休息会儿你，否则无法测试出结果
        //TimeUnit.SECONDS.sleep(1);
        invokeService();
    }

    private void invokeService() {
        // ...
        log.info("{}恭喜你，访问成功...", Thread.currentThread().getName());
    }

}
