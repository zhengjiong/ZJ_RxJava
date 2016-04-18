package com.example.hellorxjava;

import rx.Observable;

/**
 * Example7使用Lambda表达式
 * Created by zhengjiong on 16/4/19.
 */
public class Example8 {

    public static void main(String[] args){
        Observable.just("Hello RxJava")
                .map(s -> s.hashCode())
                .map(i -> Integer.toString(i) + " -zhengjiong")
                .subscribe(s -> System.out.println(s));
    }
}
