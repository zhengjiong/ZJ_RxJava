package com.example.observable;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;

/**
 * Created by zhengjiong on 16/4/26.
 */
public class Example6 {

    /**
     * 输出结果:
     * Observer onNext 1
     * Observer onNext 2
     * Observer -> onCompleted
     *
     */
    public static void main(String[] args){
        Observable<Integer> observable = Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                subscriber.onNext(1);
                subscriber.onNext(2);
                subscriber.onCompleted();
            }
        });

        observable.subscribe(new Observer<Integer>() {
            @Override
            public void onCompleted() {
                System.out.println("Observer -> onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                System.out.println("Observer -> onError");
            }

            @Override
            public void onNext(Integer i) {
                System.out.println("Observer onNext " + i);
            }
        });
    }
}
