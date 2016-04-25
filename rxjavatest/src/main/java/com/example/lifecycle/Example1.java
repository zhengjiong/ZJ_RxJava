package com.example.lifecycle;

import javax.security.auth.Subject;

import rx.Subscription;
import rx.functions.Action1;
import rx.subjects.BehaviorSubject;
import rx.subjects.PublishSubject;

/**
 * 取消订阅
 *
 * boolean isUnsubscribed()
 * void unsubscribe()
 *
 *
 * Created by zhengjiong on 16/4/25.
 */
public class Example1 {

    /**
     * 输出结果:
     *
     * Action1 call -> 1
     * Action1 call -> 2
     *
     */
    public static void main(String[] args){
        PublishSubject<Integer> behaviorSubject = PublishSubject.create();

        Subscription subscription = behaviorSubject.subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {
                System.out.println("Action1 call -> " + integer);
            }
        });

        behaviorSubject.onNext(1);
        behaviorSubject.onNext(2);
        subscription.unsubscribe();//取消订阅,取消后不会再收到onNext(3), 一个观察者取消订阅并不影响对同一个被观察者的其他观察者的订阅接受情况。
        behaviorSubject.onNext(3);
    }
}
