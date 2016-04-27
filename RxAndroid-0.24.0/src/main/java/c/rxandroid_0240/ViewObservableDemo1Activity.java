package c.rxandroid_0240;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import rx.android.view.OnClickEvent;
import rx.android.view.ViewObservable;
import rx.functions.Action1;

/**
 * ViewObservable,使用它可以给View添加了一些绑定。
 * 如果你想在每次点击view的时候都收到一个事件，可以使用ViewObservable.clicks()，
 * 或者你想监听TextView的内容变化，可以使用ViewObservable.text()。
 *
 * 运行结果:
 * clicks btn1 Action1 -> call
 *
 * 只有ViewObservable.click订阅的观察者才可以收到点击信息,除非在ViewObservable.clicks(btn1)下面重新setOnClickListener,这样
 * 就只会收到222setOnClickListener click
 *
 * Created by zhengjiong on 16/4/27.
 */
public class ViewObservableDemo1Activity extends AppCompatActivity {

    @InjectView(R.id.btn1)
    Button btn1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_observable_layout);
        ButterKnife.inject(this);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("111setOnClickListener click");
            }
        });

        ViewObservable.clicks(btn1)
                .subscribe(new Action1<OnClickEvent>() {
                    @Override
                    public void call(OnClickEvent onClickEvent) {
                        System.out.println("clicks btn1 Action1 -> call");
                    }
                });

        /*btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("222setOnClickListener click");
            }
        });*/

    }

    @OnClick({R.id.btn1})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn1:
                System.out.println("btn1 click");
                break;
        }
    }
}
