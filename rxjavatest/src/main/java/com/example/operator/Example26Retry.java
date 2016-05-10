package com.example.operator;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;

/**
 *
 * retry操作符:

 * retry操作符是当Observable发生错误或者异常时，重新尝试执行Observable的逻辑，
 * 如果经过n次重新尝试执行后仍然出现错误或者异常，则最后回调执行onError方法；当然如果源Observable没有错误或者异常出现，则按照正常流程执行。
 *
 * Created by zhengjiong on 16/5/8.
 */
public class Example26Retry {

    /**
     * 运行结果:
     *
     * Observer -> onNext s=0
     * Observer -> onNext s=0
     * Observer -> onNext s=0
     * Observer -> onError e=err-1
     * @param args
     */
    public static void main(String[] args){
        Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                for (int i=0;i<10;i++) {
                    if (i == 1) {
                        subscriber.onError(new Exception("err-1"));
                    } else {
                        subscriber.onNext(String.valueOf(i));
                    }
                }
            }
        })
        //会重新执行上面的Observable两次, i是从0开始的
        .retry(2)
        .subscribe(new Observer<String>() {
            @Override
            public void onCompleted() {
                System.out.println("Observer -> onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                System.out.println("Observer -> onError e=" + e.getMessage());
            }

            @Override
            public void onNext(String s) {
                System.out.println("Observer -> onNext s=" + s);
            }
        });
    }
}
