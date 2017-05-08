package com.edu.gridviewdemo;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.GridView;

/**
 * Created by Administrator on 2017/2/9.
 */

public class CashboxGridView extends GridView {
    public CashboxGridView(Context context) {
        super(context);
    }

    public CashboxGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setEnabled(false);
    }

    public CashboxGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE>>2,MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }


}
