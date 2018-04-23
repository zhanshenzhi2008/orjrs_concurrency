package com.orjrs.concurrency.action;

import com.orjrs.concurrency.annoations.UnThreadSafe;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * 并发测试
 *
 * @author orjrs
 * @date 2018-04-0822:00
 */
@Slf4j
@UnThreadSafe
public class ConcurrencyTest {
    /** 请求总数 */
    private static int clientTotal = 5000;

    /** 同时并发数 */
    private static int threadTotal = 5000;
    /***/
    private static int count = 0;

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newCachedThreadPool();
        final Semaphore semaphore = new Semaphore(threadTotal); // permits 初始许可数，也就是最大访问线程数,
        final CountDownLatch countDownLatch = new CountDownLatch(clientTotal); // 术语:闭包.允许一个或多个线程等待其他线程完成操作。
        try {
            for (int i = 0; i < clientTotal; i++) {
                executorService.execute(() -> {
                    add();
                    semaphore.release();// 退出信号量并返回前一个计数，初始permits 为1， 调用了两次release，最大许可会改变为2。所以最好放到finally里
                    countDownLatch.countDown();// 当我们调用一次CountDownLatch的countDown方法时，N就会减1
                });
            }
            semaphore.acquire(); // 从信号量获取一个许可，如果无可用许可前 将一直阻塞等待，
        } catch (InterruptedException e) {
            log.info("异常", e);
        }
        countDownLatch.await(); // 计数器必须大于等于0，只是等于0时候，计数器就是零，调用await方法时不会阻塞当前线程 ，类似线程的join
        executorService.shutdown(); //平滑的关闭ExecutorService，当此方法被调用时，ExecutorService停止接收新的任务并且等待已经提交的任务（包含提交正在执行和提交未执行）执行完成。当所有提交任务执行完毕，线程池即被关闭。
        log.info("count:{}", count);
    }

    private static void add() {
        count++;
    }
}
