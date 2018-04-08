package com.orjrs.concurrency.action;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试并发
 *
 * @author orjrs
 * @date 2018-04-0721:50
 */
@RestController
@Slf4j
public class TestController {
    @PostMapping("/test")
    public String test() {
        log.info("===test");
        return "Test !";
    }
}
