package com.example.error;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Action0;

/**
 * Title: Example4
 * Description:
 * Copyright:Copyright(c)2016
 * Company: 博智维讯信息技术有限公司
 * CreateTime:17/3/28  08:32
 *
 * @author 郑炯
 * @version 1.0
 */
public class Example4 {

    private static Subscription subscription;

    public static void main(String[] args) {
        Observable<Integer> observable = Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                subscriber.onNext(1);
                subscriber.onNext(2);
                subscription.unsubscribe();
                subscriber.onNext(3);
                subscriber.onCompleted();
            }
        });

        subscription = observable.doOnUnsubscribe(new Action0() {
            @Override
            public void call() {
                System.out.println("doOnUnsubscribe");
            }
        }).subscribe(new Subscriber<Integer>() {
            @Override
            public void onCompleted() {
                System.out.println("onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                System.out.println("onError " + e.toString());
            }

            @Override
            public void onNext(Integer integer) {
                System.out.println("onNext " + integer);
            }
        });

    }
}
