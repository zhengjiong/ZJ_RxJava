package com.example.hellorxjava;

import rx.Observable;
import rx.functions.Action1;

/**
 * Created by zhengjiong on 16/4/18.
 */
public class Example3 {

    public static void main(String[] args){
        Observable.just("Hello RxJava")
            .subscribe(new Action1<String>() {
                @Override
                public void call(String s) {
                    System.out.println(s);
                }
        });
    }
}
