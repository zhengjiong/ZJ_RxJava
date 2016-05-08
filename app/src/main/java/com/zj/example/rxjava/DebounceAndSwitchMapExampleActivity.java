package com.zj.example.rxjava;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

import com.jakewharton.rxbinding.widget.RxTextView;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Scheduler;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by zhengjiong on 16/5/7.
 */
public class DebounceAndSwitchMapExampleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_debounce_layout);

        EditText editText = (EditText) findViewById(R.id.edit_text);

        //Observable.switchOnNext(RxTextView.textChanges(editText)
        Observable.switchOnNext(createIntervalObservalble(500)
                .map(new Func1<Long, Observable<String>>() {
                    @Override
                    public Observable<String> call(Long l1) {
                        System.out.println("map -> call = " + l1);
                        return Observable.interval(600, TimeUnit.MILLISECONDS, Schedulers.io()).map(new Func1<Long, String>() {
                            @Override
                            public String call(Long l2) {
                                return "l1=" + l1;
                            }
                        });
                    }
                })
        )
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Action1<String>() {
            @Override
            public void call(String aLong) {
                System.out.println("subscribe -> subscribe =" + aLong);
            }
        });
                /*.subscribeOn(AndroidSchedulers.mainThread())//上面的textChanges会在主线程中执行,不然会报错
                .debounce(1000, TimeUnit.MILLISECONDS, Schedulers.newThread())
                .subscribeOn(Schedulers.io())//上面的switchMap会在io线程中执行
                .map(new Func1<CharSequence, Observable<String>>() {
                    @Override
                    public Observable<String> call(CharSequence charSequence) {
                        String inputContent = charSequence.toString();
                        System.out.println("map call =" + inputContent);

                        return Observable.create(new Observable.OnSubscribe<String>() {
                            @Override
                            public void call(Subscriber<? super String> subscriber) {
                                timeOperation1(inputContent);
                                System.out.println("create call onNext =" + inputContent);
                                subscriber.onNext(inputContent);
                            }
                        });
                    }
                }))*/

            /*.switchMap(new Func1<CharSequence, Observable<String>>() {
                @Override
                public Observable<String> call(CharSequence charSequence) {
                    String inputContent = charSequence.toString();
                    return Observable.create(new Observable.OnSubscribe<String>() {
                        @Override
                        public void call(Subscriber<? super String> subscriber) {
                            System.out.println("Observable.create inputContent=" + inputContent);
                            if (inputContent.equals("1")) {
                                timeOperation1(inputContent);
                            } else if (inputContent.equals("12")) {
                                timeOperation2(inputContent);
                            }
                            subscriber.onNext(inputContent);
                        }
                    });
                }
            })*/
            /*.subscribeOn(Schedulers.newThread())//上面的switchMap会在io线程中执行
            .observeOn(AndroidSchedulers.mainThread())*/
            /*.onErrorResumeNext(new Func1<Throwable, Observable<? extends String>>() {
                @Override
                public Observable<? extends String> call(Throwable throwable) {
                    return Observable.just(throwable.getMessage());
                }
            })*/
            /*.subscribe(new Action1<CharSequence>() {
                @Override
                public void call(CharSequence charSequence) {
                    //Thread[main,5,main],主线程中执行

                    System.out.println("Action1 -> call " + charSequence + " | currentThread.getId="+ Thread.currentThread().getId());
                }
            }, new Action1<Throwable>() {
                @Override
                public void call(Throwable throwable) {
                    System.out.println("Action1<Throwable> -> call " + throwable.getMessage());
                }
            });*/
    }

    /**
     *
     * @param interval
     * @return
     */
    Observable<Long> createIntervalObservalble(int interval){
        return Observable.interval(interval, TimeUnit.MILLISECONDS, Schedulers.io());
    }

    /**
     * 7秒耗时操作
     */
    void timeOperation1(String content){
        /*new Thread(new Runnable() {
            @Override
            public void run() {
                long start = System.currentTimeMillis();
                for (long i = 0; i < 10000000000L;i++) {
                }
                long end = System.currentTimeMillis();
                System.out.println("timeOperation1 " + (end - start));
            }
        }).start();*/
        long start = System.currentTimeMillis();
        System.out.println("timeOperation1 : " + content + " : start = " + start);
        for (long i = 0; i < 10000000000L;i++) {
        }
        long end = System.currentTimeMillis();
        System.out.println("timeOperation1 : " + content + " : end - start = " + (end - start));
    }

    /**
     * 2秒耗时操作
     */
    void timeOperation2(String content){
        /*new Thread(new Runnable() {
            @Override
            public void run() {
                long start = System.currentTimeMillis();
                for (long i = 0; i < 500000000L;i++) {
                }
                long end = System.currentTimeMillis();
                System.out.println("timeOperation2 " + (end - start));
            }
        }).start();*/
        long start = System.currentTimeMillis();
        System.out.println(content + " : start = " + start);
        for (long i = 0; i < 500000000L;i++) {
        }
        long end = System.currentTimeMillis();
        System.out.println("timeOperation2 : " + (end - start));
        System.out.println("timeOperation2 : " + content + " : end - start = " + (end - start));


    }
}
