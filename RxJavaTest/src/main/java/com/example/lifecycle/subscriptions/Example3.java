package com.example.lifecycle.subscriptions;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.subscriptions.CompositeSubscription;

/**
 * Title: Example3
 * Description:
 * Copyright:Copyright(c)2016
 * CreateTime:17/4/3  11:47
 *
 * @author 郑炯
 * @version 1.0
 */
public class Example3 {
    static CompositeSubscription compositeSubscription = new CompositeSubscription();

    public static void main(String[] args){
        compositeSubscription.add(Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                subscriber.onNext(1);
                subscriber.onNext(2);
                subscriber.onNext(3);
                subscriber.onCompleted();
            }
        }).subscribe(new Observer<Integer>() {
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
                if (integer == 1) {
                    /**
                     * 这样写是有问题的, 因为这个时候compositeSubscription中的size还是0, 根本清楚不了
                     */
                    compositeSubscription.clear();
                }
            }
        }));
    }

}
