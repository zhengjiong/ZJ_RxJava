package com.example.hellorxjava;


import rx.Observable;
import rx.Subscriber;

/**
 *
 * Created by zhengjiong on 16/4/18.
 */
public class Example1 {

    public static void main(String[] args){

        //事件源(被观察者)
        Observable<String> myObservable = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                System.out.println("myObservable call");
                subscriber.onNext("Hello RxJava");
                subscriber.onCompleted();
            }
        });

        //订阅者(观察者)
        Subscriber<String> mySubscriber = new Subscriber<String>() {
            @Override
            public void onCompleted() {
                System.out.println("mySubscriber onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                System.out.println("mySubscriber onError e=" + e.getMessage());
            }

            @Override
            public void onNext(String s) {
                System.out.println("mySubscriber onNext s=" + s);
            }
        };

        myObservable.subscribe(mySubscriber);
    }
}
