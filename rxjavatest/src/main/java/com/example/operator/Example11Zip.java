package com.example.operator;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;
import rx.functions.Func2;
import rx.schedulers.Schedulers;

/**
 * Created by zhengjiong on 16/5/3.
 */
public class Example11Zip {

    public static void main(String[] args) throws IOException {
        //test1();
        test2();
    }

    /**
     * 如何有多个 源 Observable，则 zip 会等待最慢的一个 Observable 发射完数据才开始组合这次发射的所有数据。
     *
     * zip 的任意一个源 Observable 结束标示着 zip 的结束。其他源 Observable 后续发射的数据被忽略了。

     * 运行结果:
     * subscribe Action1 -> call observable1 onNext 1 | observable2 onNext 1
     * subscribe Action1 -> call observable1 onNext 2 | observable2 onNext 2
     * subscribe Action1 -> call observable1 onNext 3 | observable2 onNext 3
     */
    public static void test2(){
        Observable observable1 = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext("observable1 onNext 1");
                subscriber.onNext("observable1 onNext 2");
                subscriber.onNext("observable1 onNext 3");
                subscriber.onNext("observable1 onNext 4");
                subscriber.onNext("observable1 onNext 5");
                subscriber.onNext("observable1 onNext 6");
            }
        });
        Observable observable2 = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext("observable2 onNext 1");
                subscriber.onNext("observable2 onNext 2");
                subscriber.onNext("observable2 onNext 3");
            }
        });

        Observable.zip(observable1, observable2, new Func2<String, String, String>() {
            @Override
            public String call(String o1, String o2) {
                return o1 + " | " + o2;
            }
        })
        .subscribe(new Action1() {
            @Override
            public void call(Object o) {
                System.out.println("subscribe Action1 -> call " + o.toString());
            }
        });
    }

    /**
     *
     * 如何有多个 源 Observable，则 zip 会等待最慢的一个 Observable 发射完数据才开始组合这次发射的所有数据。
     *
     * 虽然第二个Observable是每个100毫秒执行的, 使用zip后,第二个Observable也会等待第一个执行完后再执行.
     *
     * 输出结果:
     *
     * zipString = 0-0
     * zipString = 1-1
     * zipString = 2-2
     * zipString = 3-3
     * zipString = 4-4
     * zipString = 5-5
     * zipString = 6-6
     * zipString = 7-7
     * zipString = 8-8
     * zipString = 9-9
     */
    private static void test1() throws IOException {
        Observable.zip(
                Observable.interval(1000, TimeUnit.MILLISECONDS),
                Observable.interval(100, TimeUnit.MILLISECONDS),
                new Func2<Long, Long, String>() {
            @Override
            public String call(Long a1, Long a2) {
                return a1 + "-" + a2;
            }
        })
        .subscribe(new Action1<String>() {
            @Override
            public void call(String zipString) {
                System.out.println("zipString = " + zipString);
            }
        });

        System.in.read();
    }
}
