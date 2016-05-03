package com.example.operator;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Observer;

/**
 * Created by zhengjiong on 16/5/3.
 */
public class Example9Timer {

    public static void main(String[] args){
        test1();
    }

    /**
     * 测试什么都不输出, 暂时未找到原因
     *
     * Observable.timer 有两个重载函数。第一个示例创建了一个 Observable，
     * 该 Observable 等待一段时间，然后发射数据 0 ，然后就结束了。
     *
     *
     * 运行结果:
     *
     */
    private static void test1() {
        Observable.timer(2, TimeUnit.SECONDS)
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
    }
}
