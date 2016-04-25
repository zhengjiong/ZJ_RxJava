package com.example.operator;

import java.util.Arrays;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Created by zhengjiong on 16/4/20.
 */
public class Example5 {

    public static void main(String[] args){
        query("condition")
            .flatMap(new Func1<List<String>, Observable<String>>() {
                @Override
                public Observable<String> call(List<String> urls) {
                    return Observable.from(urls);
                }
            })
            .map(new Func1<String, String>() {
                @Override
                public String call(String url) {
                    return getTitle(url);
                }
            })
            .subscribe(new Action1<String>() {
                @Override
                public void call(String title) {
                    System.out.println(title);
                }
            });
    }

    public static Observable<String> geTitle(String url) {
        return Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                if ("url2".equals(url)) {
                    subscriber.onNext(null);
                } else {
                    subscriber.onNext(getTitle(url));
                }
            }
        });
    }

    //返回网站标题, 如果url为url2, 就返回null
    public static String getTitle(String url){

        if ("url2".equals(url)) {
            return null;
        } else {
            return "title->" + url;
        }
    }

    public static Observable<List<String>> query(String text) {
        //将just改为create, 添加了onCompleted()方法
         /*Observable.create(new Observable.OnSubscribe<ArrayList<String>>() {

             @Override
             public void call(Subscriber<? super ArrayList<String>> subscriber) {
                List<String> lists = Arrays.asList("url1", "url2", "url3");
                 subscriber.onNext(lists);
                 subscriber.onCompleted();
             }
         });*/
        //使用lambda表达式
        return Observable.create(subscriber -> {
            List<String> lists = Arrays.asList("url1", "url2", "url3");
            subscriber.onNext(lists);
            subscriber.onCompleted();
        });
    }
}
