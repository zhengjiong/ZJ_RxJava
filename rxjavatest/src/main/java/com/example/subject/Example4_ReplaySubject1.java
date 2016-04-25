package com.example.subject;

import java.util.concurrent.TimeUnit;

import rx.Observer;
import rx.Scheduler;
import rx.Subscriber;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import rx.subjects.ReplaySubject;

/**
 * ReplaySubject会缓存它所订阅的所有数据,向任意一个订阅它的观察者重发数据
 *
 * ReplaySubject 是用来缓存所有推送给它的数据值，当有一个新的订阅者，
 * 那么就会为这个新的订阅者从头开始播放原来的一系列事件。当再有新的事件来时，所有的订阅者也会接受到的。
 *
 * Created by zhengjiong on 16/4/25.
 */
public class Example4_ReplaySubject1 {


    public static void main(String[] args) throws InterruptedException {
        //test1();
        //test2();
        test3();
    }

    /**
     * 输出结果:
     * Action1 call -> 1
     * Action1 call -> 2
     * Observer onNext ->1
     * Observer onNext ->2
     * Subscriber onNext -> 1
     * Subscriber onNext -> 2
     * Action1 call -> 9
     * Observer onNext ->9
     * Subscriber onNext -> 9
     */
    private static void test1() {
        ReplaySubject<Integer> replaySubject = ReplaySubject.create();
        //在观察者被注册前,先发送两个数据,此时ReplaySubject将会缓存这两条数据
        replaySubject.onNext(1);
        replaySubject.onNext(2);

        //ReplaySubject会缓存所有onNext给他的数据,并在注册一个观察者的时候重新发送给他
        replaySubject.subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer i) {
                System.out.println("Action1 call -> " + i);
            }
        });
        replaySubject.subscribe(new Observer<Integer>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Integer integer) {
                System.out.println("Observer onNext ->" + integer);
            }
        });
        replaySubject.subscribe(new Subscriber<Integer>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Integer integer) {
                System.out.println("Subscriber onNext -> " + integer);
            }
        });

        replaySubject.onNext(9);//最后再发送一个数据9,这个时候3个订阅者都会接收到这个事件
    }

    /**
     * ReplaySubject.createWithSize是用来限制缓存大小，而ReplaySubject.createWithTime限制一个对象会被缓存多长时间
     *
     * 输出结果:
     * Action1 call -> 2
     * Action1 call -> 3
     */
    private static void test2() {
        //设置缓存大小为2, 就会只缓存最新的两条数据,2和3Action1
        ReplaySubject<Integer> replaySubject = ReplaySubject.createWithSize(2);
        replaySubject.onNext(1);
        replaySubject.onNext(2);
        replaySubject.onNext(3);

        replaySubject.subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {
                System.out.println("Action1 call -> " + integer);
            }
        });
    }

    /**
     * ReplaySubject.createWithTime限制一个对象会被缓存多长时间
     *
     * 输出结果:
     * Action1 -> 2
     */
    private static void test3() throws InterruptedException {
        //Schedulers.immediate();当前线程
        ReplaySubject<Integer> replaySubject = ReplaySubject.createWithTime(2, TimeUnit.SECONDS, Schedulers.immediate());

        replaySubject.onNext(1);
        Thread.sleep(3000);//设置延迟3秒执行,onNext(1),将不会发送给观察者
        replaySubject.onNext(2);

        replaySubject.subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {
                System.out.println("Action1 -> " + integer);
            }
        });
    }
}
