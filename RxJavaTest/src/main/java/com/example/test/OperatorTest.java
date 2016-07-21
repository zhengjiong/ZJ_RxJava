package com.example.test;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * 操作符组合练习
 * Created by zhengjiong on 16/7/21.
 */
public class OperatorTest {

    public static void main(String[] args) {
        List<String> items = new ArrayList<>();
        items.add("1");
        items.add("2");
        items.add("2");
        items.add("3");
        items.add("3");
        items.add("4");
        items.add("5");
        items.add("6");

        Observable.just(items)
                //doOnNext就是执行到哪就取出哪的数据, 并不是执行在subscriber中的onNext之前
                .doOnNext(new Action1<List<String>>() {
                    @Override
                    public void call(List<String> strings) {
                        System.out.println("doOnNext 2 ->" + strings.toString());
                    }
                })
                .flatMap(new Func1<List<String>, Observable<String>>() {
                    @Override
                    public Observable<String> call(List<String> strings) {
                        System.out.println("flatMap");
                        return Observable.from(strings);
                    }
                }).map(new Func1<String, Integer>() {
                    @Override
                    public Integer call(String s) {
                        return Integer.parseInt(s);
                    }
                }).filter(new Func1<Integer, Boolean>() {
                    //过滤掉大于4的
                    @Override
                    public Boolean call(Integer integer) {
                        if (integer <= 4) {
                            return true;
                        } else {
                            return false;
                        }
                    }
                })
                .distinct()//过滤掉重复的数据
                .doOnNext(new Action1<Integer>() {
                    //doOnNext并不是在onnext前执行,如果把doOnNext写到distinct前面就会打印出其中重复的数据,
                    //doOnNext相当于就是执行到哪就会取出哪的数据
                    @Override
                    public void call(Integer integer) {
                        System.out.println("doOnNext 1 ->" + integer);
                    }
                })
                .toList()//重新组装成list对象
                .subscribe(new Subscriber<List<Integer>>() {
                    @Override
                    public void onCompleted() {
                        System.out.println("onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        System.out.println("onError");
                    }

                    @Override
                    public void onNext(List<Integer> integers) {
                        System.out.println("onNext " + integers.toString());
                    }
                });

    }
}
