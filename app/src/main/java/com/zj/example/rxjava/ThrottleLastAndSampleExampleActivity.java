package com.zj.example.rxjava;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Sample(别名throttleLast):定期发射Observable最近发射的数据项
 *
 * Sample操作符定时查看一个Observable，然后发射自上次采样以来它最近发射的数据。
 *
 * 在某些实现中，有一个ThrottleFirst操作符的功能类似，但不是发射采样期间的最近的数据，而是发射在那段时间内的第一项数据。
 *
 * RxJava将这个操作符实现为sample和throttleLast。
 *
 * 注意：如果自上次采样以来，原始Observable没有发射任何数据，这个操作返回的Observable在那段时间内也不会发射任何数据。
 *
 * Created by zhengjiong on 16/5/11.
 */
public class ThrottleLastAndSampleExampleActivity extends AppCompatActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample_layout);

        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                test1();
            }
        });
    }

    /**
     * System.out: for i=1
     * System.out: for i=2
     * System.out: for i=3
     * System.out: Next: 3          //sample(3000), 采样的时候只会采样3000毫秒的最后一个,所以不会输出1和2
     * System.out: for i=4
     * System.out: for i=5
     * System.out: for i=6
     * System.out: Next: 6
     * System.out: for i=7
     * System.out: for i=8
     * System.out: Next: 8
     * System.out: Next: 9
     * System.out: Sequence complete.
     */
    private void test1() {
        Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                if (subscriber.isUnsubscribed()) {
                    return;
                }

                try {
                    //前8个数字产生的时间间隔为1秒，后一个间隔为2秒
                    for (int i = 1; i < 9; i++) {
                        System.out.println("for i=" + i);
                        subscriber.onNext(i);
                        Thread.sleep(1000);
                    }
                    Thread.sleep(2000);
                    subscriber.onNext(9);
                    subscriber.onCompleted();
                } catch (Exception e) {
                    subscriber.onError(e);
                }
            }
        })
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .sample(3000, TimeUnit.MILLISECONDS)  //采样间隔时间为3000毫秒
        .subscribe(new Subscriber<Integer>() {
            @Override
            public void onNext(Integer item) {
                System.out.println("Next: " + item);
            }

            @Override
            public void onError(Throwable error) {
                System.err.println("Error: " + error.getMessage());
            }

            @Override
            public void onCompleted() {
                System.out.println("Sequence complete.");
            }
        });
    }
}
