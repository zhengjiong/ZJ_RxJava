package com.example.operator;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;
import rx.functions.Func2;
import rx.functions.FuncN;

/**
 * CombineLast操作符
 * CombineLatest操作符可以将2~9个Observable发射的数据组装起来然后再发射出来。不过还有两个前提：
 * 1.所有的Observable都发射过数据。
 * 2.满足条件1的时候任何一个Observable发射一个数据，就将所有Observable最新发射的数据按照提供的函数组装起来发射出去。
 *
 * Created by zhengjiong on 16/4/28.
 */
public class Example7CombineLatest {

    /**
     * 运行结果:
     * Observable1 create call onNext 1
     * Observable1 create call onNext 2
     * Observable1 create call onNext 3
     * Observable2 create call onNext 2
     * combineLatest call 3
     * combineLatest call 2
     * combineLatest temp 5
     * subscribe Action1 call 5
     * Observable2 create call onNext 4
     * combineLatest call 3
     * combineLatest call 4
     * combineLatest temp 7
     * subscribe Action1 call 7
     * Observable2 create call onNext 6
     * combineLatest call 3
     * combineLatest call 6
     * combineLatest temp 9
     * subscribe Action1 call 9
     *
     */
    private static void test1() {


        Example7CombineLatest example7 = new Example7CombineLatest();
        example7.combineListObserver().subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer i) {
                System.out.println("subscribe Action1 call " + i);
            }
        });
    }

    /**
     * Observable1 create call onNext 1
     * Observable1 create call onNext 2
     * Observable1 create call onNext 3
     * Observable2 create call onNext 2
     * left:3 right:2
     * CombineLatest:5
     * Observable2 create call onNext 4
     * left:3 right:4
     * CombineLatest:7
     * Observable2 create call onNext 6
     * left:3 right:6
     * CombineLatest:9
     */
    private static void test2(){
        Example7CombineLatest example7 = new Example7CombineLatest();
        example7.combineLatestObserver().subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer i) {
                System.out.println("CombineLatest:" + i);
            }
        });
    }

    private List<Observable<Integer>> list = new ArrayList<>();

    private Observable<Integer> combineListObserver() {

        for (int i = 1; i < 3; i++) {
            list.add(createObserver(i));
        }
        return Observable.combineLatest(list, new FuncN<Integer>() {
            @Override
            public Integer call(Object... args) {
                int temp = 0;
                for (Object i : args) {
                    System.out.println("combineLatest call " + i);
                    temp += (Integer) i;
                }
                System.out.println("combineLatest temp " + temp);
                return temp;
            }
        });
    }

    private Observable<Integer> createObserver(int index) {
        return Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                for (int i = 1; i < 4; i++) {
                    System.out.println("Observable" + index + " create call onNext " + i * index);
                    subscriber.onNext(i * index);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        })/*.subscribeOn(Schedulers.newThread())*/;
    }

    private Observable<Integer> combineLatestObserver() {
        return Observable.combineLatest(createObserver(1), createObserver(2), new Func2<Integer, Integer, Integer>() {
            @Override
            public Integer call(Integer num1, Integer num2) {
                System.out.println("left:" + num1 + " right:" + num2);
                return num1 + num2;
            }
        });
    }



    public static void main(String[] args){
        //test1();
        test2();
    }
}
