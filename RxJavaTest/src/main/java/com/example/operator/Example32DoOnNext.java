package com.example.operator;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * doOnNext执行的前提条件是这次操作会执行subscriber中的onNext方法
 *
 * Created by zhengjiong on 16/7/24.
 */
public class Example32DoOnNext {

    public static void main(String[] args){
        //test1();
        //test2();
        //test3();
        //test4();
        test5();
    }

    /**
     * 执行过程:
     * -
     * onError e=RuntimeException
     * --
     * ---
     */
    private static void test5() {
        Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                System.out.println("-");
                subscriber.onError(new RuntimeException("RuntimeException"));
                System.out.println("--");
                /**
                 * 把doOnNext提到map之前,
                 * 这样的话map不会执行, 所以map也不会执行,因为rx是链试调用的
                 */
                subscriber.onNext(1);
                System.out.println("---");
            }
        }).doOnNext(new Action1<Integer>() {
            @Override
            public void call(Integer i) {
                System.out.println("doOnNext i=" + i);
            }
        }).map(new Func1<Integer, String>() {
            /**
             * 这里会执行map的call方法
             */
            @Override
            public String call(Integer integer) {
                System.out.println("map->" + integer);
                return String.valueOf(integer);
            }
        }).doOnNext(new Action1<String>() {
            @Override
            public void call(String i) {
                System.out.println("doOnNext i=" + i);
            }
        }).subscribe(new Subscriber<String>() {
            @Override
            public void onCompleted() {
                System.out.println("onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                System.out.println("onError e=" + e.getMessage());
            }

            @Override
            public void onNext(String i) {
                System.out.println("onNext i=" + i);
            }
        });
    }

    /**
     * 执行过程:
     * -
     * onError e=RuntimeException
     * --
     * map->1
     * ---
     */
    private static void test4() {
        Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                System.out.println("-");
                subscriber.onError(new RuntimeException("RuntimeException"));
                System.out.println("--");
                /**
                 * 这里会执行onNext(1), 但是因为已经执行了Subscriber的onError或者onCompleted(代表已经订阅结束),
                 * 所以不会执行Subscriber的onNext方法,所以doOnNext也不会执行,
                 * 但是map方法或者flatMap等方法和doOnNext不一样, 所以就算是执行了onError或者onCompleted,
                 * 也还是会执行
                 */
                subscriber.onNext(1);
                System.out.println("---");
            }
        }).map(new Func1<Integer, String>() {
            /**
             * 这里会执行map的call方法
             */
            @Override
            public String call(Integer integer) {
                System.out.println("map->" + integer);
                return String.valueOf(integer);
            }
        }).doOnNext(new Action1<String>() {
            @Override
            public void call(String i) {
                System.out.println("doOnNext i=" + i);
            }
        }).subscribe(new Subscriber<String>() {
            @Override
            public void onCompleted() {
                System.out.println("onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                System.out.println("onError e=" + e.getMessage());
            }

            @Override
            public void onNext(String i) {
                System.out.println("onNext i=" + i);
            }
        });
    }

    /**
     * 执行过程:
     * -
     * onCompleted
     * --
     * ---
     */
    private static void test3() {
        Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                System.out.println("-");
                subscriber.onCompleted();
                System.out.println("--");
                /**
                 * 这里会执行onNext(1), 但是因为已经执行了Subscriber的onError或者onCompleted(代表已经订阅结束),
                 * 所以不会执行Subscriber的onNext方法,所以doOnNext也不会执行
                 */
                subscriber.onNext(1);
                System.out.println("---");
            }
        }).doOnNext(new Action1<Integer>() {
            @Override
            public void call(Integer i) {
                System.out.println("doOnNext i=" + i);
            }
        }).subscribe(new Subscriber<Integer>() {
            @Override
            public void onCompleted() {
                System.out.println("onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                System.out.println("onError e=" + e.getMessage());
            }

            @Override
            public void onNext(Integer i) {
                System.out.println("onNext i=" + i);
            }
        });
    }

    /**
     * 执行过程:
     * -
     * onError e=RuntimeException
     * --
     * ---
     */
    private static void test2() {
        Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                System.out.println("-");
                subscriber.onError(new RuntimeException("RuntimeException"));
                System.out.println("--");
                /**
                 * 这里会执行onNext(1), 但是因为已经执行了Subscriber的onError或者onCompleted(代表已经订阅结束),
                 * 所以不会执行Subscriber的onNext方法,所以doOnNext也不会执行
                 */
                subscriber.onNext(1);
                System.out.println("---");
            }
        }).doOnNext(new Action1<Integer>() {
            @Override
            public void call(Integer i) {
                System.out.println("doOnNext i=" + i);
            }
        }).subscribe(new Subscriber<Integer>() {
            @Override
            public void onCompleted() {
                System.out.println("onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                System.out.println("onError e=" + e.getMessage());
            }

            @Override
            public void onNext(Integer i) {
                System.out.println("onNext i=" + i);
            }
        });
    }

    /**
     * 执行过程:
     * -
     * doOnNext i=1
     * onNext i=1
     * --
     */
    private static void test1() {
        Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                System.out.println("-");
                subscriber.onNext(1);
                System.out.println("--");
            }
        }).doOnNext(new Action1<Integer>() {
            @Override
            public void call(Integer i) {
                System.out.println("doOnNext i=" + i);
            }
        }).subscribe(new Subscriber<Integer>() {
            @Override
            public void onCompleted() {
                System.out.println("onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                System.out.println("onError e=" + e.getMessage());
            }

            @Override
            public void onNext(Integer i) {
                System.out.println("onNext i=" + i);
            }
        });

    }
}
