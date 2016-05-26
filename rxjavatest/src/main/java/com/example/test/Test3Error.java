package com.example.test;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;

/**
 * Created by zhengjiong on 16/5/19.
 */
public class Test3Error {

    public static void main(String[] args) {


        /**
         * 运行结果:
         *
         * subscriber onNext
         * call -> onNexton Next
         * call -> Throwable=NullPointerException
         * subscriber onError
         *
         */
        Observable.create(new Observable.OnSubscribe<Object>() {
            @Override
            public void call(Subscriber<? super Object> subscriber) {
                System.out.println("subscriber onNext");        //step 1
                subscriber.onNext("onNext");                    //step 2

                System.out.println("subscriber onError");       //step 6
                subscriber.onError(new NumberFormatException("NumberFormatException"));
            }
        })
        .subscribe(new Action1<Object>() {
                       @Override
                       public void call(Object o) {
                           System.out.println("call -> onNext " + o.toString());    //step 3
                           throw new NullPointerException("NullPointerException");  //step 4
                       }
                   },
                new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        System.out.println("call -> Throwable=" + throwable.getMessage());  //step 5
                    }
                });
    }
}
