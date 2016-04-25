package com.example.hellorxjava;

import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * map操作符，就是用来把把一个事件转换为另一个事件的
 *
 * Created by zhengjiong on 16/4/18.
 */
public class Example5 {

    public static void main(String[] args){
        Observable.just("Hello RxJava")
                /**
                 * map转换
                 * new Func1<String, String>,
                 * 第一个参数代表call方法的参数类型,
                 * 第二个参数代表call的返回类型,
                 * 第二个参数并且还和subscribe()中Action1的<>泛型参数一样
                 *
                 */
                .map(new Func1<String, String>() {
                    @Override
                    public String call(String s) {
                        return s + " -zhengjiong";
                    }
                })
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        System.out.println(s);
                    }
                });

        //使用flatMap,对应operator Example4
        Observable.just("Hello RxJava")
                /**
                 * new Func1<List<String>, Observable<String>>,
                 * 第一个参数代表call方法的参数类型,
                 * 第二个参数代表call的返回类型,而且必须是extend Observable
                 * 并且第二个参数Observable的<>泛型类型,必须和subscribe()中Action1的泛型参数一致
                 */
                .flatMap(new Func1<String, Observable<String>>() {
                    @Override
                    public Observable<String> call(String s) {
                        return Observable.from(new String[]{s+ " -zhengjiong"});
                    }
                })
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        System.out.println(s);
                    }
                });
    }
}
