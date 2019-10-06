package com.orjrs.concurrency.limiting;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.internal.xml.MappingXmlParser;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * 限流器入口
 *
 * @author orjrs
 * @create 2019-07-03 22:52
 * @since 1.0.0
 */
@Slf4j
public class LimiterMain {
    /** 同时请求数 */
    private static final int REQ_COUNT = 20;

    /** 总线程数 */
    private static final int THREAD_NUM = 30;

    public static void main(String[] args) {
        /* 闭包 */
        CountDownLatch countDownLatch = new CountDownLatch(REQ_COUNT);
        ExecutorService service = Executors.newFixedThreadPool(THREAD_NUM);
        ReqThread[] reqThread = new ReqThread[THREAD_NUM];
        for (int i = 0; i < THREAD_NUM; i++) {
            /*reqThread[i] = new ReqThread();
            try {
                Thread.sleep(2000);
                reqThread[i].start();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                countDownLatch.countDown();
            }*/

            service.execute(() -> {
                try {
                    //AtomicLimiter limiter = new AtomicLimiter();
                    SemaphoreLimiter limiter = new SemaphoreLimiter();
                    Thread.sleep(0L);
                    log.info("{}开始请求", Thread.currentThread().getName());
                    limiter.request();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    countDownLatch.countDown();
                }
            });
        }
        try {
            countDownLatch.await();
            service.shutdown();
            log.info("结束");
        } catch (InterruptedException e) {
            log.error("InterruptedException");
        }

    }

    static final class ReqThread extends Thread {
        @Override
        public void run() {
            // AtomicLimiter limiter = new AtomicLimiter();
            SemaphoreLimiter limiter = new SemaphoreLimiter();
            log.info("{}开始请求", Thread.currentThread().getName());
            limiter.request();
        }
    }
}
