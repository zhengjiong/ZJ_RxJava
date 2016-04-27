package c.rxandroid_0240;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.Subscription;
import rx.android.app.AppObservable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * bindActivity()和bindFragment()方法默认使用AndroidSchedulers.mainThread()来执行观察者代码，
 * 这两个方法会在Activity或者Fragment结束的时候通知观察者停止接收
 * <p>
 * Created by zhengjiong on 16/4/26.
 */
public class BindActivityDemo2Activity extends AppCompatActivity {

    private Subscription mSubscription1;
    private Subscription mSubscription2;

    @InjectView(R.id.txt)
    TextView txt;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bind_activity_demo_2);
        ButterKnife.inject(this);
    }

    @OnClick({R.id.btn_1, R.id.btn_2, R.id.btn_3})
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.btn_1:
                test1();
                break;
            case R.id.btn_2:
                test2();
                break;
            case R.id.btn_3:

                break;
        }
    }

    /**
     * 如果使用bindActivity, 在点击test1按钮后3秒之内退出Activity,执行结果如下:,
     * 被观察者onNext("call 2")和onCompleted()之后不会再次进入观察者的onNext和oncompleted方法,
     * 因为使用bindActivity方法将在Activity结束的时候通知观察者停止接收
     *
     * OnSubscribe -> call 1
     * Observer -> onNext
     * OnSubscribe -> call 2
     * OnSubscribe -> onCompleted
     *
     */
    private void test1() {
        mSubscription1 = AppObservable.bindActivity(BindActivityDemo2Activity.this, blockMainThread())
                .subscribeOn(Schedulers.io())//被观察者运行的线程
                //这里不需要设置observeOn, 因为查看bindActivity源码,就已经设置了观察者是在主线程中运行了
                //.observeOn(AndroidSchedulers.mainThread())//观察者在主线程中运行
                .subscribe(new Observer() {//订阅一个观察者
                    @Override
                    public void onCompleted() {
                        System.out.println("Observer -> onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        System.out.println("Observer -> onError");
                    }

                    @Override
                    public void onNext(Object o) {
                        System.out.println("Observer -> onNext ");
                        //这里会在主线程中设置textview的值
                        txt.setText(o.toString());
                    }
                });
    }

    /**
     * 点击test2按钮后3秒之内退出Activity,执行结果如下:,
     * 退出Activity之后,被观察者和观察者依然可以正常收发数据
     *
     * 执行结果:
     * OnSubscribe -> call 1
     * Observer -> onNext
     * onDestroy unsubscribe
     * InterruptedException e
     *
     */
    private void test2() {
        mSubscription2 = blockMainThread()
                .subscribeOn(Schedulers.io())//设置被观察者运行的线程
                .observeOn(AndroidSchedulers.mainThread())//设置观察者在主线程中运行
                .subscribe(new Observer() {//设置一个观察者

                    @Override
                    public void onCompleted() {
                        System.out.println("Observer -> onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        System.out.println("Observer -> onError");
                    }

                    @Override
                    public void onNext(Object o) {
                        System.out.println("Observer -> onNext ");
                        //这里会在主线程中设置textview的值
                        txt.setText(o.toString());
                    }
                });
    }


    /**
     * 耗时方法
     */
    public Observable blockMainThread() {
        return Observable.create(new Observable.OnSubscribe<Object>() {
            @Override
            public void call(Subscriber<? super Object> subscriber) {
                try {
                    String str1 = "OnSubscribe -> call 1";
                    System.out.println(str1);
                    subscriber.onNext(str1);

                    Thread.sleep(3000);

                    String str2 = "OnSubscribe -> call 2";
                    System.out.println(str2);
                    subscriber.onNext(str2);

                    Thread.sleep(500);

                    System.out.println("OnSubscribe -> onCompleted");

                } catch (InterruptedException e) {
                    //e.printStackTrace();
                    System.out.println("InterruptedException e");
                    subscriber.onError(e);//如果已经执行了mSubscription1.unsubscribe(),那观察者还是不会收到onError消息
                }
                subscriber.onCompleted();
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.out.println("onDestroy unsubscribe");
        if (mSubscription1 != null) {
            mSubscription1.unsubscribe();
        }
        if (mSubscription2 != null) {
            mSubscription2.unsubscribe();
        }
    }
}
