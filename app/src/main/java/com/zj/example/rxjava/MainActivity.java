package com.zj.example.rxjava;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by zhengjiong on 16/5/3.
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_layout);

        Button button1 = (Button) findViewById(R.id.btn1);
        Button button2 = (Button) findViewById(R.id.btn2);
        Button button3 = (Button) findViewById(R.id.btn3);
        Button button4 = (Button) findViewById(R.id.btn4);
        Button button5 = (Button) findViewById(R.id.btn5);

        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);
        button5.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn1:
                startActivity(new Intent(MainActivity.this, SchedulersTestActivity.class));
                break;
            case R.id.btn2:
                startActivity(new Intent(MainActivity.this, CombineLatestTestActivity.class));
                break;
            case R.id.btn3:
                startActivity(new Intent(MainActivity.this, DebounceExampleActivity.class));
                break;
            case R.id.btn4:
                startActivity(new Intent(MainActivity.this, DebounceAndSwitchMapExampleActivity.class));
                break;
            case R.id.btn5:
                startActivity(new Intent(MainActivity.this, ThrottleFirstAndDebounceExampleActivity.class));
                break;
        }
    }
}
