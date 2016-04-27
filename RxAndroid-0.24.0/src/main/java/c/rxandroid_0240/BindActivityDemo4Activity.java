package c.rxandroid_0240;

import android.os.Bundle;
import android.support.annotation.IntegerRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import rx.Observable;
import rx.Subscriber;
import rx.android.app.AppObservable;
import rx.functions.Action1;
import rx.subjects.BehaviorSubject;

/**
 * Created by zhengjiong on 16/4/27.
 */
public class BindActivityDemo4Activity extends AppCompatActivity {

    @InjectView(R.id.btn_1)
    Button btn1;
    @InjectView(R.id.txt)
    TextView txt;

    int i;
    BehaviorSubject<Integer> mBehaviorSubject = BehaviorSubject.create();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bind_activity_demo_4);
        ButterKnife.inject(this);

        AppObservable.bindActivity(BindActivityDemo4Activity.this, mBehaviorSubject)
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        System.out.println("Action1 -> call " + integer);
                        txt.setText("onNext->" + integer.toString());
                    }
                });
    }

    @OnClick({R.id.btn_1})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_1:
                mBehaviorSubject.onNext(++i);
                break;
        }
    }
}
