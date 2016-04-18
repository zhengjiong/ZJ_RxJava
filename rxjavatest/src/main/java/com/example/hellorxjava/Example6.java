package com.example.hellorxjava;

import rx.Observable;

/**
 * Example5转换层java8lambda表达式
 * <p>
 * Created by zhengjiong on 16/4/18.
 */
public class Example6 {

    public static void main(String[] args) {
        Observable.just("Hello RxJava")
                //如果lambda表达式中不包含一行,就需要用{},如果有返回值需要加return
                .map(s -> {
                    System.out.println("~");
                    return s + " -zhengjiong";
                })
                .subscribe(s -> System.out.println(s));
    }
}
