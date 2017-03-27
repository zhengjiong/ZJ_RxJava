package com.zj.example.rxjava;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Scheduler;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by zhengjiong on 16/5/3.
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_layout);

        Button button1 = (Button) findViewById(R.id.btn1);
        Button button2 = (Button) findViewById(R.id.btn2);
        Button button3 = (Button) findViewById(R.id.btn3);
        Button button4 = (Button) findViewById(R.id.btn4);
        Button button5 = (Button) findViewById(R.id.btn5);
        Button button6 = (Button) findViewById(R.id.btn6);
        Button button7 = (Button) findViewById(R.id.btn7);


        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);
        button5.setOnClickListener(this);
        button6.setOnClickListener(this);
        button7.setOnClickListener(this);

        /**
         * 举个简单的例子，在 RxJava1.x 中的 observeOn， 因为是切换了消费者的线程，
         * 因此内部实现用队列存储事件。在 Android 中默认的 buffersize 大小是16，
         * 因此当消费比生产慢时， 队列中的数目积累到超过16个，就会抛出MissingBackpressureException，
         * 初学者很难明白为什么会这样，使得学习曲线异常得陡峭。
         */
        /*Observable.create(
                new Observable.OnSubscribe<String>() {
                    @Override
                    public void call(Subscriber<? super String> subscriber) {
                        subscriber.onNext("1");
                        subscriber.onNext("2");
                        subscriber.onNext("3");
                        subscriber.onNext("4");
                        subscriber.onNext("5");
                        subscriber.onNext("6");
                        subscriber.onNext("7");
                        subscriber.onNext("8");
                        subscriber.onNext("9");
                        subscriber.onNext("10");
                        subscriber.onNext("11");
                        subscriber.onNext("12");
                        subscriber.onNext("13");
                        subscriber.onNext("14");
                        subscriber.onNext("15");
                        subscriber.onNext("16");
                        subscriber.onNext("17");
                    }
                })
                .observeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        SystemClock.sleep(3000);
                        System.out.println("onNext " + s);
                    }

                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        System.out.println("onError " + throwable.getMessage());
                    }

                }, new Action0() {
                    @Override
                    public void call() {
                        System.out.println("onComplete");
                    }
                });*/
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn1:
                startActivity(new Intent(MainActivity.this, SchedulersTestActivity.class));
                break;
            case R.id.btn2:
                startActivity(new Intent(MainActivity.this, CombineLatestTestActivity.class));
                break;
            case R.id.btn3:
                startActivity(new Intent(MainActivity.this, DebounceExampleActivity.class));
                break;
            case R.id.btn4:
                startActivity(new Intent(MainActivity.this, DebounceAndSwitchMapExampleActivity.class));
                break;
            case R.id.btn5:
                startActivity(new Intent(MainActivity.this, ThrottleFirstAndDebounceExampleActivity.class));
                break;
            case R.id.btn6:
                startActivity(new Intent(MainActivity.this, ThrottleLastAndSampleExampleActivity.class));
                break;
            case R.id.btn7:
                startActivity(new Intent(MainActivity.this, ObservableConcatVsFlatMapSampleActivity.class));
                break;
        }
    }


}
