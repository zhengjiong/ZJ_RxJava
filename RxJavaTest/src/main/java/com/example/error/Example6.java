package com.example.error;

import java.util.NoSuchElementException;

import rx.Observable;
import rx.Subscriber;
import rx.observers.SafeSubscriber;

/**
 * Title: Example6
 * Description:
 * Copyright:Copyright(c)2016
 * Company: 博智维讯信息技术有限公司
 * CreateTime:17/4/2  18:21
 *
 * @author 郑炯
 * @version 1.0
 */
public class Example6 {

    public static void main(String[] args){
        //test1();
        test2();
    }

    private static void test2() {
        Observable.just(1).subscribe(new SafeSubscriber<Integer>(new Subscriber<Integer>() {
            @Override
            public void onCompleted() {
                System.out.println("onCompleted");
                throw new NoSuchElementException();
            }

            @Override
            public void onError(Throwable e) {
                System.out.println("onError " + e.toString());
                if (e instanceof IllegalArgumentException) {
                    throw new UnsupportedOperationException();
                }
            }

            @Override
            public void onNext(Integer t) {
                System.out.println("onNext t=" + t);
                if (t == 1) {
                    throw new IllegalArgumentException();
                }
            }
        }));
    }

    private static void test1() {
        Observable.just(1).subscribe(new Subscriber<Integer>() {
            @Override
            public void onCompleted() {
                System.out.println("onCompleted");
                throw new NoSuchElementException();
            }

            @Override
            public void onError(Throwable e) {
                System.out.println("onError " + e.toString());
                if (e instanceof IllegalArgumentException) {
                    throw new UnsupportedOperationException();
                }
            }

            @Override
            public void onNext(Integer t) {
                System.out.println("onNext t=" + t);
                if (t == 1) {
                    throw new IllegalArgumentException();
                }
            }
        });
    }
}
