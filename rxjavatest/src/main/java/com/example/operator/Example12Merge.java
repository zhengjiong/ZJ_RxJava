package com.example.operator;

import com.google.common.collect.Lists;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * http://rxjava.yuxingxin.com/chapter6/merge.html
 * http://blog.chengyunfeng.com/?p=972
 *
 * 在”异步的世界“中经常会创建这样的场景，我们有多个来源但是又只想有一个结果：多输入，单输出。
 * RxJava的merge()方法将帮助你把两个甚至更多的Observables合并到他们发射的数据项里。
 *
 * concat 和 merge 的区别是，merge 不会等到前面一个 Observable 结束才会发射下一个 Observable 的数据，
 * merge 订阅到所有的 Observable 上，如果有任何一个 Observable 发射了数据，则 就把该数据发射出来。
 * 同样 还有一个 mergeWith 函数用了串联调用。
 *
 * Created by zhengjiong on 16/5/3.
 */
public class Example12Merge {

    public static void main(String[] args){
        //test1();
        test2();
    }

    /**
     * 同步的合并Observable，它们将连接在一起并且不会交叉。
     *
     * 会先运行o1的,然后再o2的
     *
     * 运行结果:
     * merge -> call o1-0
     * merge -> call o1-1
     * merge -> call o1-2
     * merge -> call o1-3
     * merge -> call o1-4
     * merge -> call o2-10
     * merge -> call o2-9
     * merge -> call o2-8
     * merge -> call o2-7
     * merge -> call o2-6
     * merge -> call o2-5
     * merge -> call o2-4
     * merge -> call o2-3
     * merge -> call o2-2
     * merge -> call o2-1
     * merge -> call o2-0
     * merge -> call 1
     * merge -> call 2
     * merge -> call 3
     * merge -> call 4
     * merge -> call 5
     *
     */
    private static void test2() {
        List<String> list1 = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            list1.add("o1-"+i);
        }

        List<String> list2 = new ArrayList<>();
        for (int i = 3; i >= 0; i--) {
            list2.add("o2-"+i);
        }

        Observable o1 = Observable.from(list1);
        Observable o2 = Observable.from(list2);

        List<Integer> list3 = Arrays.asList(1, 2, 3, 4, 5);
        Observable o3 = Observable.from(list3)
            .map(new Func1<Integer, String>() {
                /**
                 * 方法1: 这里必须执行转换操作, 因为这里用的是Integer,但是o1和o2用的是String, 不转换会报异常
                 * Exception in thread "main" rx.exceptions.OnErrorNotImplementedException: java.lang.Integer cannot be cast to java.lang.String
                 *
                 * 方法2: 也可以不用转换但是,下面的merge Action1<String>, 就不能用String, 必须改成Object
                 */
                @Override
                public String call(Integer integer) {
                    return String.valueOf(integer);
                }
            });

        Observable.merge(o1, o2, o3).subscribeOn(Schedulers.immediate()).subscribe(new Action1<String>() {
            @Override
            public void call(String i) {
                System.out.println("merge -> call " + i);
            }

            /**
             * 如果o3不用map转换这里需用Object类型
             */
            /*@Override
            public void call(Object i) {
                System.out.println("merge -> call " + i);
            }*/
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {

            }
        }, new Action0() {
            @Override
            public void call() {
                System.out.println("onComplete");
            }
        });

    }

    /**
     * 因为用了interval才造成了数据混在一起, 因为interval执行的线程是Schedulers.computation(),
     * merge 把多个 Observable 合并为一个，合并后的 Observable 在每个源Observable 发射数据的时候就发射同样的数据。
     * 所以多个源 Observable 的数据最终是混合是一起的, 如果你同步的合并Observable，它们将连接在一起并且不会交叉。
     *
     * 输出结果:
     * merge -> call o2-0
     * merge -> call o1-0
     * merge -> call o2-1
     * merge -> call o2-2
     * merge -> call o2-3
     * merge -> call o1-1
     * merge -> call o2-4
     * merge -> call o1-2
     * merge -> call o2-5
     * merge -> call o2-6
     * merge -> call o2-7
     * merge -> call o1-3
     * merge -> call o2-8
     * ...
     */
    private static void test1() {
        Observable o1 = Observable.interval(1000, TimeUnit.MILLISECONDS)
            .map(new Func1<Long, String>() {
                @Override
                public String call(Long i) {
                    return "o1-"+i;
                }
            });
        Observable o2 = Observable.interval(500, TimeUnit.MILLISECONDS)
            .map(new Func1<Long, String>() {
                @Override
                public String call(Long i) {
                    return "o2-" + i;
                }
            });
        Observable.merge(o1, o2)
            .subscribe(new Action1<String>() {
                @Override
                public void call(String mergeString) {
                    System.out.println("merge -> call " + mergeString);
                }
            });

        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
