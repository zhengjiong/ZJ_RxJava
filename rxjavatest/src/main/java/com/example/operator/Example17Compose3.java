package com.example.operator;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * 使用Compose操作符来优化Example15, 总结看Example18
 * Created by zhengjiong on 16/5/7.
 */
public class Example17Compose3 {

    public static void main(String[] args) {
        test1();
    }

    /**
     * Transformer实际上就是一个Func1<Observable<T>, Observable<R>>，
     * 换言之就是：可以通过它将一种类型的Observable转换成另一种类型的Observable，
     * 和调用一系列的内联操作符是一模一样的。
     */
    private static <T> Observable.Transformer<T, T> applySchedulers(){
        return new Observable.Transformer<T, T>() {
            @Override
            public Observable<T> call(Observable<T> observable) {
                return observable.subscribeOn(Schedulers.io())
                        .observeOn(Schedulers.immediate());
            }
        };
    }


    private static void test1() {
        List<Integer> numList = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);

        Observable.from(numList).map(new Func1<Integer, String>() {
            @Override
            public String call(Integer num) {
                return String.valueOf(num);
            }
        }).compose(applySchedulers())
        .subscribe(new Subscriber<String>() {
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
