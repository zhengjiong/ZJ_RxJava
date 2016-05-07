package com.example.operator;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * 使用泛型优化Example15
 *
 * 使用泛型是一个不好的做法虽然这样做也能达到目的，但是它看起来不仅丑，还容易让人产生困惑，
 * applySchedulers()到底什么鬼？它不再符合操作符链路式结构，所以，看起来很难理解。
 *
 * Created by zhengjiong on 16/5/7.
 */
public class Example16Compose2 {

    public static void main(String[] args) {
        test1();
    }


    /**
     * 使用泛型来创建一个方法, 达到重用代码的目的
     *
     * @param observable
     * @param <T>
     * @return
     */
    private static <T> Observable<T> applySchedulers(Observable<T> observable) {
        return observable.subscribeOn(Schedulers.io())
                .observeOn(Schedulers.immediate());
    }

    private static void test1() {
        List<Integer> numList = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);
        applySchedulers(Observable.from(numList).map(new Func1<Integer, String>() {
            @Override
            public String call(Integer num) {
                return String.valueOf(num);
            }
        })).subscribe(new Subscriber<String>() {
            @Override
            public void onCompleted() {
                System.out.println("Subscriber -> onCompleted");
            }

            @Override
            public void onError(Throwable e) {
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

}
