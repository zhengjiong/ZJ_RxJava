package c.rxandroid_0240;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.AndroidSubscriptions;
import rx.android.app.AppObservable;
import rx.android.view.OnClickEvent;
import rx.android.view.ViewObservable;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.subscriptions.CompositeSubscription;

import android.util.*;

public class ButtonClickDemo1Activity extends RxBaseActivity {
    public final CompositeSubscription mCompositeSubscription = new CompositeSubscription();
    private Subscription mSubscription;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bind_activity_9);

        final Button button = (Button) findViewById(R.id.btn);
        final Button button2 = (Button) findViewById(R.id.btn_unsubscribe);

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //mSubscription.unsubscribe();
                mCompositeSubscription.clear();
            }
        });
        /*bindUi(RxUtil.click(button), new Action1<Object>() {
            @Override
            public void call(Object o) {
                Log.i("zj", "Action1 call ");
            }
        });*/

        mSubscription = AppObservable.bindActivity(this, Observable.create(new Observable.OnSubscribe<Object>() {
            @Override
            public void call(final Subscriber<? super Object> subscriber) {
                Log.i("zj", "call ........");
                /**
                 * 当执行mCompositeSubscription.clear();的时候
                 * 会在主线程中执行Log.i("zj", "call -----------------");
                 */
                subscriber.add(AndroidSubscriptions.unsubscribeInUiThread(new Action0() {
                    @Override
                    public void call() {
                        Log.i("zj", "call -----------------");
                        if (button != null) button.setOnClickListener(null);
                    }
                }));
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(final View v) {
                        v.setEnabled(false);
                        v.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                v.setEnabled(true);
                            }
                        }, 350);
                        Log.i("zj", "onNext");
                        subscriber.onNext(new Object());
                    }
                });
            }
        }))
        .subscribe(new Subscriber<Object>() {
            @Override
            public void onCompleted() {
                Log.i("zj", "Subscriber onCompleted ");
            }

            @Override
            public void onError(Throwable e) {
                Log.i("zj", "Subscriber onError ");
            }

            @Override
            public void onNext(Object o) {
                Log.i("zj", "Subscriber onNext ");
            }
        });
        mCompositeSubscription.add(mSubscription);
    }

    @Override
    public void error(String error) {

    }

    /*public static Observable<Object> click(final View view) {
        return Observable.create(new Observable.OnSubscribe<Object>() {
            @Override
            public void call(final Subscriber<? super Object> subscriber) {
                subscriber.add(AndroidSubscriptions.unsubscribeInUiThread(new Action0() {
                    @Override
                    public void call() {
                        if (view != null) view.setOnClickListener(null);
                    }
                }));
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(final View v) {
                        v.setEnabled(false);
                        v.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                v.setEnabled(true);
                            }
                        }, 350);

                        subscriber.onNext(new Object());
                    }
                });
            }
        });
    }*/

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mCompositeSubscription.clear();
    }
}
