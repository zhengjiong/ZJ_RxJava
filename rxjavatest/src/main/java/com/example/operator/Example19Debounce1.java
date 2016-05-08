package com.example.operator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 *
 * AndroidDemo查看: DebounceExampleActivity.java
 *
 * debounce(): 指定一个时间段，如果在这个时间段内没有接收到下一个 item，就将前面接收到的 items 分为一组，
 * 然后将这个组的最后一个 item 分发出去，其余的丢弃。
 *
 * debounce()函数过滤掉由Observable发射的速率过快的数据；如果在一个指定的时间间隔过去了仍旧没有发射一个，那么它将发射最后的那个。
 * debounce()函数开启一个内部定时器，如果在这个时间间隔内没有新的数据发射，则新的Observable发射出最后一个数据,
 *
 * 重要: 每发射一个数据, 都会重新计时
 *
 * 当用户输入内容的时候我们发出了一个网络请求然后获得结果：

 * RxTextView.textChanges(searchEditText)
 * .flatMap(Api::searchItems)
 * .subscribe(this::updateList, t->showError());
 *
 * 减少网络请求
 * 以上存在两个问题：
 * 1.每输入一个字母（对的这很坑）比如：用户快速输入了一个“a”，然后“ab”然后“abc”然后又纠正为“ab”并最终想搜索“abe”。这样你就做了5次网络请求。想象一在网速很慢的时候是个什么情况。
 * 2.你还面临一个线程赛跑的问题。比如：用户输入了“a”，然后是“ab”。“ab”的网络调用发生在前而”a“的调用发生在后。那样的话updateList() 将根据 “a”的请求结果来执行。
 *
 * 重要: 每发射一个数据, 每次发射一个数据都会使用debounce设置的时间(900ms)来判断是否过快
 *
 * Created by zhengjiong on 16/5/7.
 */
public class Example19Debounce1 {

    public static void main(String[] args){
        //test1();
        test2();

    }

    /**
     * 每隔0.5秒循环一次, 一共循环9次
     *
     * 最后只会输出一句:Action1 -> call 9
     *
     * 因为interval设置的是500ms, 而debounce设置的是900, 所以只会输出最后的值(相当于interval输入过快,时间没有超过900ms)
     * 每次发射一个数据都会使用debounce设置的时间(900ms)来判断是否过快,而不是所有发射的数据都使用一个时间,
     *
     *
     * 运行结果:
     *
     * Action1 -> call 9
     */
    private static void test2() {
        List<Integer> numList = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);

        Observable.interval(500, TimeUnit.MILLISECONDS)
                .take(9)
                .debounce(900, TimeUnit.MILLISECONDS)
                .flatMap(new Func1<Long, Observable<Integer>>() {
                    @Override
                    public Observable<Integer> call(Long i) {
                        return Observable.just(numList.get(i.intValue()));
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.immediate())
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        System.out.println("Action1 -> call " + integer);
                    }
                });


        try {
            System.in.read();//interval()需要System.in.read()才能打印出数据
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 输出结果:
     * 9
     */
    private static void test1() {
        List<Integer> numList = new ArrayList<>();
        for (int i = 0; i< 10;i++) {
            numList.add(i);
        }

        Observable.from(numList)
            .debounce(100, TimeUnit.MILLISECONDS)
            .subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer num) {
                System.out.println(num);
            }
        });
    }
}
