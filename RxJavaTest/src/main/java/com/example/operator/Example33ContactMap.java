package com.example.operator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Title: Example33ContactMap
 * Description:
 * Copyright:Copyright(c)2016
 * Company: 博智维讯信息技术有限公司
 * CreateTime:16/7/26  16:45
 *
 * @author 郑炯
 * @version 1.0
 */
public class Example33ContactMap {

    public static void main(String[] args){
        List<Integer> ids = new ArrayList<>();
        ids.add(1);
        ids.add(2);
        ids.add(3);
        ids.add(4);
        ids.add(5);
        ids.add(6);
        ids.add(7);
        ids.add(8);
        ids.add(9);

        Observable.from(ids).flatMap(new Func1<Integer, Observable<String>>() {
            @Override
            public Observable<String> call(Integer i) {
                System.out.println("flatMap call =" + i);
                return Observable.just(String.valueOf(i));
            }
        }).subscribeOn(Schedulers.newThread()).observeOn(Schedulers.immediate()).subscribe(new Action1<String>() {
            @Override
            public void call(String i) {
                System.out.println("call i=" + i);
            }
        });

        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
