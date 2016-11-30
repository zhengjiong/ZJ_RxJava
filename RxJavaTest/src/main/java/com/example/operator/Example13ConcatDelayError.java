package com.example.operator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * 此demo有问题, 在error后也不能继续执行,以后再想办法
 * Created by zhengjiong on 16/5/3.
 */
public class Example13ConcatDelayError {

    public static void main(String[] args){
        //test1();
        test2();
    }

    /**
     *
     *
     * i == 1,的时候会抛出异常, 从而执行subscriber中的onError方法, 之后便不会继续执行.
     * 如果想继续执行要使用concatDelayError方法
     *
     * 输出结果:
     *
     * o1 发射数据 ->0
     * concat -> call o1-0
     * o1 发射数据 ->1
     * throwable -> 空指针
     *
     */
    private static void test2() {
        Observable<String> o1 = Observable.interval(1000, TimeUnit.MILLISECONDS)
                .take(3)
                .map(new Func1<Long, String>() {
                    @Override
                    public String call(Long i) {
                        System.out.println("o1 发射数据 ->" + i);
                        if (i == 1) {
                            throw new NullPointerException("空指针");
                        }
                        return "o1-"+i;
                    }
                });
        Observable<String> o2 = Observable.interval(500, TimeUnit.MILLISECONDS)
                .take(3)
                .map(new Func1<Long, String>() {
                    @Override
                    public String call(Long i) {
                        System.out.println("o2 发射数据 ->" + i);
                        return "o2-" + i;
                    }
                });

        Observable.concat(o1, o2).concatMapDelayError(new Func1<String, Observable<String>>() {
            @Override
            public Observable<String> call(String s) {
                return Observable.just("9999");
            }
        }).subscribe(new Action1<String>() {
            @Override
            public void call(String mergeString) {
                System.out.println("concat -> call " + mergeString);
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                System.out.println("throwable -> " + throwable.getMessage());
            }
        });
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * concat 把多个 Observable 合并为一个，和merge的区别是concat会顺序的执行Observable,
     * 等到第一个Observable发射完数据后再发射第二个Observable发射的数据,o2也会等到o1发射完成数据后,才开始发射数据
     *
     * i == 1,的时候会抛出异常, 从而执行subscriber中的onError方法, 之后便不会继续执行.
     * 如果想继续执行要使用concatDelayError方法
     *
     * 输出结果:
     *
     * o1 发射数据 ->0
     * concat -> call o1-0
     * o1 发射数据 ->1
     * throwable -> 空指针
     *
     */
    private static void test1() {
        Observable o1 = Observable.interval(1000, TimeUnit.MILLISECONDS)
            .take(3)
            .map(new Func1<Long, String>() {
                @Override
                public String call(Long i) {
                    System.out.println("o1 发射数据 ->" + i);
                    if (i == 1) {
                        throw new NullPointerException("空指针");
                    }
                    return "o1-"+i;
                }
            });
        Observable o2 = Observable.interval(500, TimeUnit.MILLISECONDS)
            .take(3)
            .map(new Func1<Long, String>() {
                @Override
                public String call(Long i) {
                    System.out.println("o2 发射数据 ->" + i);
                    return "o2-" + i;
                }
            });
        Observable.concat(o1, o2)
            .subscribe(new Action1<String>() {
                @Override
                public void call(String mergeString) {
                    System.out.println("concat -> call " + mergeString);
                }
            }, new Action1<Throwable>() {
                @Override
                public void call(Throwable throwable) {
                    System.out.println("throwable -> " + throwable.getMessage());
                }
            });
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
