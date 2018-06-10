package com.orjrs.concurrency.action.aqs;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Future
 *
 * @author orjrs
 * @date 2018-06-1016:54
 */
@Slf4j
public class FutureDemo {
    static class MyCallable implements Callable<String> {

        @Override
        public String call() throws Exception {
            log.info("do something");
            Thread.sleep(3000);
            return "done";
        }
    }

    public static void main(String[] args) throws Exception {
        ExecutorService executor = Executors.newCachedThreadPool();
        Future<String> future = executor.submit(new MyCallable());
        log.info("do something in main");
        Thread.sleep(1000);
        String result = future.get();
        log.info("result {}", result);

    }

}
