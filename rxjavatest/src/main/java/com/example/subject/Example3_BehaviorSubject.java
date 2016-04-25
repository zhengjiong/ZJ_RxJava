package com.example.subject;

import rx.functions.Action1;
import rx.subjects.BehaviorSubject;

/**
 * 简单的说，BehaviorSubject会首先向他的订阅者发送截至订阅前最新的一个数据对象（或初始值）,然后正常发送订阅后的数据流。
 *
 * Created by zhengjiong on 16/4/25.
 */
public class Example3_BehaviorSubject {

    public static void main(String[] args){
        //test1();
        test2();
    }



    /**
     * observer will receive all events.
     *
     * 运行结果:
     * call -> s=0
     * call -> s=1
     * call -> s=2
     * call -> s=3
     */
    private static void test1() {
        //create("0")是设置一个数据初值
        BehaviorSubject<String> behaviorSubject = BehaviorSubject.create("0");
        //这个时候订阅一个观察者,behaviorSubject就会把这个初值发送给观察者对象,所以就会把0传过去
        behaviorSubject.subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                System.out.println("call -> s=" + s);
            }
        });
        behaviorSubject.onNext("1");
        behaviorSubject.onNext("2");
        behaviorSubject.onNext("3");
    }

    // observer will receive the "2", "3" events, but not 0 and 1

    /**
     * 运行结果:
     *
     * call -> s=2
     * call -> s=3
     * call -> s=4
     */
    private static void test2(){
        /**
         * create("0")是设置一个数据初值, 但是订阅前又执行了3次onNext方法,behaviorSubject对象在订阅后将会只发送最后一个值给观察者,之后订阅的会正常发送
         * 所以0,1,1.5都不会接收到
         */
        BehaviorSubject<String> behaviorSubject = BehaviorSubject.create("0");
        behaviorSubject.onNext("1");
        behaviorSubject.onNext("1.5");
        behaviorSubject.onNext("2");
        behaviorSubject.subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                System.out.println("call -> s=" + s);
            }
        });
        behaviorSubject.onNext("3");
        behaviorSubject.onNext("4");
    }
}
