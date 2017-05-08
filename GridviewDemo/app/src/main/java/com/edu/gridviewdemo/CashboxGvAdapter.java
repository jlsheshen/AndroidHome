package com.edu.gridviewdemo;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Administrator on 2017/2/9.
 */

public class CashboxGvAdapter extends BaseAdapter  {
	/**
	 * 数据集合
	 */
	private List<BlankData> datas;
	RefereeJudgmentPopupwindow ppw;

	int clickTemp;

	/**
	 * 当前模式
	 */
	private int pattern;
	/**
	 * 当前的item 用于学生模式,可移动的光标
	 */
	private int nowItem = 0;
	/**
	 * 当前的item 用于裁判模式模式,当前item
	 */
	public int nowpos;

	private boolean nowSubject = true;

	private Context context;

	ViewHolder viewHolder;

	public CashboxGvAdapter(Context context) {
		this.context = context;
	}

	public CashboxGvAdapter setDatas(List<BlankData> datas) {
		this.datas = datas;

		return this;
	}

	public CashboxGvAdapter setPattern(int pattern) {
		this.pattern = pattern;
		return this;
	}

	public void setNumber(String number) {

		if (nowSubject) {
			datas.get(nowItem++).setUserAnswer(number);
			// addItem();
			notifyDataSetChanged();
		} else {
			datas.get(nowItem - 1).setUserAnswer(number);
			// nowSubject = true;
			// addItem();
			notifyDataSetChanged();

		}

		// answerBlanks.get(nowItem).setText(number);

	}

	// 标识选择的Item
	public void setSeclection(int position) {
		clickTemp = position;
	}

	// private void addItem() {
	// for (int i = 0; i < answerBlanks.size(); i++) {
	// }
	// int lastInem = Math.max(nowItem++, 0);
	// int thisItem = Math.min(nowItem, answerBlanks.size() - 1);
	// setFocus(answerBlanks.get(lastInem));
	// setFocus(answerBlanks.get(thisItem));
	// }

	@Override
	public int getCount() {
		return datas == null ? 0 : datas.size();
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		switch (pattern) {
		case BlankData.IN_EXAM:

			break;

		case BlankData.AFTER_STUDENT_EXAM:

			break;
		case BlankData.BEFORE_EXAM:

			break;
		case BlankData.AFTER_REFEREE_EXAM:
			if (convertView == null) {
				convertView = LayoutInflater.from(context).inflate(R.layout.item_answerblank_referee, parent, false);
				viewHolder = new ViewHolderTv(convertView);
				convertView.setTag(viewHolder);
			} else {
				viewHolder = (ViewHolderTv) convertView.getTag();
			}
			viewHolder.getMainView().setText("" + datas.get(position).getRefereeScore());
			// viewHolder.getMainView().setOnClickListener(new OnClickListener()
			// {
			// @Override
			// public void onClick(View v) {
			// // TODO Auto-generated method stub
			// Log.d("----------", "convertView"
			// +viewHolder.getMainView().getBottom() + "convertView" +
			// viewHolder.getMainView().getTop());
			//
			// // Log.d("----------", "dianji" +getR+ "dianji" +
			// viewHolder.getMainView().getTop());
			//
			// // ((RefereeAnswerBlank) viewHolder.getMainView()).dialogShow();
			// ppw = new RefereeJudgmentPopupwindow(context);
			// // ppw.showPopupWindow(viewHolder.getMainView());
			// ppw.showAsDropDown(viewHolder.getMainView());
			// View view = viewHolder.getMainView();
			//
			// }
			// });

			break;
		}

		return convertView;
	}

	/**
	 * 学生作答模式下,GridView进行的操作
	 * 
	 * @param position
	 */
	void studentPattern(final int position) {
		if (position == nowItem || position == nowItem - 1) {
			setFocus((EditText) viewHolder.getMainView(), true);
		} else {
			setFocus((EditText) viewHolder.getMainView(), false);
		}
		viewHolder.getMainView().setOnFocusChangeListener(new OnFocusChangeListener() {

			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				// TODO Auto-generated method stub
				Log.d("bbbbb", "----" + position + "----" + hasFocus);

				if (position == nowItem - 1 && hasFocus) {
					nowSubject = false;

				} else {
					nowSubject = true;
				}
			}
		});

		// if (datas.get(position).getUserAnswer() != null &&
		// !("".equals(datas.get(position).getUserAnswer()))) {
	}

	void setFocus(EditText view, boolean focusable) {
		view.setFocusable(focusable);
		view.setFocusableInTouchMode(focusable);
		view.setEnabled(focusable);
		if (focusable) {
			view.requestFocus();
		}
	}


	/**
	 * 确定dialog弹出坐标
	 * 
	 * @return
	 */
	private int getdialogy(int top, int bottom) {
		// TODO Auto-generated method stub
		if (bottom > 400) {
			return top;
		}
		return bottom;
	}

	private int getdialogx(int left, int rigth) {
		// TODO Auto-generated method stub
		if (rigth > 800) {
			return left;
		}
		return rigth;
	}

	abstract class ViewHolder implements holderView {
		View view;

		public ViewHolder(View view) {
			super();
			this.view = view;
		}

	}


	class ViewHolderTv extends ViewHolder {
		RefereeAnswerBlank answerBlank;

		public ViewHolderTv(View view) {
			super(view);
			answerBlank = (RefereeAnswerBlank) view.findViewById(R.id.cashbox_tv);
		}

		@Override
		public TextView getMainView() {
			// TODO Auto-generated method stub
			return answerBlank;
		}
	}

	interface holderView {
		TextView getMainView();
	}



	public BlankData getData(int position) {
		// TODO Auto-generated method stub

		return datas.get(position);
	}

}
