package com.example.subject;

import java.util.concurrent.TimeUnit;

import rx.Observer;
import rx.Subscriber;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import rx.subjects.ReplaySubject;
import rx.subjects.Subject;

/**
 * ReplaySubject会缓存它所订阅的所有数据,向任意一个订阅它的观察者重发数据
 *
 * ReplaySubject 是用来缓存所有推送给它的数据值，当有一个新的订阅者，
 * 那么就会为这个新的订阅者从头开始播放原来的一系列事件。当再有新的事件来时，所有的订阅者也会接受到的。
 *
 * Created by zhengjiong on 16/4/25.
 */
public class Example5_ReplaySubject2 {


    public static void main(String[] args) throws InterruptedException {
        test1();
    }

    /**
     * 输出结果:
     */
    private static void test1() {
        Subject<Integer, Integer> replaySubject = ReplaySubject.create();
        //在观察者被注册前,先发送两个数据,此时ReplaySubject将会缓存这两条数据
        replaySubject.onNext(1);
        replaySubject.onNext(2);

        //这里如果抛出异常,下面的subscribe()方法必须有处理异常的Action,或者使用Observer和Subscriber,不然会报错
        replaySubject.onError(new Exception("custom exception"));


        /*replaySubject.subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {

            }
        });*/
        //ReplaySubject会缓存所有onNext给他的数据,并在注册一个观察者的时候重新发送给他
        replaySubject.subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer i) {
                System.out.println("Action1 call -> " + i);
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                System.out.println("Throwable -> " + throwable.getMessage());
            }
        });

        replaySubject.onNext(9);//最后再发送一个数据9,这个时候订阅者也会接收到这个事件
    }


}
