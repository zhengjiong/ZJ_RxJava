package com.example.operator;

import java.util.Arrays;
import java.util.List;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by zhengjiong on 16/5/3.
 */
public class Example14MergeDelayError {

    public static void main(String[] args){
        //test1();
        //test2();
        test3();
    }

    /**
     * Subscriber -> call onNext 1
     * Subscriber -> call onNext 2
     * Subscriber -> call onNext 3
     * Subscriber -> call onNext 4
     * Subscriber -> call onNext 5
     * Subscriber -> call onNext A
     * Subscriber -> call onNext B
     * Subscriber -> call onNext C
     * Subscriber -> call onNext D
     * Subscriber -> call onNext E
     * Subscriber -> call onNext F
     * Subscriber -> call onCompleted
     */
    private static void test1() {
        List<Integer> list1 = Arrays.asList(1, 2, 3, 4, 5);
        List<String> list2 = Arrays.asList("A", "B", "C", "D", "E", "F");

        Observable o1 = Observable.from(list1);
        Observable o2 = Observable.from(list2);

        Observable.merge(o1, o2)
                .subscribe(new Subscriber() {
                    @Override
                    public void onCompleted() {
                        System.out.println("Subscriber -> call onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        System.out.println("Subscriber -> call onError");
                    }

                    @Override
                    public void onNext(Object o) {
                        System.out.println("Subscriber -> call onNext " +o);
                    }
                });
    }

    /**
     * 当o2中发射出一个Error错误的时候, merge会受到onError信息, 之后便结束,
     * subscriber.onNext("三") 和 o3 将不会再执行,如果想在onError后继续执行需要用mergeDelayError(),看test3()
     *
     * 运行结果:
     *
     * Subscriber -> call onNext 1
     * Subscriber -> call onNext 2
     * Subscriber -> call onNext 3
     * Subscriber -> call onNext 4
     * Subscriber -> call onNext 5
     * Subscriber -> call onNext 一
     * Subscriber -> call onNext 二
     * Subscriber -> call onError zhengjiong
     */
    private static void test2() {
        List<Integer> list1 = Arrays.asList(1, 2, 3, 4, 5);
        List<String> list2 = Arrays.asList("A", "B", "C", "D", "E", "F");

        Observable o1 = Observable.from(list1);

        Observable o2 = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext("一");
                subscriber.onNext("二");
                subscriber.onError(new Exception("zhengjiong"));
                //subscriber.onCompleted(); //这里执行onCompleted将不会中断后面的执行, onCompleted回调将在o3执行完成后, 最后执行
                subscriber.onNext("三");
            }

        });

        Observable o3 = Observable.from(list2);

        Observable.merge(o1, o2, o3)
                .subscribe(new Subscriber() {
                    @Override
                    public void onCompleted() {
                        System.out.println("Subscriber -> call onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        System.out.println("Subscriber -> call onError " + e.getMessage());
                    }

                    @Override
                    public void onNext(Object o) {
                        System.out.println("Subscriber -> call onNext " +o);
                    }
                });
    }

    /**
     *
     *
     * o2中使用发射一个错误, 但是使用mergeDelayError也会继续执行剩下的,
     * onError将在o3最后执行完后执行Subscriber的onError方法
     *
     *
     * 运行结果:
     *
     * Subscriber -> call onNext 1
     * Subscriber -> call onNext 2
     * Subscriber -> call onNext 3
     * Subscriber -> call onNext 4
     * Subscriber -> call onNext 5
     * Subscriber -> call onNext 一
     * Subscriber -> call onNext 二
     * Subscriber -> call onNext 三
     * Subscriber -> call onNext A
     * Subscriber -> call onNext B
     * Subscriber -> call onNext C
     * Subscriber -> call onNext D
     * Subscriber -> call onNext E
     * Subscriber -> call onNext F
     * Subscriber -> call onError zhengjiong
     */
    private static void test3() {
        List<Integer> list1 = Arrays.asList(1, 2, 3, 4, 5);
        List<String> list2 = Arrays.asList("A", "B", "C", "D", "E", "F");

        Observable o1 = Observable.from(list1);

        Observable o2 = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext("一");
                subscriber.onNext("二");
                subscriber.onError(new Exception("zhengjiong"));
                subscriber.onNext("三");
            }

        });

        Observable o3 = Observable.from(list2);

        Observable.mergeDelayError(o1, o2, o3)
                .subscribe(new Subscriber() {
                    @Override
                    public void onCompleted() {
                        System.out.println("Subscriber -> call onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        System.out.println("Subscriber -> call onError " + e.getMessage());
                    }

                    @Override
                    public void onNext(Object o) {
                        System.out.println("Subscriber -> call onNext " +o);
                    }
                });
    }
}
