package com.zj.example.rxjava;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func0;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private TextView txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txt = (TextView) findViewById(R.id.txt);


        /**
         * Observable中处理耗时操作
         */
        Observable.create(new Observable.OnSubscribe<String>() {
                @Override
                public void call(Subscriber<? super String> subscriber) {
                    System.out.println("-----------------------");
                    try {
                        Thread.sleep(2000);
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
