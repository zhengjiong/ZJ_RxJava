package com.zj.example.rxjava;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Title:
 * Description: ConcatMap和FlatMap使用区别
 * Copyright:Copyright(c)2016
 * Company: 博智维讯信息技术有限公司
 * CreateTime:16/7/27  10:35
 * @author 郑炯
 * @version 1.0
 */

public class ObservableConcatVsFlatMapSampleActivity extends AppCompatActivity implements View.OnClickListener{

    private List<Integer> ids;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_concat_flatmap_layout);

        Button btnFlatMap1 = (Button) findViewById(R.id.btn_flatMap1);
        Button btnFlatMap2 = (Button) findViewById(R.id.btn_flatMap2);
        Button btnConcatMap = (Button) findViewById(R.id.btn_concatMap);


        btnFlatMap1.setOnClickListener(this);
        btnFlatMap2.setOnClickListener(this);
        btnConcatMap.setOnClickListener(this);

        ids = new ArrayList<>();
        for (int i =0; i < 20; i++) {
            ids.add(i);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_flatMap1:
                flatMapTest1();
                break;
            case R.id.btn_flatMap2:
                flatMapTest2();
                break;
            case R.id.btn_concatMap:
                concatMapTest();
                break;
        }
    }

    /**
     * 使用concatMapTest可以使flatMapTest2()中的混乱代码按顺序执行
     *
     * 运行结果:
     *
     * RxNewThreadScheduler-21|1924
     * RxNewThreadScheduler-22|1925
     * RxNewThreadScheduler-23|1926
     * RxNewThreadScheduler-24|1927
     * RxNewThreadScheduler-25|1928
     * RxNewThreadScheduler-26|1929
     * RxNewThreadScheduler-27|1930
     * RxNewThreadScheduler-28|1931
     * RxNewThreadScheduler-29|1932
     * RxNewThreadScheduler-30|1933
     * RxNewThreadScheduler-31|1934
     * RxNewThreadScheduler-32|1935
     * RxNewThreadScheduler-33|1936
     * RxNewThreadScheduler-34|1937
     * RxNewThreadScheduler-35|1938
     * RxNewThreadScheduler-36|1939
     * RxNewThreadScheduler-37|1940
     * RxNewThreadScheduler-38|1941
     * RxNewThreadScheduler-39|1942
     * RxNewThreadScheduler-40|1943
     * main|1
     * sb2 = 0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,
     *
     */
    void concatMapTest(){
        Observable.from(ids).concatMap(new Func1<Integer, Observable<String>>() {
            @Override
            public Observable<String> call(Integer integer) {
                return Observable.create(new Observable.OnSubscribe<String>() {
                    @Override
                    public void call(Subscriber<? super String> subscriber) {
                        System.out.println("" + Thread.currentThread().getName() + "|" + Thread.currentThread().getId());
                        subscriber.onNext(String.valueOf(integer));
                        subscriber.onCompleted();
                    }
                }).subscribeOn(Schedulers.newThread());
            }
        }).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<String>() {
            StringBuilder sb2 = new StringBuilder();
            @Override
            public void onCompleted() {
                System.out.println("" + Thread.currentThread().getName() + "|" + Thread.currentThread().getId());
                System.out.println("sb2 = " + sb2.toString());
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {
                sb2.append(s);
                sb2.append(",");
            }
        });
    }

    /**
     *  Observable.create(new Observable.OnSubscribe<String>() 中的代码会执行在一个新的线程中,
     *  并且循环执行ids的时候, Observable.create中的代码每一次都会运行在同一个新线程中,所以下面的sb2输出
     *  的代码顺序不会混乱从0到19
     *
     *  运行结果:
     *  sb2 = 0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19
     */
    void flatMapTest1(){

        Observable.from(ids).flatMap(new Func1<Integer, Observable<String>>() {

            @Override
            public Observable<String> call(Integer integer) {
                //System.out.println("flatMap ->" + integer);
                return Observable.create(new Observable.OnSubscribe<String>() {
                    @Override
                    public void call(Subscriber<? super String> subscriber) {
                        System.out.println("" + Thread.currentThread().getName() + "|" + Thread.currentThread().getId());
                        subscriber.onNext(String.valueOf(integer));
                        subscriber.onCompleted();
                    }
                });
            }
        }).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<String>() {
            StringBuilder sb2 = new StringBuilder();
            @Override
            public void onCompleted() {
                System.out.println("" + Thread.currentThread().getName() + "|" + Thread.currentThread().getId());
                System.out.println("sb2 = " + sb2.toString());
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {
                sb2.append(s);
                sb2.append(",");
                //System.out.println("call -> " + s);
            }
        });
    }

    /**
     *  Observable.create(new Observable.OnSubscribe<String>() 中的代码会执行在一个新的线程中,
     *  并且循环执行ids的时候, Observable.create中的代码每一次都会运行在一个不同的新线程中,所以下面的sb2输出
     *  的代码会混乱
     *
     *  运行结果:
     *  sb2 = 18, 0,6,5,7,2,4,8,1,9,3,13,19,15,10,14,12,11,16,17
     */
    void flatMapTest2(){

        Observable.from(ids).flatMap(new Func1<Integer, Observable<String>>() {

            @Override
            public Observable<String> call(Integer integer) {
                //System.out.println("flatMap ->" + integer);
                return Observable.create(new Observable.OnSubscribe<String>() {
                    @Override
                    public void call(Subscriber<? super String> subscriber) {
                        System.out.println("" + Thread.currentThread().getName() + "|" + Thread.currentThread().getId());
                        subscriber.onNext(String.valueOf(integer));
                        subscriber.onCompleted();
                    }
                }).subscribeOn(Schedulers.newThread());
            }
        }).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<String>() {
            StringBuilder sb2 = new StringBuilder();
            @Override
            public void onCompleted() {
                System.out.println("" + Thread.currentThread().getName() + "|" + Thread.currentThread().getId());
                System.out.println("sb2 = " + sb2.toString());
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {
                sb2.append(s);
                sb2.append(",");
                //System.out.println("call -> " + s);
            }
        });
    }
}
