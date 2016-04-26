package com.example.lifecycle.subscriptions;

import rx.Subscription;
import rx.functions.Action0;
import rx.subscriptions.Subscriptions;

/**
 * Subscription 和其使用的资源绑定在一起。所以你应该记得释放 Subscription 来释放资源。
 * 使用 Subscriptions 的工厂函数可以把 Subscription 和需要的资源绑定在一起，然后可以使用 unsubscribe 来释放绑定的资源。

 一个Subscription 将会和其使用的资源绑定，因此，你必须记得处理订阅的善后工作,使用Subscriptions 这个静态工厂创建一个Subscription，
 可将Subscription与一个必需的资源绑定：

 Subscription s = Subscriptions.create(() -> System.out.println("Clean "));
 s.unsubscribe();
 输出结果： Clean

 Subscriptions.create创建的订阅Subscription需要有一个取消订阅的动作，以便释放资源，这些方式有如下几种：

 Subscriptions.empty()返回的一个订阅Subscription是不需要做善后工作的，当你需要一个订阅Subscription并不需要释放任何资源是有效。
 Subscriptions.from(Subscription... subscriptions) 返回的订阅Subscription是当其结束时需要处理多个其他订阅的善后工作。
 Subscriptions.unsubscribed() 返回一个已经取消订阅做好善后工作的订阅Subscription

 * Created by zhengjiong on 16/4/26.
 */
public class Example1 {

    /**
     * 输出结果:
     * Action0 -> call
     *
     */
    public static void main(String[] args){
        Subscription subscription = Subscriptions.create(new Action0() {
            @Override
            public void call() {
                System.out.println("Action0 -> call");
            }
        });

        //这里执行unsubscribe()方法, 才会输出Action0 -> call,如果不执行什么都不会输出
        subscription.unsubscribe();
    }
}
