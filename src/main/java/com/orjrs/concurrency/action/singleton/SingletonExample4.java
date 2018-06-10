package com.orjrs.concurrency.action.singleton;

import com.orjrs.concurrency.annoations.ThreadSafe;
import com.orjrs.concurrency.annoations.UnRecommend;

/**
 * 双重检测机制的懒汉模式：
 * 单例实例在类装载时进行创建
 *
 * @author orjrs
 * @date 2018-05-1317:08
 */
@ThreadSafe
@UnRecommend
public class SingletonExample4 {
    private SingletonExample4() {

    }
    // CPU指令：
    // 1. memory =allocate() 分配对象的内存空间
    // 2. ctorInstance() 初始化对象
    // 3. instance= memory 设置instance指向共分配的内存

    // JVM和CPU优化，发生了指令重排序
    // 2、3步骤可能会发生变化

    // 单例对象
    private static SingletonExample4 instance = null;

    // 静态的工厂方法
    public static synchronized SingletonExample4 getInstance() {
        if (instance == null) {// 双重检测机制
            synchronized (SingletonExample4.class) { // 同步锁
                if (instance == null) {
                    instance = new SingletonExample4();
                }
            }
        }

        return instance;
    }
}
