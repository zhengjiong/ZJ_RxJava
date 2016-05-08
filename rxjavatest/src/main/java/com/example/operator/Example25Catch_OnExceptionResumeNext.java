package com.example.operator;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;

/**
 * onExceptionResumeNext:
 *
 * 让Observable在遇到异常(不是错误)时继续发射后面的数据项。
 *
 * onExceptionResumeNext操作符和onErrorResumeNext操作符类似，不同的地方在于onErrorResumeNext操作符是当Observable发生错误或异常时触发，而onExceptionResumeNext是当Observable发生异常时才触发。

 * 这里要普及一个概念就是，java的异常分为错误（error）和异常（exception）两种，它们都是继承于Throwable类。

 * 错误（error）:一般是比较严重的系统问题，比如我们经常遇到的OutOfMemoryError、StackOverflowError等都是错误。
 * 错误一般继承于Error类，而Error类又继承于Throwable类，如果需要捕获错误，需要使用try..catch(Error e)或
 * 者try..catch(Throwable e)句式。使用try..catch(Exception e)句式无法捕获错误

 * 异常（Exception）:也是继承于Throwable类，一般是根据实际处理业务抛出的异常，分为运行时异常
 * （RuntimeException）和普通异常。普通异常直接继承于Exception类，如果方法内部没有通过try..catch句式进行处理，
 * 必须通过throws关键字把异常抛出外部进行处理（即checked异常）；而运行时异常继承于RuntimeException类，
 * 如果方法内部没有通过try..catch句式进行处理，不需要显式通过throws关键字抛出外部，
 * 如IndexOutOfBoundsException、NullPointerException、ClassCastException等都是运行时异常，
 * 当然RuntimeException也是继承于Exception类，因此是可以通过try..catch(Exception e)句式进行捕获处理的。
 *
 * Created by zhengjiong on 16/5/8.
 */
public class Example25Catch_OnExceptionResumeNext {

    /**
     * 运行结果:
     *
     * Observer -> onNext s=0
     * Observer -> onNext s=onExceptionResumeNext
     * Observer -> onCompleted
     */
    public static void main(String[] args){
        Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                for (int i=0;i<10;i++) {
                    if (i == 1) {
                        subscriber.onError(new Exception("err-1"));
                    } else {
                        subscriber.onNext(String.valueOf(i));
                    }
                }
            }
        })
        .onExceptionResumeNext(Observable.just("onExceptionResumeNext"))
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
