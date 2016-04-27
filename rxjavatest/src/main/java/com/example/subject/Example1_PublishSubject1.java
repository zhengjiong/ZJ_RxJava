package com.example.subject;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;
import rx.subjects.PublishSubject;

/**
 * http://www.cnblogs.com/krislight1105/p/5193969.html
 *
 * Subject = Observable + Observer
 * Subject是观察者又是被观察者,他将两者封装关联了起来
 *
 *
 * subject是一个神奇的对象，它可以是一个Observable同时也可以是一个Observer：
 * 它作为连接这两个世界的一座桥梁。一个Subject可以订阅一个Observable，就像一个观察者，
 * 并且它可以发射新的数据，或者传递它接受到的数据，就像一个Observable。很明显，作为一个Observable，
 * 观察者们或者其它Subject都可以订阅它。
 *
 *
 * Subject是可观察者Observable(被观察者)的一个拓展，同时实现了Observer(观察者)接口，
 * 也就是说，通过引入Subject，我们将可观察者和观察者联系起来，这样主要是为了简化，
 * Subject能像观察者那样接受发送给它们的事件，也能像可观察者一样将事件发送给自己的订阅者。
 * Subject能成为RxJava的理想入口，当你有来自Rx外部的事件数据值时，你能将它们推送到一个Subject，
 * 把它们转为一个可观察者（被观察者），由此可以作为Rx整个管道连的切入点。这个概念很有函数编程的味道。
 *
 * Created by zhengjiong on 16/4/25.
 */
public class Example1_PublishSubject1 {



    public static void main(String[] args){
        test1();
        //test2();
    }


    /**
     * 输出结果:
     * call -> s=2
     * call -> s=3
     * call -> s=4
     *
     * 并没有输出1, 是因为onNext("1")发出事件的时候publishSubject还没有被订阅,
     * 只有订阅以后我们才能接受到发送给它的数值
     */
    private static void test1() {
        PublishSubject<String> publishSubject = PublishSubject.create();

        publishSubject.onNext("1");//这里还未订阅观察者
        publishSubject.subscribe(new Action1<String>() {//订阅一个观察者
            @Override
            public void call(String s) {
                System.out.println("call -> s=" + s);
            }
        });

        publishSubject.onNext("2");
        publishSubject.onNext("3");
        publishSubject.onNext("4");
    }

    /**
     * 不使用PublishSubject,使用observable
     *
     * 输出结果:
     * subscribe s=1
     * subscribe s=2
     * subscribe s=3
     * subscribe s=4
     */
    private static void test2() {
        //PublishSubject.create可以替换为Observable.create
        Observable<String> observable = PublishSubject.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext("1");
                subscriber.onNext("2");
                subscriber.onNext("3");
                subscriber.onNext("4");
            }
        });
        observable.subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                System.out.println("subscribe s=" + s);
            }
        });
    }
}
