package com.edu.blankdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
//    public static final int FLAG_HOMEKEY_DISPATCHED = 0x80000000; //需要自己定义标志

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LinearLayout layout2 = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.activity_main, null);

        setContentView(layout2);
//        this.getWindow().setFlags(FLAG_HOMEKEY_DISPATCHED, FLAG_HOMEKEY_DISPATCHED);//关键代码
        RecyclerView recyclerView;
        Intent intent = new Intent();
        intent.setAction("com.edu.hide.systemui");
        this.sendBroadcast(intent);

//        this.findViewById(R.id.tv).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent();
//                intent.setAction("com.edu.show.systemui");
//                MainActivity.this.sendBroadcast(intent);
//            }
//        });


        List<BlankData> datas = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            BlankData blankData = new BlankData();
        blankData.setAnswer("-2").setAnswerState(BlankData.WRONG_ANSWER).setNumber(12).setPattern(BlankData.BEFORE_EXAM).setUserAnswer("-1");
        datas.add(blankData);
        }
        CashboxScrollView view  = (CashboxScrollView) findViewById(R.id.cashbox_view);
        view.setDatas(datas);
//        答案空进行试验
//        BlankData blankData = new BlankData();
//        blankData.setAnswer("-2").setAnswerState(BlankData.WRONG_ANSWER).setNumber(12).setPattern(BlankData.BEFORE_EXAM).setUserAnswer("-1");
//
//        AnswerBlank button = new AnswerBlank(this, blankData);
//        layout2.addView(button);
//        AnswerBlank answerBlank;
//        blankData.setAnswer("+4").setAnswerState(BlankData.WRONG_ANSWER).setNumber(13).setPattern(BlankData.AFTER_EXAM).setUserAnswer("-3");
//        AnswerBlank button2 =  new AnswerBlank(this, blankData);
//        button.setBlankData(blankData);
//        layout2.addView(button2);



    }


}
