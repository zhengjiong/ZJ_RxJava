package com.example.error;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;

/**
 * Title: Example5
 * Description:
 * Copyright:Copyright(c)2016
 * Company: 博智维讯信息技术有限公司
 * CreateTime:17/4/2  18:08
 *
 * @author 郑炯
 * @version 1.0
 */
public class Example5 {

    public static void main(String[] args){
        Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                subscriber.onNext(1);
                subscriber.onNext(2);
                subscriber.onNext(3);
                subscriber.onError(new RuntimeException("error-1"));
                subscriber.onError(new RuntimeException("error-2"));
                subscriber.onNext(4);
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
            }
        });
    }
}
