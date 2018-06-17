package com.orjrs.concurrency.action.deadlock;

import lombok.extern.slf4j.Slf4j;

/**
 * 死锁
 *
 * @author orjrs
 * @date 2018-06-1121:47
 */
@Slf4j
public class DeadLock implements Runnable {
    public int flag = 1;
    //静态对象是类的所有对象共享的, 如果不用static声明，则不会死锁
    private static Object a = new Object();
    private static Object b = new Object();


    public static void main(String[] args) {
        DeadLock dl1 = new DeadLock();
        DeadLock dl2 = new DeadLock();
        dl1.flag = 1;
        dl2.flag = 0;
        //td1,td2都处于可执行状态，但JVM线程调度先执行哪个线程是不确定的。
        //td2的run()可能在td1的run()之前运行
        new Thread(dl1).start();
        new Thread(dl2).start();
    }

    @Override
    public void run() {
        log.info("flag:{}", flag);
        if (flag == 1) {
            synchronized (a) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (b) {
                    log.info("1");
                }
            }
        }

        if (flag == 0) {
            synchronized (b) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (a) {
                    log.info("2");
                }
            }
        }
    }
}
