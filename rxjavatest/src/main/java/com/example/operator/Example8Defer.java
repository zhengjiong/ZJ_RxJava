package com.example.operator;

import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func0;

/**
 * Created by zhengjiong on 16/5/3.
 */
public class Example8Defer {

    public static void main(String[] args) {
        //test1();
        test2();
    }

    /**
     * 两次打印的结果都是一样的
     *
     * just 函数创建一个发射预定义好的数据的 Observable ，发射完这些数据后，事件流就结束了。
     *
     * subscriber 相隔 1秒订阅这个 Observable，但是他们收到的时间数据是一样的！这是因为当订阅的时候，时间数据只调用一次。
     *
     * 执行结果:
     * Action1 -> call 1462235285886
     * Action1 -> call 1462235285886
     */
    private static void test1() {
        Observable observable = Observable.just(getCurrentTime());
        observable.subscribe(new Action1<Long>() {
            @Override
            public void call(Long time) {
                System.out.println("Action1 -> call " + time);
            }
        });

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        observable.subscribe(new Action1<Long>() {
            @Override
            public void call(Long time) {
                System.out.println("Action1 -> call " + time);
            }
        });
    }

    /**
     * defer 并没有定义一个新的 Observable， defer 只是用来声明当 Subscriber 订阅到一个 Observable 上时，
     * 该 Observable 应该如何创建。
     *
     *
     * defer 的参数是一个返回一个 Observable 对象的函数。
     * 该函数返回的 Observable 对象就是 defer 返回的 Observable 对象。
     * 重点是，每当一个新的 Subscriber 订阅的时候，这个函数就重新执行一次
     *
     * 执行结果:
     * Action1 -> call 1462235582664
     * Action1 -> call 1462235583861
     *
     */
    private static void test2(){
        Observable observable = Observable.defer(new Func0<Observable<Object>>() {
            @Override
            public Observable<Object> call() {
                return Observable.just(getCurrentTime());
            }
        });

        observable.subscribe(new Action1<Long>() {
            @Override
            public void call(Long time) {
                System.out.println("Action1 -> call " + time);
            }
        });

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        observable.subscribe(new Action1<Long>() {
            @Override
            public void call(Long time) {
                System.out.println("Action1 -> call " + time);
            }
        });
    }

    private static long getCurrentTime() {
        return System.currentTimeMillis();
    }
}
