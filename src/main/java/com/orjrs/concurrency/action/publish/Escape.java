package com.orjrs.concurrency.action.publish;

import com.orjrs.concurrency.annoations.UnRecommend;
import com.orjrs.concurrency.annoations.UnThreadSafe;
import lombok.extern.slf4j.Slf4j;

/**
 * 对象逃逸：一个错误的发布。当一个对象还没有构造完成时，就使它被其他线程所见
 *
 * @author orjrs
 * @date 2018-05-1316:57
 */
@Slf4j
@UnThreadSafe
@UnRecommend
public class Escape {
    private int thisCanBeEscape = 0;

    public Escape() {
        new InnerClass();
    }

    private class InnerClass {
        public InnerClass() {
            log.info("{}", Escape.this.thisCanBeEscape);
        }
    }

    public static void main(String[] args) {
        new Escape();
    }
}
