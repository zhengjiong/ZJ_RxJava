package com.example.test;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;

/**
 * Created by zhengjiong on 16/5/3.
 */
public class Test1Timer {

    public static void main(String[] args){
        test1();
        //test2();
    }

    /**
     * 会直接输出结果, 不会延迟3秒后输出,想要延迟看test2()
     * 执行结果:
     *
     * OnSubscribe -> call
     */
    private static void test1() {
        Observable.timer(2, TimeUnit.SECONDS).create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                System.out.println("OnSubscribe -> call");
            }
        }).subscribe();

        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 执行结果:
     *
     * OnSubscribe -> call
     */
    private static void test2() {
        Observable.timer(2, TimeUnit.SECONDS).map(new Func1<Long, String>() {
            @Override
            public String call(Long l) {
                System.out.println("Func1 call " + l);
                return null;
            }
        }).subscribe();

        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
