package com.example.operator;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.functions.Func1;

/**
 * Created by zhengjiong on 16/5/3.
 */
public class Example9Timer {

    public static void main(String[] args) throws IOException {
        //test1();
        test2();
    }

    /**
     * 延迟2秒执行
     *
     * 运行结果:
     *
     * flatMap -> call 0
     * Subscriber -> onNext 0
     * Subscriber -> onCompleted
     */
    private static void test2() {
        Observable.timer(2, TimeUnit.SECONDS)
            .flatMap(new Func1<Long, Observable<String>>() {
                @Override
                public Observable<String> call(Long l) {
                    System.out.println("flatMap -> call " + l);
                    return Observable.just(String.valueOf(l));
                }
            })
            .subscribe(new Subscriber<String>() {
                @Override
                public void onCompleted() {
                    System.out.println("Subscriber -> onCompleted");
                }

                @Override
                public void onError(Throwable e) {
                    System.out.println("Subscriber -> onError");
                }

                @Override
                public void onNext(String s) {
                    System.out.println("Subscriber -> onNext " + s);
                }
            });

        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * Observable.timer 有两个重载函数。第一个示例创建了一个 Observable，
     * 该 Observable 等待一段时间，然后发射数据 0 ，然后就结束了。
     *
     * 运行结果:
     * Observer -> onNext 0
     * Observer -> onCompleted
     *
     */
    private static void test1() throws IOException {
        Observable.timer(500, TimeUnit.MILLISECONDS)
            .subscribe(new Observer<Long>() {
                @Override
                public void onCompleted() {
                    System.out.println("Observer -> onCompleted");
                }

                @Override
                public void onError(Throwable e) {
                    System.out.println("Observer -> onError");
                }

                @Override
                public void onNext(Long l) {
                    System.out.println("Observer -> onNext " + l);
                }
            });

        System.in.read();
    }
}
