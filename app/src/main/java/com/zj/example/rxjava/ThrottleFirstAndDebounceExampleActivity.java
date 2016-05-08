package com.zj.example.rxjava;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;

import com.jakewharton.rxbinding.view.RxView;
import com.jakewharton.rxbinding.widget.RxTextView;

import java.util.concurrent.TimeUnit;

import rx.functions.Action1;

/**
 *
 * 总结: throttleFirst和debounce效果有点不一样
 * 连续点击button1, 每过500毫秒都会触发一次onclick事件,
 * 连续点击button2, 只会在最后停止点击才会触发一次onclick事件
 *
 * 我们通过使用throttleFirst操作符来解决按钮被多次点击的问题。throttleFirst允许设置一个时间长度，
 * 之后它会发送固定时间长度内的第一个事件，而屏蔽其它事件，在间隔达到设置的时间后，重新计时才可以再发送下一个事件示。
 *
 * http://codethink.me/2015/12/20/using-throttlefirst-to-avoid-double-tap/
 *
 * Created by zhengjiong on 16/5/8.
 */
public class ThrottleFirstAndDebounceExampleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_throttle_first_layout);

        Button button1 = (Button) findViewById(R.id.btn);
        Button button2 = (Button) findViewById(R.id.btn2);

        /*RxTextView.textChanges(editText)
                .subscribe(new Action1<CharSequence>() {
                    @Override
                    public void call(CharSequence charSequence) {
                        System.out.println("输入内容 -> " + charSequence);
                    }
                });*/

        RxView.clicks(button1)
                .throttleFirst(500, TimeUnit.MILLISECONDS)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        System.out.println("onclick");
                    }
                });

        /**
         * debounce(): 指定一个时间段，如果在这个时间段内没有接收到下一个 item，就将前面接收到的 items 分为一组，
         * 然后将这个组的最后一个 item 分发出去，其余的丢弃。
         *
         * debounce()函数过滤掉由Observable发射的速率过快的数据；如果在一个指定的时间间隔过去了仍旧没有发射一个，那么它将发射最后的那个。
         * debounce()函数开启一个内部定时器，如果在这个时间间隔内没有新的数据发射，则新的Observable发射出最后一个数据,
         *
         * 重要: 每发射一个数据, 都会重新计时
         * 重要: 每发射一个数据, 每次发射一个数据都会使用debounce设置的时间(500ms)来判断是否过快,而不是所有发射的数据都使用一个时间,
         */
        RxView.clicks(button2)
                .debounce(500, TimeUnit.MILLISECONDS)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        System.out.println("onclick 2");
                    }
                });

    }
}
