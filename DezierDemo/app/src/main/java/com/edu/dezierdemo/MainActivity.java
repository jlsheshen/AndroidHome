package com.edu.dezierdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    @BindView(R.id.jump_btn)
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AnnotationUtils.bindView(this);
        button.setOnClickListener(this);


        AdhesionLayout adhesionLayout = (AdhesionLayout) findViewById(R.id.adhesion);
        adhesionLayout.setMaxAdherentLength(200);
        adhesionLayout.setOnAdherentListener(new AdhesionLayout.OnAdherentListener() {
            @Override
            public void onDismiss() {

            }
        });
    }


    @Override
    public void onClick(View v) {
        Intent intent  = new Intent(this,SecondActivity.class);
        startActivity(intent);
    }
}
