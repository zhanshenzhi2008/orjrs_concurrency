package com.orjrs.concurrency.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * ${todo}
 *
 * @author orjrs
 * @date 2018-06-1713:41
 */
@RestController
public class CacheController {
    @Autowired
    private RedisClient redisClient;

    @RequestMapping("/cache/redis/set")
    public String set(@RequestParam("K") String key, @RequestParam("V") String value) throws Exception {
        redisClient.set(key, value);
        return "SUCCESS";
    }

    @RequestMapping("/cache/redis/get")
    public String get(@RequestParam("K") String key) throws Exception {
        return redisClient.get(key);
    }
}
