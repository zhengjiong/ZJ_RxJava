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
                //转换
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
    }
}
