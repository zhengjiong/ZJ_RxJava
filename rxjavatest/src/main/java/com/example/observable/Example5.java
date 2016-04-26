package com.example.observable;

import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func0;

/**
 * Created by zhengjiong on 16/4/26.
 */
public class Example5 {

    /**
     * subscribing.defer 对于每个新的订阅都会再创建返回一次(也就是Func0()中的call方法会每次订阅都执行一次)
     * 输出结果:
     *
     * Func0 -> call 1461648785127
     * 11111 Action1 -> call 1461648785127
     * Func0 -> call 1461648786133
     * 22222 Action1 -> call 1461648786133
     *
     */
    public static void main(String[] args) throws InterruptedException {
        Observable observable = Observable.defer(new Func0<Observable<Long>>() {
            @Override
            public Observable<Long> call() {
                System.out.println("Func0 -> call " + System.currentTimeMillis());
                return Observable.just(System.currentTimeMillis());
            }
        });
        observable.subscribe(new Action1<Long>() {
            @Override
            public void call(Long time) {
                System.out.println("11111 Action1 -> call " + time);
            }
        });

        Thread.sleep(1000);

        observable.subscribe(new Action1<Long>() {
            @Override
            public void call(Long time) {
                System.out.println("22222 Action1 -> call " + time);
            }
        });
    }
}
