package com.example.subject;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;
import rx.subjects.BehaviorSubject;

/**
 * Created by zhengjiong on 16/4/27.
 */
public class Example3_BehaviorSubject2 {

    public static void main(String[] args){
        test1();
        //test2();
        //test3();
    }

    /**
     * 这里改为其他Subject(PublishSubject),运行结果还是一样
     *
     * BehaviorSubjecth或PublishSubject虽然是Subject(Subject = Observable + Observer),
     * 但是Subject必须要执行onNext,或者OnCompleted或onError,才会被观察者接收到,这里和Observable是不一样的(详细看test2()方法)
     *
     * 运行结果: 什么都不会输出
     */
    private static void test1() {

        BehaviorSubject<Integer> behaviorSubject = BehaviorSubject.create();

        behaviorSubject.subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {
                System.out.println("Action1 call " + integer);
            }
        });
    }

    /**
     *
     * 运行结果:
     * Action1 call 1
     */
    private static void test2(){
        Observable<Integer> observable = Observable.just(1);

        observable.subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {
                System.out.println("Action1 call " + integer);
            }
        });
    }

    /**
     * 未知
     *
     * 运行结果:
     * Subscriber -> call
     */
    private static void test3() {
        Observable observable = BehaviorSubject.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                System.out.println("Subscriber -> call");
            }
        });

        observable.subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {
                System.out.println("Action1 call");
            }
        });
    }
}
