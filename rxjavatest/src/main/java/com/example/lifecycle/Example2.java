package com.example.lifecycle;

import rx.Observer;
import rx.subjects.PublishSubject;

/**
 * onError 和 onCompleted
 * 这两个方法意味着中断一个事件系列，可观察者（被观察者）一旦在这两个方法以后就不再发射任何事件，尽管你的代码可能还会有发送事件
 * <p>
 * <p>
 * Created by zhengjiong on 16/4/25.
 */
public class Example2 {


    public static void main(String[] args){
        //testOnCompleted();
        testOnError();
    }

    /**
     * 结果输出:
     * Observer -> onNext 1
     * Observer -> onCompleted
     *
     * 没有输出onNext(2),是因为已经执行了onCompleted方法
     */
    private static void testOnCompleted() {
        PublishSubject<Integer> publishSubject = PublishSubject.create();

        publishSubject.subscribe(new Observer<Integer>() {
            @Override
            public void onCompleted() {
                System.out.println("Observer -> onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                System.out.println("Observer -> onError e=" + e.getMessage());
            }

            @Override
            public void onNext(Integer integer) {
                System.out.println("Observer -> onNext " + integer);
            }
        });

        publishSubject.onNext(1);
        publishSubject.onCompleted();
        publishSubject.onNext(2);
    }

    /**
     * 结果输出:
     * Observer -> onNext 1
     * Observer -> onError e=custom exception
     *
     * 没有输出onNext(2),是因为已经执行了onError方法
     */
    private static void testOnError() {
        PublishSubject<Integer> publishSubject = PublishSubject.create();

        publishSubject.subscribe(new Observer<Integer>() {
            @Override
            public void onCompleted() {
                System.out.println("Observer -> onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                System.out.println("Observer -> onError e=" + e.getMessage());
            }

            @Override
            public void onNext(Integer integer) {
                System.out.println("Observer -> onNext " + integer);
            }
        });

        publishSubject.onNext(1);
        publishSubject.onError(new Exception("custom exception"));
        publishSubject.onNext(2);
    }
}
