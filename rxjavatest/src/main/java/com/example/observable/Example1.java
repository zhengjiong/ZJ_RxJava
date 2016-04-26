package com.example.observable;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;

/**
 * Created by zhengjiong on 16/4/26.
 */
public class Example1 {

    /**
     * 输出结果:
     *
     * Action1 -> call 1
     * Action1 -> call 2
     * Action1 onCompleted
     */
    public static void main(String[] args){

        //Observable.just是创建一个Observable实例，它会发送一个预先定义好的数值序列,最后也会执行onCompleted操作
        Observable<Integer> observable = Observable.just(1, 2);

        observable.subscribe(new Subscriber<Integer>() {
            @Override
            public void onCompleted() {
                System.out.println("Action1 onCompleted");
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Integer integer) {
                System.out.println("Action1 onNext -> " + integer);
            }
        });
    }
}
