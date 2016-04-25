package com.example.operator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Created by zhengjiong on 16/4/20.
 */
public class Example6 {

    public static void main(String[] args) {

    }

    /*public static Observable<String> geTitle(String url) {

    }*/

    //返回网站标题, 如果url为url2, 就返回null
    public static String getTitle(String url) {

        if ("url2".equals(url)) {
            return null;
        } else {
            return "title->" + url;
        }
    }

    public static Observable<List<String>> query(String text) {
        //将just改为create, 添加了onCompleted()方法
        return Observable.create(new Observable.OnSubscribe<List<String>>() {

            @Override
            public void call(Subscriber<? super List<String>> subscriber) {
                ArrayList<String> lists = (ArrayList<String>) Arrays.asList("url1", "url2", "url3");
                subscriber.onNext(lists);
                subscriber.onCompleted();
            }
        });
        //使用lambda表达式
        /*return Observable.create(subscriber -> {
            List<String> lists = Arrays.asList("url1", "url2", "url3");
            subscriber.onNext(lists);
            subscriber.onCompleted();
        });*/
    }
}
