package c.rxandroid_0240;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by zhengjiong on 16/4/26.
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_layout);
        ButterKnife.inject(this);
    }


    @OnClick({R.id.btn_1, R.id.btn_2, R.id.btn_3, R.id.btn_4, R.id.btn_5, R.id.btn_6, R.id.btn_7})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_1:
                startActivity(new Intent(this, BindActivityDemo1Activity.class));
                break;
            case R.id.btn_2:
                startActivity(new Intent(this, BindActivityDemo2Activity.class));
                break;
            case R.id.btn_3:
                startActivity(new Intent(this, BindActivityDemo3Activity.class));
                break;
            case R.id.btn_4:
                startActivity(new Intent(this, ButtonClickDemo1Activity.class));
                break;
            case R.id.btn_5:
                startActivity(new Intent(this, ViewObservableDemo1Activity.class));
                break;
            case R.id.btn_6:
                startActivity(new Intent(this, ViewObservableDemo2Activity.class));
                break;
            case R.id.btn_7:
                startActivity(new Intent(this, BindActivityDemo4Activity.class));
                break;
        }
    }
}
