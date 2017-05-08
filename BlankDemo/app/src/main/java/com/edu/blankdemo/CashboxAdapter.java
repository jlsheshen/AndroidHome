package com.edu.blankdemo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by Administrator on 2017/2/9.
 */

public class  CashboxAdapter extends BaseAdapter {
    /**
     * 数据集合
     */
    private List<BlankData> datas;

    /**
     * 当前模式
     */
    private int pattern;
    private Context context;

    public CashboxAdapter(Context context) {
        this.context = context;
    }

    public CashboxAdapter setDatas(List<BlankData> datas) {
        this.datas = datas;
        return this;
    }

    public CashboxAdapter setPattern(int pattern) {
        this.pattern = pattern;
        return this;
    }

    @Override
    public int getCount() {
        return datas==null?0:datas.size();
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
        ViewHolder viewHolder = null;
        if (convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.item_answerblank,parent,false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.answerBlank.setBlankData(datas.get(position));

        return convertView;
    }
    class ViewHolder{
        AnswerBlank answerBlank;
        public ViewHolder(View view) {
            answerBlank = (AnswerBlank) view.findViewById(R.id.cashbox_ab);

        }
    }

}
