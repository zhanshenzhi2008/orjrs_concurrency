package com.orjrs.concurrency.action.singleton;

import com.orjrs.concurrency.annoations.UnThreadSafe;
import sun.security.jca.GetInstance;

/**
 * 懒汉模式：
 * 单例实例在第一次使用时初始化
 *
 * @author orjrs
 * @date 2018-05-1317:08
 */
@UnThreadSafe
public class SingletonExample1 {
    private SingletonExample1() {

    }

    // 单例对象
    private static SingletonExample1 instance = null;

    // 静态的工厂方法
    public static SingletonExample1 getInstance() {
        if (instance == null) {
            instance = new SingletonExample1();
        }
        return instance;
    }
}
