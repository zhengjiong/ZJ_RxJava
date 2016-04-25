package com.example.operator;

import rx.Observable;
import rx.functions.Action1;

/**
 * Observable.from()方法，它接收一个集合作为输入，然后每次输出一个元素给subscriber
 *
 * Created by zhengjiong on 16/4/19.
 */
public class Example1 {

    public static void main(String[] args){

        /*
         * Observable.from()方法，它接收一个集合作为输入，然后每次输出一个元素给subscriber
         */
        Observable.from(new String[]{"url1", "url2", "url3"})
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String url) {
                        System.out.println(url);
                    }
                });

        //用lambda表达式
        Observable.from(new String[]{"url1", "url2", "url3"})
                .subscribe(url -> System.out.println(url));
    }
}
