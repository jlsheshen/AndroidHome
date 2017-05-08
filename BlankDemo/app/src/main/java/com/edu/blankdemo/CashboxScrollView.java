package com.edu.blankdemo;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ScrollView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/2/9.
 */

public class CashboxScrollView extends ScrollView {

    /**
     * 七个gridview集合
     */
    private List<CashboxGridView> gridViewList;



    /**
     * 答题模式
     */
    private int pattern;
    /**
     * 数据集合
     */
    List<BlankData> datas ;

    public CashboxScrollView setPattern(int pattern) {
        this.pattern = pattern;
        return this;
    }

    public CashboxScrollView setDatas(List<BlankData> datas) {
        this.datas = datas;
        init();
        return this;
    }

    public CashboxScrollView(Context context) {
        super(context);
        init();
    }



    public CashboxScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.view_cashbox,this);
        gridViewList = new ArrayList<>();
        CashboxGridView gridView = (CashboxGridView) findViewById(R.id.hundred_gv);
        gridViewList.add(gridView);
        CashboxGridView gridView2 = (CashboxGridView) findViewById(R.id.fifty_gv);
        gridViewList.add(gridView2);
        CashboxGridView gridView3 = (CashboxGridView) findViewById(R.id.twenty_gv);
        gridViewList.add(gridView3);
        CashboxGridView gridView4 = (CashboxGridView) findViewById(R.id.ten_gv);
        gridViewList.add(gridView4);
        CashboxGridView gridView5 = (CashboxGridView) findViewById(R.id.five_gv);
        gridViewList.add(gridView5);
        CashboxGridView gridView6 = (CashboxGridView) findViewById(R.id.one_gv);
        gridViewList.add(gridView6);
       CashboxAdapter adapter = new CashboxAdapter(getContext());
        for (CashboxGridView cashboxGridView : gridViewList) {
            adapter.setDatas(datas);
            cashboxGridView.setAdapter(adapter);
            cashboxGridView.setNumColumns(7);
        }

    }

}
