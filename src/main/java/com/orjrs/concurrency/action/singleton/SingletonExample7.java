package com.orjrs.concurrency.action.singleton;

import com.orjrs.concurrency.annoations.Recommend;
import com.orjrs.concurrency.annoations.ThreadSafe;

/**
 * 枚举模式
 *
 * @author orjrs
 * @date 2018-05-1317:08
 */
@ThreadSafe
@Recommend
public class SingletonExample7 {
    private SingletonExample7() {

    }

    // 静态的工厂方法
    public static SingletonExample7 getInstance() {
        return Singleton.INSTANCE.getInstance();
    }

    private enum Singleton {
        INSTANCE;

        private SingletonExample7 singleton;

        // JVM保证这个方法绝对只调用一次
        Singleton() {
            singleton = new SingletonExample7();
        }

        public SingletonExample7 getInstance() {
            return singleton;
        }
    }
}
