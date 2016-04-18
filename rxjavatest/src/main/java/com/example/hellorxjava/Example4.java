package com.example.hellorxjava;

import rx.Observable;

/**
 * Example3转换成java8的lambda表达式
 *
 * Created by zhengjiong on 16/4/18.
 */
public class Example4 {

    public static void main(String[] args){
        Observable.just("Hello RxJava").subscribe(s -> System.out.println(s));
    }
}
