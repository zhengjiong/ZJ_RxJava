package com.example.test;

import com.google.common.base.Predicate;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.functions.Action1;
import rx.subjects.PublishSubject;

/**
 * Created by zhengjiong on 16/5/22.
 */
public class Test4Subscriber {

    public static void main(String[] args){
        Subscriber<String> subscriber = new Subscriber<String>() {
            @Override
            public void onCompleted() {
                System.out.println("subscriber onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                System.out.println("subscriber onError");
            }

            @Override
            public void onNext(String s) {
                System.out.println("subscriber onNext " + s);
            }
        };

        Observable.just("2").subscribe(subscriber);
        subscriber.onNext("3");
        subscriber.unsubscribe();

        Observer<String> observer = new Observer<String>() {
            @Override
            public void onCompleted() {
                System.out.println("observer onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                System.out.println("observer onError");
            }

            @Override
            public void onNext(String s) {
                System.out.println("observer onNext " + s);
            }
        };

        PublishSubject<String> publishSubject = PublishSubject.create();


        Observable.just("1")
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        System.out.println("s=" + s);
                        subscriber.onNext(s);
                        observer.onNext(s);
                    }
                });

        Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {

            }
        });
    }

}
