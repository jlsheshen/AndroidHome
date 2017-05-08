package com.eteng.moblieplayer.adapter;

import android.content.Context;
import android.text.format.Formatter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.eteng.moblieplayer.R;
import com.eteng.moblieplayer.beans.VideoBenas;
import com.eteng.moblieplayer.utils.StringTimeUtil;
import java.util.List;

/**
 * Created by gch on 2016/7/6.
 */
public class VideoAdapter extends BaseAdapter {
    private List<VideoBenas> data;
    private LayoutInflater inflater;
    private StringTimeUtil util;
    private Context context;
    public VideoAdapter(List<VideoBenas> data, Context context, StringTimeUtil util) {
        this.data = data;
        inflater = LayoutInflater.from(context);
        this.util = util;
        this.context=context;
    }

    @Override
    public int getCount() {
        return data.size();
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
            convertView = inflater.inflate(R.layout.videolist_item, null);
            holder = new ViewHolder();
            holder.title = (TextView) convertView.findViewById(R.id.video_title);
            holder.describe = (TextView) convertView.findViewById(R.id.video_describe);
            holder.size = (TextView) convertView.findViewById(R.id.video_size);
            holder.longtime = (TextView) convertView.findViewById(R.id.video_longtime);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.title.setText(data.get(position).getTitle());
        holder.describe.setText(data.get(position).getDescribe());
        //Formatter.formatFileSize(context, data.get(position).getSize()//自带视频大小转换工具类
        holder.size.setText(Formatter.formatFileSize(context, data.get(position).getSize()));
        holder.longtime.setText(util.stringfortime(Integer.valueOf(data.get(position).getLongtime())));
        return convertView;
    }

    private class ViewHolder {
        TextView title, describe, size, longtime;
    }
}
