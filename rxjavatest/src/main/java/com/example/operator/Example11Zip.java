package com.example.operator;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func2;
import rx.schedulers.Schedulers;

/**
 * 在一种新的可能场景中处理多个数据来源时会带来：多从个Observables接收数据，处理它们，
 * 然后将它们合并成一个新的可观测序列来使用。RxJava有一个特殊的方法可以完成：zip()合并两
 * 个或者多个Observables发射出的数据项，根据指定的函数Func*变换它们，并发射一个新值。
 *
 * Created by zhengjiong on 16/5/3.
 */
public class Example11Zip {

    /**
     * zip和merge的区别是:
     * 1.zip会把两个Observable发射的值在Func2中重新组装后发送给观察者
     * 2.zip中如果有一个Observable结束,那后续的发射数据将被忽略.
     * 3.如何有多个 源 Observable，则 zip 会等待最慢的一个 Observable 发射完数据才开始组合这次发射的所有数据。
     * 4.merge 把多个 Observable 合并为一个，合并后的 Observable 在每个源Observable 发射数据的时候就发射同样的数据。
     */
    public static void main(String[] args) throws IOException {
        //test1();
        //test2();
        //test3();
        test4();
    }

    /**
     *
     * 每隔一秒输出list1中的一个值, list1输出完之后, o1将会默认执行onCompleted,
     * 标示着 zip 的结束。其他源 Observable 后续发射的数据被忽略了。
     * 如何有多个 源 Observable，则 zip 会等待最慢的一个 Observable 发射完数据才开始组合这次发射的所有数据。
     *
     * 运行结果:
     * Subscriber -> onNext o1: 1 | o2:0
     * Subscriber -> onNext o1: 2 | o2:1
     * Subscriber -> onNext o1: 3 | o2:2
     * Subscriber -> onNext o1: 4 | o2:3
     * Subscriber -> onNext o1: 5 | o2:4
     * Subscriber -> onNext o1: 6 | o2:5
     * Subscriber -> onNext o1: 7 | o2:6
     * Subscriber -> onNext o1: 8 | o2:7
     * Subscriber -> onNext o1: 9 | o2:8
     * Subscriber -> onCompleted
     */
    private static void test4() {
        List<Integer> list1 = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);

        Observable o1 = Observable.from(list1);
        Observable o2 = Observable.interval(1, TimeUnit.SECONDS);

        Observable.zip(o1, o2, new Func2<Integer, Long, String>() {

            @Override
            public String call(Integer integer, Long l) {

                return "o1: " + integer + " | o2:" + l;
            }
        })
        .subscribe(new Subscriber<String>() {
            @Override
            public void onCompleted() {
                System.out.println("Subscriber -> onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                System.out.println("Subscriber -> onError");
            }

            @Override
            public void onNext(String s) {
                System.out.println("Subscriber -> onNext " + s);
            }
        });

        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * zip 的任意一个源 Observable 结束标示着 zip 的结束。其他源 Observable 后续发射的数据被忽略了。
     *
     * 运行结果:
     *
     * Action1 -> call observable1 onNext 1 | observable2 onNext 1
     * Action1 -> call observable1 onNext 2 | observable2 onNext 2
     * Action1 -> call observable1 onNext 3 | observable2 onNext 3
     */
    public static void test3(){
        Observable observable1 = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext("observable1 onNext 1");
                subscriber.onNext("observable1 onNext 2");

                subscriber.onCompleted();//Observable1执行完成操作
                //subscriber.onError(new Exception("zhengjiong"));

                subscriber.onNext("observable1 onNext 3");
                subscriber.onNext("observable1 onNext 4");
                subscriber.onNext("observable1 onNext 5");
                subscriber.onNext("observable1 onNext 6");
            }
        });
        Observable observable2 = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext("observable2 onNext 1");
                subscriber.onNext("observable2 onNext 2");
                subscriber.onNext("observable2 onNext 3");
            }
        });

        Observable.zip(observable1, observable2, new Func2<String, String, String>() {

            @Override
            public String call(String s, String s2) {
                return s + " | " + s2;
            }
        })
        .subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                System.out.println("Action1 -> call " + s);
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                System.out.println("Action1<Throwable> -> call " + throwable.getMessage());
            }
        }, new Action0() {
            @Override
            public void call() {
                System.out.println("Action0 -> call onCompleted");
            }
        });
    }

    /**
     * 如何有多个 源 Observable，则 zip 会等待最慢的一个 Observable 发射完数据才开始组合这次发射的所有数据。
     *
     * zip 的任意一个源 Observable 结束标示着 zip 的结束。其他源 Observable 后续发射的数据被忽略了。

     * 运行结果:
     * subscribe Action1 -> call observable1 onNext 1 | observable2 onNext 1
     * subscribe Action1 -> call observable1 onNext 2 | observable2 onNext 2
     * subscribe Action1 -> call observable1 onNext 3 | observable2 onNext 3
     */
    public static void test2(){
        Observable observable1 = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext("observable1 onNext 1");
                subscriber.onNext("observable1 onNext 2");
                subscriber.onNext("observable1 onNext 3");
                subscriber.onNext("observable1 onNext 4");
                subscriber.onNext("observable1 onNext 5");
                subscriber.onNext("observable1 onNext 6");
            }
        });
        Observable observable2 = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext("observable2 onNext 1");
                subscriber.onNext("observable2 onNext 2");
                subscriber.onNext("observable2 onNext 3");
            }
        });

        Observable.zip(observable1, observable2, new Func2<String, String, String>() {
            @Override
            public String call(String o1, String o2) {
                return o1 + " | " + o2;
            }
        })
        .subscribe(new Action1() {
            @Override
            public void call(Object o) {
                System.out.println("subscribe Action1 -> call " + o.toString());
            }
        });
    }

    /**
     *
     * 如何有多个 源 Observable，则 zip 会等待最慢的一个 Observable 发射完数据才开始组合这次发射的所有数据。
     *
     * 虽然第二个Observable是每个100毫秒执行的, 使用zip后,第二个Observable也会等待第一个执行完后再执行.
     *
     * 输出结果:
     *
     * zipString = 0-0
     * zipString = 1-1
     * zipString = 2-2
     * zipString = 3-3
     * zipString = 4-4
     * zipString = 5-5
     * zipString = 6-6
     * zipString = 7-7
     * zipString = 8-8
     * zipString = 9-9
     */
    private static void test1() throws IOException {
        Observable.zip(
                Observable.interval(1000, TimeUnit.MILLISECONDS),
                Observable.interval(100, TimeUnit.MILLISECONDS),
                new Func2<Long, Long, String>() {
            @Override
            public String call(Long a1, Long a2) {
                return a1 + "-" + a2;
            }
        })
        .subscribe(new Action1<String>() {
            @Override
            public void call(String zipString) {
                System.out.println("zipString = " + zipString);
            }
        });

        System.in.read();
    }
}
