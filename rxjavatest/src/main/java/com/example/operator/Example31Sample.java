package com.example.operator;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Sample(别名throttleLast):定期发射Observable最近发射的数据项
 *
 * Sample操作符定时查看一个Observable，然后发射自上次采样以来它最近发射的数据。

 * 在某些实现中，有一个ThrottleFirst操作符的功能类似，但不是发射采样期间的最近的数据，而是发射在那段时间内的第一项数据。

 * RxJava将这个操作符实现为sample和throttleLast。

 * 注意：如果自上次采样以来，原始Observable没有发射任何数据，这个操作返回的Observable在那段时间内也不会发射任何数据。
 * Created by zhengjiong on 16/5/11.
 */
public class Example31Sample {

    //Sample demo 看android demo
    public static void main(String[] args){
        //test1();
    }

    private static void test1() {
        List<Integer> nums = Arrays.asList(1, 2, 3, 4);
        Observable.interval(1000, TimeUnit.MILLISECONDS, Schedulers.immediate())
                .flatMap(new Func1<Long, Observable<String>>() {
                    @Override
                    public Observable<String> call(Long i) {
                        System.out.println("flatMap -> i=" + i);
                        return Observable.just(String.valueOf(i));
                    }
                })
            .sample(1000, TimeUnit.MILLISECONDS, Schedulers.immediate())
            .subscribe(new Observer<String>() {
                @Override
                public void onCompleted() {
                    System.out.println("Observer -> onCompleted");
                }

                @Override
                public void onError(Throwable e) {

                }

                @Override
                public void onNext(String i) {
                    System.out.println("Observer -> onNext i=" + i);
                }
            });
    }
}
