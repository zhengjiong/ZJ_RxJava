package com.zj.example.rxjava;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;

import com.jakewharton.rxbinding.widget.RxTextView;

import rx.Observable;
import rx.Subscription;
import rx.functions.Action1;
import rx.functions.Func3;

/**
 * https://segmentfault.com/a/1190000004966620
 *
 * 结合多个Observable发射的最近数据项，当原始Observables的任何一个发射了一条数据时，
 * CombineLatest使用一个函数结合它们最近发射的数据，然后发射这个函数的返回值
 *
 * Created by zhengjiong on 16/5/3.
 */
public class CombineLatestTestActivity extends AppCompatActivity {

    private Subscription mSubscribe;
    private Button mBtnRegister;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_combine_latest_test_layout);

        EditText editUsername = (EditText) findViewById(R.id.edit_username);
        EditText editEmail = (EditText) findViewById(R.id.edit_email);
        EditText editPassword = (EditText) findViewById(R.id.edit_password);

        mBtnRegister = (Button) findViewById(R.id.btn_register);

        //skip(1), 过滤掉程序进入后第一次textChange回调
        Observable<CharSequence> o1 = RxTextView.textChanges(editUsername).skip(1);
        Observable<CharSequence> o2 = RxTextView.textChanges(editEmail).skip(1);
        Observable<CharSequence> o3 = RxTextView.textChanges(editPassword).skip(1);

        mSubscribe = Observable.combineLatest(o1, o2, o3, new Func3<CharSequence, CharSequence, CharSequence, Boolean>() {

            @Override
            public Boolean call(CharSequence charSequence, CharSequence charSequence2, CharSequence charSequence3) {
                boolean isUsernameValid = !TextUtils.isEmpty(charSequence) && charSequence.length() >= 3 && charSequence.length() <= 8;

                if (!isUsernameValid) {
                    editUsername.setError("用户名无效");
                }

                boolean isEmailValid = !TextUtils.isEmpty(charSequence2) && Patterns.EMAIL_ADDRESS.matcher(charSequence2).matches();

                if (!isEmailValid) {
                    editEmail.setError("邮箱无效");
                }

                boolean isPasswordValid = !TextUtils.isEmpty(charSequence3) && charSequence3.length() >= 6 && charSequence3.length() <= 10;

                if (!isPasswordValid) {
                    editPassword.setError("密码无效");
                }

                if (isUsernameValid && isEmailValid && isPasswordValid) {
                    return true;
                }

                return false;
            }
        })
        .subscribe(new Action1<Boolean>() {
            @Override
            public void call(Boolean isValid) {
                if (isValid) {
                    mBtnRegister.setEnabled(true);
                } else {
                    mBtnRegister.setEnabled(false);
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSubscribe.unsubscribe();
    }
}
