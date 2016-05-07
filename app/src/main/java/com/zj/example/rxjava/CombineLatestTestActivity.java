package com.zj.example.rxjava;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.jakewharton.rxbinding.widget.RxTextView;

/**
 * https://segmentfault.com/a/1190000004966620
 *
 * 结合多个Observable发射的最近数据项，当原始Observables的任何一个发射了一条数据时，
 * CombineLatest使用一个函数结合它们最近发射的数据，然后发射这个函数的返回值
 *
 * Created by zhengjiong on 16/5/3.
 */
public class CombineLatestTestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_combine_latest_test_layout);

        //RxTextView.textChanges()
    }
}
