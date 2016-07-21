package com.example.hellorxjava;


import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Action0;
import rx.subscriptions.Subscriptions;

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
                System.out.println("Hello1 onNext");
                subscriber.onNext("Hello1 RxJava");
                System.out.println("Hello2 onNext");
                subscriber.onNext("Hello2 RxJava");
                subscriber.onCompleted();
                //subscriber.onError(new NullPointerException());

                /**
                 * 这段代码是给Subscription(观察者)增加一个unsubscribe的回调事件。 也就是onCompleted或onError执行完成后，会自动对call进行一个终止。
                 * 如果不执行onCompleted或者onError是不会条用Action0中的方法的,必须要有unsbuscribe事件,如onCompleted onError,或者手动执行Subscription.unsubscribe
                 */
                subscriber.add(Subscriptions.create(new Action0() {
                    @Override
                    public void call() {
                        System.out.println("Subscriptions.create Action0 -> call");
                    }
                }));

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
        Subscription subscription = myObservable.subscribe(new Subscriber<String>() {

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
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                System.out.println("mySubscriber onNext s=" + s);
            }
        });

        //subscription.unsubscribe();//执行后会执行上面Action0中的方法
    }
}
