package com.eteng.moblieplayer.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.eteng.moblieplayer.R;

/**
 * Created by gch on 2016/7/6.
 */
public class GridMainAdapter extends BaseAdapter {
    private int[] imags_data;
    private LayoutInflater inflater;

    public GridMainAdapter(int[] imags_data, Context context) {
        this.imags_data = imags_data;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return imags_data.length;
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
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.grid_main_item, null);
            holder = new ViewHolder();
            holder.gridmain_iv = (ImageView) convertView.findViewById(R.id.griditem_iv);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }


        holder.gridmain_iv.setImageResource(imags_data[position]);


        return convertView;
    }

    private class ViewHolder {
        ImageView gridmain_iv;

    }
}


