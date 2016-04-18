package com.example.hellorxjava;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;

/**
 * Created by zhengjiong on 16/4/18.
 */
public class Example2 {

    public static void main(String[] args){
        Observable<String> myObservable = Observable.just("Hello RxJava");

        Action1<String> action1 = new Action1<String>() {
            @Override
            public void call(String s) {
                System.out.println(s);
            }
        };

        myObservable.subscribe(action1);

    }
}
