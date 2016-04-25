package com.example.subject;

import rx.Observer;
import rx.subjects.AsyncSubject;

/**
 * AsyncSubject 只有等整个事件系列完成时(执行onCompleted)才会发送数据，执行onComplieted后只发送最后一个值。
 * Created by zhengjiong on 16/4/25.
 */
public class Example6_AsyncSubject {


    public static void main(String[] args){
        test1();
        test2();
    }

    /**
     * 什么都不会输出,因为没有执行asyncSubject.onCompleted();
     */
    private static void test1() {
        AsyncSubject<Integer> asyncSubject = AsyncSubject.create();
        asyncSubject.subscribe(new Observer<Integer>() {
            @Override
            public void onCompleted() {
                System.out.println("Observer -> onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                System.out.println("Observer -> onError");
            }

            @Override
            public void onNext(Integer integer) {
                System.out.println("Observer -> onNext " + integer);
            }
        });
        asyncSubject.onNext(1);
        asyncSubject.onNext(2);
        asyncSubject.onNext(3);
    }

    /**
     * 输出结果:
     * Observer -> onNext 3
     * Observer -> onCompleted
     *
     * 不会输出1和2,只会输出onCompleted之前的最后一个值
     */
    private static void test2() {
        AsyncSubject<Integer> asyncSubject = AsyncSubject.create();
        asyncSubject.onNext(1);
        asyncSubject.onNext(2);
        asyncSubject.onNext(3);
        asyncSubject.onCompleted();

        asyncSubject.subscribe(new Observer<Integer>() {
            @Override
            public void onCompleted() {
                System.out.println("Observer -> onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                System.out.println("Observer -> onError");
            }

            @Override
            public void onNext(Integer integer) {
                System.out.println("Observer -> onNext " + integer);
            }
        });
    }
}
