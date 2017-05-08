package com.edu.blankdemo;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.EditText;

/**
 * 有三种模式,一种是裁判在答题前看题,一种是学生答题,一种答题后看题模式
 * 答题前看题只能显示答案
 * 学生只是一个普通的EditText
 *
 *
 * Created by Administrator on 2017/2/8.
 */

public class AnswerBlank extends EditText {
    /**
     * 答案空数据类
     */
    private BlankData blankData;
    //控件宽高,字体大小,hint字体大小
    private int blankHeight = 60,blankWidth = 60,blankTextSize = 18, blankHintSize = 18;


    public AnswerBlank(Context context,BlankData blankData) {
        super(context);
        this.blankData = blankData;
        init();
    }


    public AnswerBlank(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    public AnswerBlank setBlankData(BlankData blankData) {
        this.blankData = blankData;
        init();
        return this;
    }

    public AnswerBlank setBlankHeight(int blankHeight) {
        this.blankHeight = blankHeight;
        return this;
    }

    public AnswerBlank setBlankWidth(int blankWidth) {
        this.blankWidth = blankWidth;
        return this;
    }

    public AnswerBlank setBlankTextSize(int blankTextSize) {
        this.blankTextSize = blankTextSize;
        return this;
    }

    private void init() {
        setBackground(getResources().getDrawable(R.drawable.shape_default_answer_blank));
        setGravity(Gravity.CENTER);
        setHintTextColor(Color.GRAY);
        setTextSize(blankTextSize);
        switch (blankData.getPattern()){
            case BlankData.BEFORE_EXAM:
                setFocusable(false);
                setText(blankData.getAnswer());
                break;
            case BlankData.IN_EXAM:
                setHint(String.valueOf(blankData.getNumber()));
                break;
            case BlankData.AFTER_EXAM:
                setFocusable(false);
                setText(blankData.getAnswer());

                if (blankData.getAnswerState() == BlankData.WRONG_ANSWER){
                    setTextColor(Color.RED);
                }else if (blankData.getAnswerState() == BlankData.CORRECT_ANSWER){

                }else {

                }
                break;
        }
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(blankWidth, blankHeight);
    }
}
