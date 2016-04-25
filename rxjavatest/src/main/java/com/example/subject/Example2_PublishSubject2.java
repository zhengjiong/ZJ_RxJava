package com.example.subject;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.functions.Action0;
import rx.subjects.PublishSubject;

/**
 * Created by zhengjiong on 16/4/25.
 */
public class Example2_PublishSubject2 {

    final PublishSubject<String> mPublishSubject = PublishSubject.create();

    /**
     * 输出结果
     * Observable for i=0
     * Observable for i=1
     * Observable for i=2
     * Observable for i=3
     * Observable for i=4
     * Observable onCompleted
     * mPublishSubject onNext -> s=999
     */
    public static void main(String[] args) {
        Example2_PublishSubject2 example2 = new Example2_PublishSubject2();

        //给PublishSubject设置一个订阅监听
        example2.mPublishSubject.subscribe(new Observer<String>() {
            @Override
            public void onCompleted() {
                System.out.println("mPublishSubject onCompleted");
                 /*Observable.never();
                Observable.error();
                Observable.empty();*/
            }

            @Override
            public void onError(Throwable e) {
                System.out.println("mPublishSubject onError");
            }

            @Override
            public void onNext(String s) {
                System.out.println("mPublishSubject onNext -> s=" + s);
            }
        });

        Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                for (int i = 0; i < 5; i++) {
                    System.out.println("Observable for i=" + i);
                    subscriber.onNext(String.valueOf(i));
                }
                System.out.println("Observable onCompleted");
                subscriber.onCompleted();
            }
        })
        //doOnCompleted()方法指定当Observable结束时要做什么事情
        .doOnCompleted(new Action0() {
            @Override
            public void call() {
                //使用mPublishSubject来发出onNext事件,这样mPublishSubject就可以在call方法中处理该事件
                example2.mPublishSubject.onNext(String.valueOf(999));
            }
        })
        .subscribe();//这里没有订阅任何观测者,但是Observable.create的call方法还是会执行,但是不会有任何观察者处理这些事件
    }
}
