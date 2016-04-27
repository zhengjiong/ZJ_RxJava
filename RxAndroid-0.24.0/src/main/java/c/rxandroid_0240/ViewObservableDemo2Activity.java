package c.rxandroid_0240;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

import butterknife.ButterKnife;
import butterknife.InjectView;
import rx.Observable;
import rx.Subscriber;
import rx.android.view.ViewObservable;
import rx.functions.Action1;

/**
 * 此demo未完成
 * Created by zhengjiong on 16/4/27.
 */
public class ViewObservableDemo2Activity extends AppCompatActivity {

    @InjectView(R.id.edittext)
    EditText edittext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_observable_demo2_layout);
        ButterKnife.inject(this);

        //此demo未完成
        ViewObservable.bindView(edittext, Observable.create(new Observable.OnSubscribe<Object>() {
            @Override
            public void call(Subscriber<? super Object> subscriber) {
                System.out.println("subscriber -> onNext");
                subscriber.onNext("1");
            }
        }))
        .subscribe(new Action1<Object>() {
            @Override
            public void call(Object o) {
                System.out.println("Action1 -> call " + o.toString());
            }
        });
    }
}
