package com.edu.gridviewdemo;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.PopupWindow;

public class RefereeJudgmentPopupwindow extends PopupWindow implements OnCheckedChangeListener {
	CheckBox bundleUpCb, sealCb;
	RefereeCheckListener refereeCheckListener;
	View inflater;
	Context context;
	int position;
	private static RefereeJudgmentPopupwindow intance;
	public static RefereeJudgmentPopupwindow getInstance(Context context){
		if(intance == null){
			intance = new RefereeJudgmentPopupwindow(context);
		}
		return intance;
	}
	
	private RefereeJudgmentPopupwindow(Context context) {
		super(context);
		this.context = context;
		inflater = LayoutInflater.from(context).inflate(R.layout.dialog_referee_judgment_point, null);
		setContentView(inflater);
		// TODO Auto-generated constructor stub
		init();
	}

	public void setRefereeCheckListener(RefereeCheckListener refereeCheckListener) {
		this.refereeCheckListener = refereeCheckListener;
	}

	private void init() {
		bundleUpCb = (CheckBox) inflater.findViewById(R.id.bundle_up_cb);
		sealCb = (CheckBox) inflater.findViewById(R.id.seal_cb);
		
		sealCb.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Log.d("----", "setOnClickListener");
				
			}
		});
		bundleUpCb.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				Log.d("----", "onCheckedChanged");

			}
		});
		sealCb.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				Log.d("----", "onCheckedChanged");

			}
		});
		inflater.findViewById(R.id.bundle_up_tv).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.d("----", "收到点击事件");
			}
		});
		
		//设置可以获取焦点，否则弹出菜单中的EditText是无法获取输入的
//	    this.setFocusable(true);
//	    //这句是为了防止弹出菜单获取焦点之后，点击activity的其他组件没有响应
//	    this.setBackgroundDrawable(new BitmapDrawable());


	}

	/** * 显示popupWindow * * @param parent */
	public void showPopupWindow(View parent) {
		if (!this.isShowing()) {
			// 以下拉方式显示popupwindow
			this.showAsDropDown(parent);
		} else {
			this.dismiss();
		}
	}
	public void bindData(BlankData data, int position){
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);

		
		// 设置SelectPicPopupWindow弹出窗体的宽
		 this.setWidth(200);
		// 设置SelectPicPopupWindow弹出窗体的高
		 this.setHeight(100);
		// 设置SelectPicPopupWindow弹出窗体可点击 
	 this.setFocusable(true);
		 this.setOutsideTouchable(true);
		// 刷新状态 this.update(); // 实例化一个ColorDrawable颜色为半透明
		 ColorDrawable dw = new ColorDrawable(0000000000);
		// 点back键和其他地方使其消失,设置了这个才能触发OnDismisslistener ，设置其他控件变化等操作
		 this.setBackgroundDrawable(dw);
		// 设置SelectPicPopupWindow弹出窗体动画效果
//		 this.setAnimationStyle(R.style.AnimationPreview);
		this.position  = position;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}


	public interface RefereeCheckListener {
		// 綁扎监听
		public void bundleUpListener(boolean isBundle);

		public void sealListener(boolean isSeal);

	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		// TODO Auto-generated method stub
		switch (buttonView.getId()) {
		case R.id.bundle_up_cb:
			Log.d("----", "onCheckedChanged");

			refereeCheckListener.bundleUpListener(isChecked);

			break;
		case R.id.seal_cb:		
			Log.d("----", "onCheckedChanged");

			refereeCheckListener.sealListener(isChecked);
			break;
		}

	}

}
