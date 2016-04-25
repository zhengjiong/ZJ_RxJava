package com.example.operator;

import java.util.Arrays;
import java.util.List;

import rx.Observable;
import rx.functions.Action1;

/**
 *
 * Created by zhengjiong on 16/4/19.
 */
public class Example2 {

    public static void main(String[] args) {
        query("condition")
                .subscribe(new Action1<List<String>>() {
                    @Override
                    public void call(List<String> urls) {
                        for (String url : urls) {
                            System.out.println(url);
                        }
                    }
                });

        /**
         * 使用lambda表达式
         */
        query("condition")
                .subscribe(urls -> {
                    for (String url : urls) {
                        System.out.println(url);
                    }
                });
    }

    static Observable<List<String>> query(String condition) {
        List<String> list = Arrays.asList("url1", "url2", "url3");
        return Observable.just(list);
    }
}
