package com.orjrs.concurrency.limiting;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 并发数限流：原子 限流器
 *
 * @author orjrs
 * @create 2019-07-03 21:26
 * @since 1.0.0
 */
@Slf4j
public class AtomicLimiter {

    /** 最大并发数 */
    private static final int MAX_REQ_COUNT = 10;
    /** 原子Integer */
    private static AtomicInteger atomicInteger = new AtomicInteger(0);

    public void request() {
        // 这个自旋的方法 其实就是atomicInteger.incrementAndGet();
        for (; ; ) {
            if (atomicInteger.get() >= MAX_REQ_COUNT) {
                log.info("请求用户过多，请稍后在试！" + System.currentTimeMillis() / 1000);
                return;
            }
            int currCount = atomicInteger.get();
            if (atomicInteger.compareAndSet(currCount, currCount + 1)) {
                log.info("currCount={}", currCount);
                break;
            }

        }

        try {
            // 让cpu休息会儿你，否则无法测试出结果
            TimeUnit.SECONDS.sleep(1);
            invokeService();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            int i = atomicInteger.decrementAndGet();
        }

    }

    public void request2() {
        if (atomicInteger.get() >= MAX_REQ_COUNT) {
            log.info("请求用户过多，请稍后在试！" + System.currentTimeMillis() / 1000);
            return;
        }
        atomicInteger.incrementAndGet();
        try {
            //处理核心逻辑
            TimeUnit.SECONDS.sleep(1);
            System.out.println("--" + System.currentTimeMillis() / 1000);
            invokeService();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            atomicInteger.decrementAndGet();
        }

    }

    private void invokeService() {
        // ...
        log.info("{}恭喜你，访问成功...", Thread.currentThread().getName());
    }

}
