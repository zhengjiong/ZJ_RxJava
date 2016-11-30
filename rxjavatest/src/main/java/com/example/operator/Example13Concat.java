package com.example.operator;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * concat 和 merge 的区别是，merge 不会等到前面一个 Observable 结束才会发射下一个 Observable 的数据，
 * merge 订阅到所有的 Observable 上，如果有任何一个 Observable 发射了数据，则 就把该数据发射出来。
 * 同样 还有一个 mergeWith 函数用了串联调用。
 *
 * Created by zhengjiong on 16/5/3.
 */
public class Example13Concat {

    public static void main(String[] args){
        test1();
        //test2();
    }

    /**
     *
     * concat 把多个 Observable 合并为一个，和merge的区别是concat会顺序的执行Observable,
     * 等到第一个Observable发射完数据后再发射第二个Observable发射的数据,o2也会等到o1发射完成数据后,才开始发射数据
     *
     *
     * 输出结果:
     *
     * o1 发射数据 ->0
     * concat -> call o1-0
     * o1 发射数据 ->1
     * concat -> call o1-1
     * o1 发射数据 ->2
     * concat -> call o1-2
     * o1 发射数据 ->3
     * concat -> call o1-3
     * o1 发射数据 ->4
     * concat -> call o1-4
     * o2 发射数据 ->0
     * concat -> call o2-0
     * o2 发射数据 ->1
     * concat -> call o2-1
     * o2 发射数据 ->2
     * concat -> call o2-2
     * o2 发射数据 ->3
     * concat -> call o2-3
     * o2 发射数据 ->4
     * concat -> call o2-4

     */
    private static void test1() {
        Observable o1 = Observable.interval(1000, TimeUnit.MILLISECONDS)
            .take(5)
            .map(new Func1<Long, String>() {
                @Override
                public String call(Long i) {
                    System.out.println("o1 发射数据 ->" + i);
                    return "o1-"+i;
                }
            });
        Observable o2 = Observable.interval(500, TimeUnit.MILLISECONDS)
            .take(5)
            .map(new Func1<Long, String>() {
                @Override
                public String call(Long i) {
                    System.out.println("o2 发射数据 ->" + i);
                    return "o2-" + i;
                }
            });
        Observable.concat(o1, o2)
                .onErrorResumeNext(new Func1<Throwable, Observable>() {
                    @Override
                    public Observable call(Throwable throwable) {
                        return null;
                    }
                })
            .subscribe(new Action1<String>() {
                @Override
                public void call(String mergeString) {
                    System.out.println("concat -> call " + mergeString);
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
     * merge 把多个 Observable 合并为一个，合并后的 Observable 在每个源Observable 发射数据的时候就发射同样的数据。
     * 所以多个源 Observable 的数据最终是混合是一起的
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
    private static void test2() {
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
                        System.out.println("concat -> call " + mergeString);
                    }
                });

        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
