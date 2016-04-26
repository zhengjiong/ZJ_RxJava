package com.example.observable;

import rx.Observable;
import rx.Observer;

/**
 * Created by zhengjiong on 16/4/26.
 */
public class Example2 {

    /**
     * Observable.empty，它是创建一个可观察者，只发射onCompleted ，其他什么也不发送
     *
     * 输出结果:
     * Observer -> onCompleted
     *
     */
    public static void main(String[] args) {


        Observable emptyObservable = Observable.empty();

        emptyObservable.subscribe(new Observer() {
            @Override
            public void onCompleted() {
                System.out.println("Observer -> onCompleted");
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Object o) {
                System.out.println("Observer -> onNext " + o.toString());
            }
        });
    }
}
