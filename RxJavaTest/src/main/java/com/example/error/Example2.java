package com.example.error;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Title: Example2
 * Description:
 * Copyright:Copyright(c)2016
 * Company: 博智维讯信息技术有限公司
 * CreateTime:17/3/21  09:14
 *
 * @author 郑炯
 * @version 1.0
 */
public class Example2 {

    public static void main(String[] args) {
        Observable.just(1, 2, 3, 4, 5)
                .flatMap(new Func1<Integer, Observable<?>>() {
                    @Override
                    public Observable<?> call(Integer integer) {
                        return Observable.create(new Observable.OnSubscribe<Integer>() {
                            @Override
                            public void call(Subscriber<? super Integer> subscriber) {
                                if (integer == 4) {
                                    //subscriber.onError(new RuntimeException("4"));
                                    subscriber.onCompleted();
                                } else {
                                    subscriber.onNext(integer);
                                }
                                //subscriber.onCompleted();
                            }
                        });
                    }
                })
                .subscribe(new Action1<Object>() {
                    @Override
                    public void call(Object o) {
                        System.out.println("onNext=" + o.toString());
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        System.out.println("onError=" + throwable.getMessage());
                    }
                }, new Action0() {
                    @Override
                    public void call() {
                        System.out.println("onCompleted");
                    }
                });
    }
}
