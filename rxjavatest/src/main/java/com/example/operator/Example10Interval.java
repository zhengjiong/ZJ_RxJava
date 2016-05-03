package com.example.operator;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.functions.Action1;

/**
 * Created by zhengjiong on 16/5/3.
 */
public class Example10Interval {

    public static void main(String[] args) throws IOException {
        test1();


    }

    /**
     * 创建一个无限的计时序列，每隔一段时间发射一个数字，从 0 开始
     *
     * 运行结果:
     *
     * Action1 -> call 0
     * Action1 -> call 1
     * Action1 -> call 2
     * Action1 -> call 3
     * Action1 -> call 4
     * Action1 -> call 5
     * Action1 -> call 6
     * Action1 -> call 7
     * Action1 -> call 8
     * Action1 -> call 9
     *
     */
    private static void test1() throws IOException {
        Observable.interval(500, TimeUnit.MILLISECONDS)
            .subscribe(new Action1<Long>() {
                @Override
                public void call(Long l) {
                    System.out.println("Action1 -> call " + l);
                }
            });

        System.in.read();//必须要加上这一句才会有输出
    }

}
