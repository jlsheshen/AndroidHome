package com.baiyuliang.listview.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Build;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.baiyuliang.listview.R;


/**
 * 
 * Date: 2014-06-19 <br>
 * 
 * @author byl
 */
public class MyProgressDialog extends Dialog {

	private LoadingDialogView loadingDialogView;
	private TextView tv;
	private AnimationDrawable loadAnimation;
	private boolean cancelable = true;


	public MyProgressDialog(Context context) {
		super(context, R.style.Dialog_bocop);
		init();
	}

	private void init() {
		View contentView = View.inflate(getContext(), R.layout.loading_dialog, null);
		 if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {    
			   Window win = getWindow();    
		       WindowManager.LayoutParams winParams = win.getAttributes();    
		       winParams.flags |= WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;    
		       win.setAttributes(winParams);    
	       }
		setContentView(contentView);
		
		contentView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (cancelable) {
					dismiss();
				}
			}
		});
		loadingDialogView= (LoadingDialogView) findViewById(R.id.loadingView);
		loadingDialogView.setBackgroundResource(R.drawable.anim_loading);
		loadAnimation= (AnimationDrawable) loadingDialogView.getBackground();
		tv = (TextView) findViewById(R.id.tv);
		getWindow().setWindowAnimations(R.anim.alpha_in);
	}
	

	@Override
	public void show() {
		loadAnimation.start();
		super.show();
	}
	
	@Override
	public void dismiss() {
		loadAnimation.stop();
		super.dismiss();
	}
	
	
	@Override
	public void setCancelable(boolean flag) {
		cancelable = flag;
		super.setCancelable(flag);
	}
	
	@Override
	public void setTitle(CharSequence title) {
		tv.setText(title);
	}
	
	public void setMessage(String title) {
		tv.setText(title);
	}
	
	@Override
	public void setTitle(int titleId) {
		setTitle(getContext().getString(titleId));
	}
}
