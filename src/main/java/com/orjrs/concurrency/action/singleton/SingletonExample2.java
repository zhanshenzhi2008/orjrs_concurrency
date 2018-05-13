package com.orjrs.concurrency.action.singleton;

import com.orjrs.concurrency.annoations.ThreadSafe;

/**
 * 饿汉模式：
 * 单例实例在类装载时进行创建
 *
 * @author orjrs
 * @date 2018-05-1317:08
 */
@ThreadSafe
public class SingletonExample2 {
    private SingletonExample2() {

    }

    // 单例对象
    private static SingletonExample2 instance = new SingletonExample2();

    // 静态的工厂方法
    public static SingletonExample2 getInstance() {
        return instance;
    }
}
