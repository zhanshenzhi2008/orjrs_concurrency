package com.orjrs.concurrency.action.singleton;

import com.orjrs.concurrency.annoations.Recommend;
import com.orjrs.concurrency.annoations.ThreadSafe;

/**
 * 饿汉模式：
 * 单例实例在类装载时进行创建
 *
 * @author orjrs
 * @date 2018-05-1317:08
 */
@ThreadSafe
public class SingletonExample6 {
    private SingletonExample6() {

    }

    // 单例对象
    private static SingletonExample6 instance = null;
    static {
        instance = new SingletonExample6();

    }
    // 注意静态的顺序，换了上下这行代码的顺序，发现会有不同的结果
    static {
        instance = new SingletonExample6();

    }

    // 静态的工厂方法
    public static SingletonExample6 getInstance() {
        return instance;
    }

    public static void main(String[] args) {
        System.out.print(getInstance().hashCode());
        System.out.print(getInstance().hashCode());
    }
}
