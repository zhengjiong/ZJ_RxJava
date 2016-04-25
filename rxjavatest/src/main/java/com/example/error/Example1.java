package com.example.error;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;

/**
 * observable中出现报错后, 会回调观察者(Subscriber)的onError方法
 * Created by zhengjiong on 16/4/24.
 */
public class Example1 {

    public static void main(String[] args){
        Observable.just("hello rxJava")
            .map(new Func1<String, String>() {
                @Override
                public String call(String s) {
                    if (s.equals("hello rxJava")) {
                        Observable.error(new NumberFormatException("number format exception"));
                        //也可以用throw Exception
                        //throw new NumberFormatException("number format exception");
                    }
                    return s + " -zj";
                }
            })
            .subscribe(new Subscriber<String>() {

                @Override
                public void onCompleted() {
                    System.out.println("onCompleted");
                }

                @Override
                public void onError(Throwable e) {
                    System.out.println("onError e=" + e.getMessage());
                }

                @Override
                public void onNext(String s) {
                    System.out.println("onNext s=" + s);
                }
            });
    }
}
