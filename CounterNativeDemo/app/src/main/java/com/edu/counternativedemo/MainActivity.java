package com.edu.counternativedemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    CounterNative cn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cn = new CounterNative();
        findViewById(R.id.button).setOnClickListener(this);
        findViewById(R.id.button2).setOnClickListener(this);
        findViewById(R.id.button3).setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button:
            cn.nativeExec(55);

                break;
            case R.id.button2:
                CounterNative.nativeExecute(66);
                break;
            case R.id.button3:
                cn.nativeSetup();
//                finish();
                break;

        }

    }
}
