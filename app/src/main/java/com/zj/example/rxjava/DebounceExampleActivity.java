package com.zj.example.rxjava;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.jakewharton.rxbinding.widget.RxTextView;

import java.util.concurrent.TimeUnit;

import rx.functions.Action1;

/**
 * debounce()函数过滤掉由Observable发射的速率过快的数据；如果在一个指定的时间间隔过去了仍旧没有发射一个，那么它将发射最后的那个。
 * debounce()函数开启一个内部定时器，如果在这个时间间隔内没有新的数据发射，则新的Observable发射出最后一个数据,
 *
 * 重要: 每发射一个数据都会使用debounce设置的时间(500)来判断是否过快, 如果在500ms以内将被判定为发射过快而被过滤.
 *
 *
 * Created by zhengjiong on 16/5/7.
 */
public class DebounceExampleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_debounce_layout);

        EditText editText = (EditText) findViewById(R.id.edit_text);

        RxTextView.textChanges(editText)
                .debounce(500, TimeUnit.MILLISECONDS)
                .subscribe(new Action1<CharSequence>() {
                    @Override
                    public void call(CharSequence charSequence) {
                        System.out.println("onTextChanged " + charSequence);
                    }
                });
    }
}
