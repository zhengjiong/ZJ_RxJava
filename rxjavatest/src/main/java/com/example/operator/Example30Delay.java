package com.example.operator;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Observer;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * 用法1:
 * 第一种delay接受一个定义时长的参数（包括数量和单位）。每当原始Observable发射一项数据，delay就启动一个定时器，
 * 当定时器过了给定的时间段时，delay返回的Observable发射相同的数据项。
 * 注意：delay不会平移onError通知，它会立即将这个通知传递给订阅者，同时丢弃任何待发射的onNext通知。
 * 然而它会平移一个onCompleted通知。
 * delay默认在computation调度器上执行，你可以通过参数指定使用其它的调度器。
 *
 *
 * 用法@:
 * 另一种delay不实用常数延时参数，它使用一个函数针对原始Observable的每一项数据返回一个Observable，
 * 它监视返回的这个Observable，当任何那样的Observable终止时，delay返回的Observable就发射关联的那项数据。
 * 这种delay默认不在任何特定的调度器上执行。
 *
 * Created by zhengjiong on 16/5/9.
 */
public class Example30Delay {

    /**
     * 运行结果:
     *
     * onNext 1
     * onNext 2
     * onNext 3
     * onCompleted
     */
    public static void main(String[] args){
        //test1();
        test2();

    }

    /**
     *
     *
     * onNext 1
     * onNext 2
     * onNext 3
     * onCompleted
     */
    private static void test2() {
        Observable.just(1, 2, 3)
            .delay(new Func1<Integer, Observable<Object>>() {
                @Override
                public Observable<Object> call(Integer integer) {
                    return Observable.just(integer + 100);
                }
            })
            .subscribe(new Observer<Integer>() {
                @Override
                public void onCompleted() {
                    System.out.println("onCompleted");
                }

                @Override
                public void onError(Throwable e) {

                }

                @Override
                public void onNext(Integer integer) {
                    System.out.println("onNext " + integer);

                }
            });
    }

    private static void test1() {
        Observable.just(1, 2, 3)
            .delay(1, TimeUnit.SECONDS, Schedulers.immediate())
            .subscribe(new Observer<Integer>() {
                @Override
                public void onCompleted() {
                    System.out.println("onCompleted");
                }

                @Override
                public void onError(Throwable e) {

                }

                @Override
                public void onNext(Integer integer) {
                    System.out.println("onNext " + integer);

                }
            });
    }
}
