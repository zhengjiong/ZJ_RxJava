package com.example.test;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;

/**
 * Created by zhengjiong on 16/5/26.
 */
public class Test5 {

    public static void main(String[] args) {

        Action1<Boolean> action1 = new Action1<Boolean>() {
            @Override
            public void call(Boolean results) {
                System.out.println("action1 = " + results);
                throw new NullPointerException("NullPointerException");
            }
        };

        Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext("1");
                Observable.just(true).subscribe(action1);
            }
        }).subscribe(new Subscriber<String>() {
            @Override
            public void onCompleted() {
                System.out.println("onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                System.out.println("onError " + e.getMessage());
            }

            @Override
            public void onNext(String s) {
                System.out.println("onNext " + s);
            }
        });
    }
}
