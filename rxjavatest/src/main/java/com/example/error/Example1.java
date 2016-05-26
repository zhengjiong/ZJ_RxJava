package com.example.error;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * observable中出现报错后, 会回调观察者(Subscriber)的onError方法
 * Created by zhengjiong on 16/4/24.
 */
public class Example1 {

    /**
     * 输出结果:
     *
     * onNext s=1
     * onError e= Subscriber error
     *
     *
     * doOnTerminate 在 Observable 结束发射数据之前发生。
     * 不管是因为 onCompleted 还是 onError 导致数据流结束。
     * 另外还有一个 finallyDo 函数在 Observable 结束发射之后发生。
     *
     */
    public static void main(String[] args){
        Observable.just("1", "2")
            .map(new Func1<String, String>() {
                @Override
                public String call(String s) {
                    if (s.equals("1")) {
                        //Observable.error(new NumberFormatException(" number format exception"));
                        //也可以用throw Exception
                        throw new NumberFormatException(" number format exception");
                    } else {

                    }
                    return "1";
                }
            })
            .doOnError(new Action1<Throwable>() {
                @Override
                public void call(Throwable throwable) {
                    System.out.println("doOnError -> call =" + throwable.getMessage());
                }
            })
            /*.doOnTerminate(new Action0() {
                @Override
                public void call() {
                    System.out.println("doOnTerminate -> call");
                }
            })*/
            .subscribe(new Subscriber<String>() {

                @Override
                public void onCompleted() {
                    System.out.println("onCompleted");
                }

                @Override
                public void onError(Throwable e) {
                    System.out.println("onError e=" + e.getMessage());
                }

                @Override
                public void onNext(String s) {
                    System.out.println("onNext s=" + s);
                    throw new NumberFormatException(" Subscriber error ");
                }
            });
    }
}
