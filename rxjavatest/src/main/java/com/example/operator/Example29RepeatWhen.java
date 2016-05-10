package com.example.operator;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * 当.repeat()接收到.onCompleted()事件后触发重订阅。
 * 当.retry()接收到.onError()事件后触发重订阅。
 * <p>
 * 然而，这种简单的叙述尚不能令人满意。试想如果你要实现一个延迟数秒的重订阅该如何去做？
 * 或者想通过观察错误来决定是否应该重订阅呢？这种情况下就需要.repeatWhen()和.retryWhen()的介入了，
 * 因为它们允许你为重试提供自定义逻辑。
 * <p>
 * <p>
 * Created by zhengjiong on 16/5/10.
 */
public class Example29RepeatWhen {

    /**
     *
     * 运行结果:
     * subscribe -> onNext i=1
     * subscribe -> onNext i=2
     * subscribe -> onNext i=1
     * subscribe -> onNext i=2
     * subscribe -> onCompleted
     */
    public static void main(String[] args){
        Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> o) {
                o.onNext(1);
                o.onNext(2);
                o.onCompleted();
            }
        })
        .repeatWhen(new Func1<Observable<? extends Void>, Observable<?>>() {
            @Override
            public Observable<?> call(Observable<? extends Void> completedObservable) {
                //return Observable.just(null);这里直接这样是不会触发重试操作的,必须使用操作符转换一下,flatMap或者直接take也可以,其他操作符应该也可以
                //take(2) 参数确保判断条件 Observable 只发射两个数据（源 Observable onCompleted2次）然后结束。所以当源 Observable 出现2次onCompleted以后就不再重试了。
                return completedObservable.take(2).flatMap(new Func1<Void, Observable<?>>() {
                    @Override
                    public Observable<?> call(Void aVoid) {
                        return Observable.just("开始重试操作");
                    }
                });
            }
        })
        .subscribe(new Observer<Integer>() {
            @Override
            public void onCompleted() {
                System.out.println("subscribe -> onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                System.out.println("subscribe -> onError e=" + e.getMessage());
            }

            @Override
            public void onNext(Integer i) {
                System.out.println("subscribe -> onNext i=" + i);
            }
        });
    }
}
