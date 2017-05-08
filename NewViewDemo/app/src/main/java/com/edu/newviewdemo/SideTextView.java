package com.edu.newviewdemo;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SideTextView extends LinearLayout {
	private TextView rightTv,leftTv;

	public SideTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init( attrs);
		// TODO Auto-generated constructor stub
		
	}

	private void init(AttributeSet attrs) {
		// TODO Auto-generated method stub
		LayoutInflater.from(getContext()).inflate(R.layout.view_two_side, this,true);
		rightTv = (TextView) findViewById(R.id.side_left_tv);
		leftTv = (TextView)findViewById(R.id.side_right_tv);
//		TypedArray typedArray = getContext().obtainStyledAttributes(attrs,  R.styleable.SideTextView);  
//		String leftString = typedArray.getString(R.styleable.SideTextView_left_tv);
//		String rightString = typedArray.getString(R.styleable.SideTextView_right_tv);
//		rightTv.setText("" + rightString  );
//		leftTv.setText("" + leftString  );
//		typedArray.recycle();



	}
	public void setRightTv(String string){
		rightTv.setText(string);
	}
	public void setLeftTv(String string){
		leftTv.setText(string);
		
	}

}
