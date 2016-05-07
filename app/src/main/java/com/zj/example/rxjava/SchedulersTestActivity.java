package com.zj.example.rxjava;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class SchedulersTestActivity extends AppCompatActivity {

    private TextView txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedulers_test);

        txt = (TextView) findViewById(R.id.txt);


        /**
         * subscribeOn 用来指定 Observable.create 中的代码在那个 Scheduler 中执行。
         *
         * observeOn 控制数据流的另外一端。你的 observer 如何收到事件。
         * 也就是在那个线程中回调 observer 的 onNext/onError/onCompleted 函数。
         */
        Observable.create(new Observable.OnSubscribe<String>() {
                @Override
                public void call(Subscriber<? super String> subscriber) {
                    System.out.println("-----------------------");
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("OnSubscribe call");
                    System.out.println(Thread.currentThread().toString());
                    subscriber.onNext("hello rxJava");
                    System.out.println("-----------------------");
                }
            })
            .subscribeOn(Schedulers.io())//指定观察者运行的线程(io线程)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Action1<String>() {
                @Override
                public void call(String s) {
                    System.out.println("==================");
                    System.out.println("Action1 call");
                    System.out.println(Thread.currentThread().toString());
                    System.out.println("s=" + s);
                    txt.setText(s);
                    System.out.println("==================");
                }
            });


    }
}
