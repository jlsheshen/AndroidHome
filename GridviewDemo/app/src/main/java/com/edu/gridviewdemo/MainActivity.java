package com.edu.gridviewdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{
    GridView gridView;
    CashboxGvAdapter adapter;
    RefereeJudgmentPopupwindow ppw;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_request_check);
        List<BlankData> datas = new ArrayList<BlankData>();
        for (int i = 0; i < 100; i++) {
            BlankData blankData = new BlankData();
            blankData.setNumber(i).setPattern(BlankData.AFTER_REFEREE_EXAM);
            datas.add(blankData);
        }
        gridView = (GridView) findViewById(R.id.request_check_gv);

        adapter = new CashboxGvAdapter(this);
        adapter.setDatas(datas);
        adapter.setPattern(BlankData.AFTER_REFEREE_EXAM);
        gridView.setNumColumns(7);
        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener(this);

    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        // TODO Auto-generated method stub
        Log.d("----------", "view" +view.getBottom() + "view" + view.getTop());
//		adapter.showDialog(view.getTop(),view.getBottom() ,view.getRight(),view.getLeft());
        ppw= RefereeJudgmentPopupwindow.getInstance(this);
        ppw.bindData(adapter.getData(position),position);
        ppw.showPopupWindow(view);
    }
}
