package com.example.operator;

import java.util.Arrays;
import java.util.List;

import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * 使用flatMap优化Example3
 * Created by zhengjiong on 16/4/19.
 */
public class Example4 {

    public static void main(String[] args){
        query("condition")
            /**
             * new Func1<List<String>, Observable<String>>,
             * 第一个参数代表call方法的参数类型,
             * 第二个参数代表call的返回类型,而且必须是extend Observable
             * 并且第二个参数Observable的<>泛型类型,必须和subscribe()中Action1的泛型参数一致
             */
            .flatMap(new Func1<List<String>, Observable<String>>() {
                @Override
                public Observable<String> call(List<String> urls) {
                    return Observable.from(urls);
                }
            })
            .subscribe(new Action1<String>() {
                @Override
                public void call(String s) {
                    System.out.println(s);
                }
            });
    }

    static Observable<List<String>> query(String condition){
        List<String> lists = Arrays.asList("url1", "url2", "url3");
        return Observable.just(lists);
    }
}
