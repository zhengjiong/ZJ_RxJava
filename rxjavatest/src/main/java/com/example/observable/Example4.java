package com.example.observable;

import rx.Observable;
import rx.functions.Action1;

/**
 * Created by zhengjiong on 16/4/26.
 */
public class Example4 {

    /**
     * 这里just创建当前时间的可观察者，也就是说，这个可观察者只发送一个当前时间的数值，
     * 然后就中断退出了。 注意输出的这两个时间，完全是一样的时间，但是我们在代码中有sleep(1000)等待时间的，
     * 应该第二个时间会比第一个时间延迟1000，这是因为时间数值的获得是一次性的。如果我们想获得延迟1000的效果，
     * 就要使用defer了(Example5)
     *
     * 输出结果:
     * 11111111   Action1 -> call 1461646321529
     * 22222222   Action1 -> call 1461646321529
     */
    public static void main(String[] args) throws InterruptedException {
        Observable<Long> nowObservable = Observable.just(System.currentTimeMillis());

        nowObservable.subscribe(new Action1<Long>() {
            @Override
            public void call(Long timeMillis) {
                System.out.println("11111111   Action1 -> call " + timeMillis);
            }
        });

        Thread.sleep(1000);

        nowObservable.subscribe(new Action1<Long>() {
            @Override
            public void call(Long timeMillis) {
                System.out.println("22222222   Action1 -> call " + timeMillis);
            }
        });
    }
}
