package com.example.test;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Created by zhengjiong on 16/7/24.
 */
public class Test6OnComplete {

    public static void main(String[] args){
        Observable<String> o1 = Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                System.out.println("11111");
                //Observable.error(new NullPointerException("zzz"));
                //subscriber.onError(new NullPointerException("zzz"));
                System.out.println("22222");
                subscriber.onNext(1);
                System.out.println("-----------");
                subscriber.onCompleted();
                subscriber.onError(new NullPointerException("aaa"));
                System.out.println("33333");
                subscriber.onCompleted();
                System.out.println("44444");
                subscriber.onNext(2);
            }
        })
        //.onExceptionResumeNext(Observable.just(9))
        .flatMap(new Func1<Integer, Observable<String>>() {
            @Override
            public Observable<String> call(Integer i) {
                System.out.println("flatMap i=" + i);
                return Observable.just(String.valueOf(i));
            }
        })
        .onExceptionResumeNext(Observable.just("onExceptionResumeNext"))

        //doOnNext()的执行在onNext()之前，对数据进行相关处理
        //doOnNext并不是在onnext前执行,如果把doOnNext写到distinct前面就会打印出其中重复的数据,
        //doOnNext相当于就是执行到哪就会取出哪的数据
        //最重要的一点是:这次操作会触发Subscriber中的onNext方法, 那doOnNext才会执行, 如果之前
        //出现过异常,就不会执行onNext方法, doOnNext也就不会执行,
        //所以只要能执行onNext, 那doOnNext就能执行, 如果不能执行
        //onNext那doOnNext也不能执行
        .doOnNext(new Action1<String>() {
            @Override
            public void call(String s) {
                System.out.println("doOnNext s=" + s);
            }
        });

        o1.subscribe(new Subscriber<String>() {
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
