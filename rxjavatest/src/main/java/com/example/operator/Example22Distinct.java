package com.example.operator;

import rx.Observable;
import rx.functions.Action1;

/**
 *
 *
 *
 * Created by zhengjiong on 16/5/8.
 */
public class Example22Distinct {

    public static void main(String[] args) {
        test1();
    }

    /**
     * 抑制（过滤掉）重复的数据项
     * Distinct的过滤规则是：只允许还没有发射过的数据项通过。
     *
     * 在某些实现中，有一些变体允许你调整判定两个数据不同(distinct)的标准。
     * 还有一些实现只比较一项数据和它的直接前驱，因此只会从序列中过滤掉连续重复的数据。
     */
    private static void test1() {
        Observable.just(1, 1,2,2, 3, 4, 5)
                .distinct()
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer i) {
                        System.out.println("Action1 -> call " + i);
                    }
                });
    }
}
