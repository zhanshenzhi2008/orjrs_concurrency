package com.orjrs.concurrency.action.singleton;

import com.orjrs.concurrency.annoations.ThreadSafe;
import com.orjrs.concurrency.annoations.UnRecommend;

/**
 * 线程安全的懒汉模式：
 * 单例实例在类装载时进行创建
 *
 * @author orjrs
 * @date 2018-05-1317:08
 */
@ThreadSafe
@UnRecommend
public class SingletonExample3 {
    private SingletonExample3() {

    }

    // 单例对象
    private static SingletonExample3 instance = null;

    // 静态的工厂方法
    public static synchronized SingletonExample3 getInstance() {
        if (instance == null) {
            instance = new SingletonExample3();
        }
        return instance;
    }
}
