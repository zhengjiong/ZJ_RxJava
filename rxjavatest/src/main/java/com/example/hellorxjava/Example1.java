package com.example.hellorxjava;


import rx.Observable;
import rx.Observer;
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
                //subscriber.add();
            }
        });

        //订阅者(观察者)
        /*Subscriber<String> mySubscriber = new Subscriber<String>() {
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
        };*/

        /**
         * Observer是RxJava的观察者，Subscriber 是其子类，另外增加了一些额外功能，应该算是观察者的基本实现
         */
        /*myObservable.subscribe(new Observer<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {

            }
        });*/

        /**
         * Observer是RxJava的观察者，Subscriber 是其子类，另外增加了一些额外功能，应该算是观察者的基本实现
         */
        myObservable.subscribe(new Subscriber<String>() {

            /*
             * 订阅者(观察者)
             */

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
        });
    }
}
