package com.example.operator;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.functions.Func1;

/**
 *
 * onErrorResumeNext:
 *
 * 让Observable在遇到错误时开始发射第二个Observable的数据序列。
 *
 * onErrorResumeNext操作符跟onErrorReturn类似，只不过onErrorReturn只能在错误或异常发
 * 生时只返回一个和源Observable相同类型的结果，而onErrorResumeNext操作符是在错误或异常发
 * 生时返回一个Observable，也就是说可以返回多个和源Observable相同类型的结果
 *
 * Created by zhengjiong on 16/5/8.
 */
public class Example24Catch_OnErrorResumeNext {

    /**
     * 运行结果:
     *
     * Observer -> onNext s=0
     * Observer -> onNext s=onErrorResumeNext
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
        .map(new Func1<Integer, String>() {
            @Override
            public String call(Integer i) {
                return String.valueOf(i);
            }
        })
        //当i==1的时候,会触发这里的Observable.just发射数据, 然后onCompleted正常结束
        //和onErrorReturn区别是onErrorResumeNext可以返回Observable
        .onErrorResumeNext(Observable.just("onErrorResumeNext"))
        .subscribe(new Observer<String>() {

            @Override
            public void onCompleted() {
                System.out.println("Observer -> onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                System.out.println("Observer -> onError e=" + e.getMessage());
            }

            @Override
            public void onNext(String s) {
                System.out.println("Observer -> onNext s=" + s);
            }

        });

    }
}
