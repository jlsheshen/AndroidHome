package com.edu.newviewdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SideTextView fristTv = (SideTextView) findViewById(R.id.frist_referee_stv);

        fristTv.setVisibility(View.VISIBLE);
        fristTv.setLeftTv("校验裁判");
        fristTv.setRightTv("" + 202);
    }
}
