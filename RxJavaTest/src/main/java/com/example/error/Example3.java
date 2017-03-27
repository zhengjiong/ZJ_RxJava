package com.example.error;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;

/**
 * Title: Example3
 * Description:
 * Copyright:Copyright(c)2016
 * Company: 博智维讯信息技术有限公司
 * CreateTime:17/3/27  16:17
 *
 * @author 郑炯
 * @version 1.0
 */
public class Example3 {

    public static void main(String[] args){
        Observer observer = new Observer() {
            @Override
            public void onCompleted() {
                System.out.println("onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                System.out.println("onError " + e.getMessage());
            }

            @Override
            public void onNext(Object o) {
                System.out.println("onNext " + o);
            }
        };

        Observable observable = Observable.create(new Observable.OnSubscribe<Object>() {
            @Override
            public void call(Subscriber<? super Object> subscriber) {
                subscriber.onNext(1);
                subscriber.onNext(2);
                subscriber.onNext(3);
                //e.onError(new RuntimeException("custom error"));
                subscriber.onCompleted();
                subscriber.onNext(4);
            }
        });

        observable.subscribe(observer);
    }
}
