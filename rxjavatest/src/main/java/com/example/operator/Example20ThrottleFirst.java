package com.example.operator;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * http://codethink.me/2015/12/20/using-throttlefirst-to-avoid-double-tap/
 *
 * 我们通过使用throttleFirst操作符来解决按钮被多次点击的问题。throttleFirst允许设置一个时间长度，
 * 之后它会发送固定时间长度内的第一个事件，而屏蔽其它事件，在间隔达到设置的时间后，重新计时才可以再发送下一个事件，如下图所示。
 *
 * Created by zhengjiong on 16/5/8.
 */
public class Example20ThrottleFirst {

    /**
     * 使用了take后, 只会接受到interval循环发射9次的值,
     *
     * 用throttleFirst只会发射在两秒内接收到的第一个事件,在到达间隔事件后, 重新计时才可以发射下一个事件.
     *
     * 运行结果:
     * Action1 -> call 0       两秒内会收到0和1, 但是只会发送第一个事件, 所以会在绑定观察者后延迟两秒后发送0,
     * Action1 -> call 3
     * Action1 -> call 6
     */
    public static void main(String[] args){
        Observable.interval(900, TimeUnit.MILLISECONDS)
            /*.map(new Func1<Long, Long>() {
                @Override
                public Long call(Long l) {
                    System.out.println("map -> " + l);
                    return l;
                }
            })*/
            .take(9)
            .throttleFirst(2000, TimeUnit.MILLISECONDS)
            .subscribe(new Action1<Long>() {
                @Override
                public void call(Long l) {
                    System.out.println("Action1 -> call " + l);
                }
            });


        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
