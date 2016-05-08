package com.example.operator;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Created by zhengjiong on 16/5/7.
 */
public class Example21SwitchOnNext {

    public static void main(String[] args){
        //test1();
        //test2();
        test3();
    }

    static void timeOperation1(String content){
        /*new Thread(new Runnable() {
            @Override
            public void run() {
                long start = System.currentTimeMillis();
                for (long i = 0; i < 10000000000L;i++) {
                }
                long end = System.currentTimeMillis();
                System.out.println("timeOperation1 " + (end - start));
            }
        }).start();*/
        long start = System.currentTimeMillis();
        System.out.println("timeOperation1 : " + content + " : start = " + start);
        for (long i = 0; i < 10000000000L;i++) {
        }
        long end = System.currentTimeMillis();
        System.out.println("timeOperation1 : " + content + " : end - start = " + (end - start));
    }

    /**
     * 运行结果:
     *
     * 0
     * 0
     * 0
     * 1
     * 1
     * 1
     * 2
     * 2
     * 2
     */
    private static void test3() {
                Observable.interval(100, TimeUnit.MILLISECONDS)
                .take(9)
                .switchMap(new Func1<Long, Observable<Long>>() {

                    @Override
                    public Observable<Long> call(Long i) {
                        return Observable.interval(30, TimeUnit.MILLISECONDS)
                                .map(new Func1<Long, Long>() {
                                    @Override
                                    public Long call(Long i2) {
                                        timeOperation1(String.valueOf(i));
                                        return i;
                                    }
                                });
                    }
                })
                .take(9)
                .subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
                        System.out.println(aLong);
                    }
                });



        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * switchOnNext 的参数为一个返回 Observable 对象的 Observable。也就是说，这个参数为一个 Observable，
     * 但是这个 Observable 所发射的数据类型是 Observable 。
     * switchOnNext 返回的 Observable 发射数据的规则如下：
     * 在参数 Observable 返回的 Observable 中，把最先发射数据的 Observable 中的数据拿来转发，
     * 如果之后又有新的 Observable 开始发射数据了，则就切换到新的 Observable 丢弃前一个。
     *
     * 运行结果:
     * 0
     * 0
     * 0
     * 1
     * 1
     * 1
     * 2
     * 2
     * 2
     *
     * 注意上面示例中 switchOnNext 的参数 每隔 100毫秒返回一个 Observable 。这个返回的 Observable 会每隔
     * 30 毫秒发射一个数字，这个数字被映射为 100毫秒发射一个数据的 Observable 返回的数据。
     * 所以在第一个100毫秒的时候，switchOnNext 的参数返回的第一个 Observable 可以发射3个数据 0，
     * 然后到第100毫秒的时候，switchOnNext 的参数返回的第二个 Observable 开发发射数据1，
     * 所以前一个发射数据 0 的 Observable 就被丢弃了， switchOnNext 切换到新的发射数据的 Observable。
     */
    private static void test2() {
        Observable.switchOnNext(
                Observable.interval(100, TimeUnit.MILLISECONDS).map(new Func1<Long, Observable<Long>>() {
                            @Override
                            public Observable<Long> call(Long i) {
                                return Observable.interval(30, TimeUnit.MILLISECONDS)
                                        .map(new Func1<Long, Long>() {
                                            @Override
                                            public Long call(Long i2) {
                                                return i;
                                            }
                                        });
                            }
                        })
                 )
                .take(9)
                .subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
                        System.out.println(aLong);
                    }
                });



        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static void test1() {
        Observable.switchOnNext(
                Observable.just(Observable.just(1))
        ).subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {
                System.out.println(integer);
            }
        });
    }
}
