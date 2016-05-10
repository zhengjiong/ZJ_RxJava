package com.example.operator;

import java.util.Arrays;
import java.util.List;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.schedulers.Schedulers;

/**
 *
 * repeat与Retry的对比
 * 首先，来了解一下.repeat()和.retry()之间最直观的区别是什么？这个问题并不难：区别就在于什么样的终止事件会触发重订阅。
 * 当.repeat()接收到.onCompleted()事件后触发重订阅。
 * 当.retry()接收到.onError()事件后触发重订阅。
 *
 * 经验之谈
 * 这里有一些关于.repeatWhen()和.retryWhen()的要点，我们应该牢记于心。
 * <p>
 * .repeatWhen()与.retryWhen()非常相似，只不过不再响应onCompleted作为重试条件，而是onError。因为onCompleted没有类型，所有输入变为Observable<Void>。
 * 每一次事件流的订阅notificationHandler（也就是Func1）只会调用一次。这也是讲得通的，因为你有一个可观测的Observable<Throwable>，它能够发送任意数量的error。
 * 输入的Observable必须作为输出Observable的源。你必须对Observable<Throwable>做出反应，然后基于它发送事件；你不能只返回一个通用泛型流。
 * <p>
 * <p>
 * 输入Observable只在终止事件发生的时候才会触发（对于.repeatWhen()来说是onCompleted,而对于.retryWhen()来说是onError）。
 * 它不会从源中接收到任何onNext的通知，所以你不能通过观察被发送的事件来决定重订阅。如果你真的需要这样做，
 * 你应该添加像.takeUntil()这样的操作符，来拦截事件流。
 * <p>
 * Created by zhengjiong on 16/5/10.
 */
public class Example28Repeat {

    /**
     *
     * 运行结果:
     * onNext -> i=1
     * onNext -> i=2
     * onNext -> i=1
     * onNext -> i=2
     * subscribe -> onCompleted
     */
    public static void main(String[] args) {
        Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                subscriber.onNext(1);
                subscriber.onNext(2);
                subscriber.onCompleted();
            }
        })
        /**
         * 当.repeat()接收到.onCompleted()事件后触发重订阅,
         * 如果被观察者没有执行subscriber.onCompleted(), 就不会触发重订阅
         *
         * repeat(2)只会触发一次重订阅, repeat(1)不会触发重订阅
         */
        .repeat(2, Schedulers.immediate())
        .subscribe(new Observer<Integer>() {
            @Override
            public void onCompleted() {
                System.out.println("subscribe -> onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                System.out.println("onError -> e=" + e.getMessage());
            }

            @Override
            public void onNext(Integer i) {
                System.out.println("onNext -> i=" + i);
            }
        });
    }
}
