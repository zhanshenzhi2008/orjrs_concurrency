package com.orjrs.concurrency.action.aqs;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

/**
 * FutureTask
 *
 * @author orjrs
 * @date 2018-06-1017:05
 */
@Slf4j
public class FutureTaskDemo {
    public static void main(String[] args) throws Exception {
        FutureTask<String> future = new FutureTask<String>(new Callable() {

            @Override
            public Object call() throws Exception {
                log.info("do something");
                Thread.sleep(5000);
                return "done";
            }
        });
        new Thread(future).start();
        log.info("do something in main");
        Thread.sleep(1000);
        String result = future.get();
        log.info("result {}", result);

    }
}
