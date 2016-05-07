package com.example.operator;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.functions.Action1;

/**
 * debounce(): 指定一个时间段，如果在这个时间段内没有接收到下一个 item，就将前面接收到的 items 分为一组，
 * 然后将这个组的最后一个 item 分发出去，其余的丢弃。
 *
 * Created by zhengjiong on 16/5/7.
 */
public class Example19Debounce1 {

    public static void main(String[] args){
        test1();


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
