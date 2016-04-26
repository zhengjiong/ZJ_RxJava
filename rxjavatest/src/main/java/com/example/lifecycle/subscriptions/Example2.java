package com.example.lifecycle.subscriptions;

import rx.Subscription;
import rx.functions.Action0;
import rx.subscriptions.Subscriptions;

/**
 * Created by zhengjiong on 16/4/26.
 */
public class Example2 {

    public static void main(String[] args){
        test1();
        test2();
    }

    /**
     * 输出结果:
     *
     * false
     * true
     */
    private static void test1() {
        //Subscriptions.empty()返回的一个Subscription是不需要做善后工作的，当你需要一个Subscription并不需要释放任何资源是有效。
        Subscription subscription = Subscriptions.empty();

        //输出false
        System.out.println(subscription.isUnsubscribed());

        subscription.unsubscribe();

        //输出true
        System.out.println(subscription.isUnsubscribed());
    }

    /**
     * 输出结果:
     *
     * false
     * call
     * true
     */
    private static void test2() {
        Subscription subscription = Subscriptions.create(new Action0() {
            @Override
            public void call() {
                System.out.println("call");
            }
        });

        System.out.println(subscription.isUnsubscribed());

        subscription.unsubscribe();

        System.out.println(subscription.isUnsubscribed());
    }
}
