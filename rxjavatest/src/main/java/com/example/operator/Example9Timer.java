package com.example.operator;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Observer;

/**
 * Created by zhengjiong on 16/5/3.
 */
public class Example9Timer {

    public static void main(String[] args) throws IOException {
        test1();
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
