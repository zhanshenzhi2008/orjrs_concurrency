package com.orjrs.concurrency.action.aqs;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * CyclicBarrier 信号量
 *
 * @author orjrs
 * @date 2018-06-0521:49
 */

@Slf4j
public class CyclicBarrierDemo {
    /** 线程总数 */
    private final static int THREAD_COUNT = 20;

    //private static CyclicBarrier cyclicBarrier = new CyclicBarrier(5);
    private static CyclicBarrier cyclicBarrier = new CyclicBarrier(5, () -> {
        log.info("优先执行这里的runnable");
    });

    public static void main(String[] args) {
        ExecutorService exec = Executors.newCachedThreadPool();
        for (int i = 0; i < THREAD_COUNT; i++) {
            final int threadNum = i;
            exec.execute(() -> {
                try {
                    race(threadNum);
                } catch (Exception e) {
                    log.error("exception", e);
                }
            });
        }
    }

    public static void race(int threadNum) throws Exception {
        Thread.sleep(100);
        log.info("{}", threadNum);
        cyclicBarrier.await();
        log.info("{} is ready", threadNum);
    }
}
