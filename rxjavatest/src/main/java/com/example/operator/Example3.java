package com.example.operator;

import java.util.Arrays;
import java.util.List;

import rx.Observable;
import rx.functions.Action1;

/**
 * 优化Example2, 用from代替for循环
 * Created by zhengjiong on 16/4/19.
 */
public class Example3 {

    public static void main(String[] args){
        query("condition")
            .subscribe(new Action1<List<String>>() {
                @Override
                public void call(List<String> urls) {
                    //Observable.from()方法，它接收一个集合作为输入，然后每次输出一个元素给subscriber
                    Observable.from(urls)
                        .subscribe(new Action1<String>() {
                            @Override
                            public void call(String url) {
                                System.out.println(url);
                            }
                    });
                }
            });

        /**
         * 使用lambda
         */
        query("condition")
            .subscribe(urls -> {
                Observable.from(urls)
                    .subscribe(url -> System.out.println(url));
            });
    }

    static Observable<List<String>> query(String condition){
        List<String> lists = Arrays.asList("url1", "url2", "url3");
        return Observable.just(lists);
    }
}
