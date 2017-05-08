package com.eteng.moblieplayer.video;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.eteng.moblieplayer.BaseActivity;
import com.eteng.moblieplayer.R;
import com.eteng.moblieplayer.adapter.VideoAdapter;
import com.eteng.moblieplayer.beans.VideoBenas;
import com.eteng.moblieplayer.utils.StringTimeUtil;

import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;

/**
 * Created by gch on 2016/7/6.
 */
public class VideoActivity extends BaseActivity implements AdapterView.OnItemClickListener {

    private ListView video_listview;

    private TextView noresources_tv;

    private VideoBenas beans;
    private List<VideoBenas> data = new ArrayList<VideoBenas>();
    private VideoAdapter adapter;

    private StringTimeUtil util;

    public Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    if (data != null && data.size() != 0) {
                        noresources_tv.setVisibility(View.GONE);
                        adapter = new VideoAdapter(data, VideoActivity.this, util);
                        video_listview.setAdapter(adapter);
                    } else {
                        noresources_tv.setVisibility(View.VISIBLE);
                    }

                    break;
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        settabtitle("视频列表");
        setrightvisible(View.GONE);
        video_listview = (ListView) findViewById(R.id.video_listviw);
        noresources_tv = (TextView) findViewById(R.id.noresources_tv);
        util = new StringTimeUtil();
        getAllVideo();

        video_listview.setOnItemClickListener(this);
    }

    private void getAllVideo() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                ContentResolver resolver = getContentResolver();
                Uri uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                //查找的条件
                String[] projection = {MediaStore.Video.Media.TITLE,   //标题
                        MediaStore.Video.Media.DESCRIPTION,     //描述
                        MediaStore.Video.Media.SIZE,        //大小
                        MediaStore.Video.Media.DURATION,    //时长
                        MediaStore.Video.Media.DATA};   //绝对路径
                Cursor cursor = resolver.query(uri, projection, null, null, null);
                while (cursor.moveToNext()) {
                    //判断视频大小>3MB的才添加进来。
                    if (cursor.getLong(2) > 3 * 1024 * 1024) {
                        beans = new VideoBenas();
                        beans.setTitle(cursor.getString(0));
                        beans.setDescribe(cursor.getString(1));
                        beans.setSize(cursor.getLong(2));
                        beans.setLongtime(cursor.getString(3));
                        beans.setPath(cursor.getString(4));
                        data.add(beans);
                    }
                }
                handler.sendEmptyMessage(0);
            }
        }).start();


    }

    @Override
    public void leftonclick() {
        finish();
    }

    @Override
    public void rightonclick() {

    }

    //加载子布局内容
    @Override
    public View setcontent() {
        return View.inflate(VideoActivity.this, R.layout.activity_video, null);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        VideoBenas item = data.get(position);
        Intent intent = new Intent(VideoActivity.this, VideoPlayerActivity.class);
        intent.setData(Uri.parse(item.getPath()));
        intent.putExtra("videotitle",item.getTitle());
        startActivity(intent);
    }
}
