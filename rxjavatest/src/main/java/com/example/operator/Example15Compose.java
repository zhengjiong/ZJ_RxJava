package com.example.operator;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * 使用compose的方式看demo17,泛型的方式看demo16
 * Created by zhengjiong on 16/5/7.
 */
public class Example15Compose {

    public static void main(String[] args) {
        test1();
    }

    /**
     * 使用普通的方式
     */
    private static void test1() {
        List<Integer> numList = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);
        Observable.from(numList).map(new Func1<Integer, String>() {
            @Override
            public String call(Integer num) {
                return String.valueOf(num);
            }
        }).subscribeOn(Schedulers.io())//下面必须要使用System.in.read(), 不然在java环境中运行不会显示打印
        .observeOn(Schedulers.immediate())
        .subscribe(new Subscriber<String>() {
            @Override
            public void onCompleted() {
                System.out.println("Subscriber -> onCompleted");
            }

            @Override
            public void onError(Throwable e) {}

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

}
