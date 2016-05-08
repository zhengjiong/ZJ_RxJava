package com.example.operator;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.functions.Func1;

/**
 *
 * onErrorReturn:
 *
 * 让Observable遇到错误时发射一个特殊的项并且正常终止。
 *
 * Created by zhengjiong on 16/5/8.
 */
public class Example23Catch_OnErrorReturn {


    /**
     * 运行结果:
     *
     * Observer -> onNext i=0
     * onErrorReturn -> throwable=err-1
     * Observer -> onNext i=999
     * Observer -> onCompleted
     */
    public static void main(String[] args){
        Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                for (int i=0;i<10;i++) {
                    if (i == 1) {
                        subscriber.onError(new Exception("err-1"));
                    } else {
                        subscriber.onNext(i);
                    }
                }
            }
        })
        //遇到错误时发射一个特殊的项并且正常终止, 会执行onCompleted
        .onErrorReturn(new Func1<Throwable, Integer>() {
            @Override
            public Integer call(Throwable throwable) {
                System.out.println("onErrorReturn -> throwable=" + throwable.getMessage());
                return 999;
            }
        })
        .subscribe(new Observer<Integer>() {
            @Override
            public void onCompleted() {
                System.out.println("Observer -> onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                System.out.println("Observer -> onError e=" + e.getMessage());
            }

            @Override
            public void onNext(Integer i) {
                System.out.println("Observer -> onNext i=" + i);
            }
        });
    }
}
