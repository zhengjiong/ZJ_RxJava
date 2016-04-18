package com.example.hellorxjava;

import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * 使用两个Map操作符
 * Created by zhengjiong on 16/4/19.
 */
public class Example7 {

    public static void main(String[] args){
        Observable.just("Hello RxJava")
                .map(new Func1<String, Integer>() {
                    @Override
                    public Integer call(String s) {
                        return s.hashCode();
                    }
                })
                .map(new Func1<Integer, String>() {
                    @Override
                    public String call(Integer integer) {
                        return Integer.toString(integer) + " -zhengjiong";
                    }
                })
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        System.out.println(s);  //-1105135402 -zhengjiong
                    }
                });
    }
}
