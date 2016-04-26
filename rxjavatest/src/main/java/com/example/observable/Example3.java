package com.example.observable;

import rx.Observable;
import rx.Observer;

/**
 * Created by zhengjiong on 16/4/26.
 */
public class Example3 {

    /**
     * Observable.never，它是从不发射任何东西,下面代码将什么也不会输出。
     * 但是并不意味着程序在这里堵塞等待了，而是直接中断了。
     */
    public static void main(String[] args){
        Observable observable = Observable.never();

        observable.subscribe(new Observer() {
            @Override
            public void onCompleted() {
                System.out.println("Observer -> onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                System.out.println("Observer -> onError");
            }

            @Override
            public void onNext(Object o) {
                System.out.println("Observer -> onNext");
            }
        });
    }
}
