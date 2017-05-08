package com.edu.gridviewdemo;



import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.widget.TextView;

/**
 * 有三种模式,一种是裁判在答题前看题,一种是学生答题,一种答题后看题模式 答题前看题只能显示答案 学生只是一个普通的EditText
 * 
 * 
 * Created by Administrator on 2017/2/8.
 */

public class RefereeAnswerBlank extends TextView {

	/**
	 * 学生作答模式中的已答题状态
	 */
	public static final int FINISH_SUBJECT_STATE = 1;

	/**
	 * 学生作答模式中的已正在作答当前空状态
	 */
	public static final int ON_SUBJECT_STATE = 1;
	/**
	 * 学生作答模式中的未答题状态
	 */
	public static final int UNFINISH_SUBJECT_STATE = 1;
	
	
	float sX;

	float sY;


	/**
	 * 答案空数据类
	 */
	private BlankData blankData;
	// 控件宽高,字体大小,hint字体大小
	private int blankHeight = 60, blankWidth = 60, blankTextSize = 18, blankHintSize = 18;

	public RefereeAnswerBlank(Context context, BlankData blankData) {
		super(context);
		this.blankData = blankData;
		init();

	}

	public RefereeAnswerBlank(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public RefereeAnswerBlank setBlankData(BlankData blankData) {
		this.blankData = blankData;
		showPattern();
		return this;
	}

	public RefereeAnswerBlank setBlankHeight(int blankHeight) {
		this.blankHeight = blankHeight;
		return this;
	}

	public RefereeAnswerBlank setBlankWidth(int blankWidth) {
		this.blankWidth = blankWidth;
		return this;
	}

	public void setUAnswer(String uAnswer) {
		setText(uAnswer);
	}

	public RefereeAnswerBlank setBlankTextSize(int blankTextSize) {
		this.blankTextSize = blankTextSize;
		return this;
	}

	/**
	 * 加载视图
	 */
	private void init() {
		setGravity(Gravity.CENTER);
		setTextSize(blankTextSize);
		// setInputType(InputType.TYPE_NULL);

	}

	void showPattern() {
		switch (blankData.getPattern()) {
		case BlankData.BEFORE_EXAM:
			setText(blankData.getAnswer());
			break;
		case BlankData.IN_EXAM:
			break;
		case BlankData.AFTER_STUDENT_EXAM:
			setText(blankData.getAnswer());

			if (blankData.getAnswerState() == BlankData.WRONG_ANSWER) {
				setTextColor(Color.RED);
			} else if (blankData.getAnswerState() == BlankData.CORRECT_ANSWER) {
			} else {
			}
			break;
		case BlankData.AFTER_REFEREE_EXAM:

			break;
		}

	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		if (blankData == null) {
			super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		} else {
			setMeasuredDimension(blankWidth, blankHeight);

		}
	}
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		  // 如果是按下操作
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
        	sX = event.getX();
        	sY = event.getY();
        }
        return super.onTouchEvent(event);
    }
	

	/**
	 * 确定dialog弹出坐标
	 * 
	 * @return
	 */
	private float getdialogy() {
		// TODO Auto-generated method stub
		if (sY > 400) {
			return sY-200;
		}
		return sY;
	}

	private float getdialogx() {
		// TODO Auto-generated method stub
		if (sX > 800) {
			return sX-500;
		}
		return sX;
	}


}
