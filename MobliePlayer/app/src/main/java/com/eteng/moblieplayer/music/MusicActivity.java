package com.eteng.moblieplayer.music;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.eteng.moblieplayer.BaseActivity;
import com.eteng.moblieplayer.R;
import com.eteng.moblieplayer.adapter.MusicAdapter;
import com.eteng.moblieplayer.adapter.VideoAdapter;
import com.eteng.moblieplayer.beans.MusicBenas;
import com.eteng.moblieplayer.beans.VideoBenas;
import com.eteng.moblieplayer.utils.StringTimeUtil;
import com.eteng.moblieplayer.video.VideoActivity;
import com.eteng.moblieplayer.video.VideoPlayerActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gch on 2016/7/6.
 */
public class MusicActivity extends BaseActivity implements AdapterView.OnItemClickListener {

    private ListView music_listviw;

    private TextView noresources_tv;

    private MusicBenas beans;
    private List<MusicBenas> data = new ArrayList<MusicBenas>();
    private MusicAdapter adapter;

    private StringTimeUtil util;

    public Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    if (data != null && data.size() != 0) {
                        noresources_tv.setVisibility(View.GONE);
                        adapter = new MusicAdapter(data, MusicActivity.this, util);
                        music_listviw.setAdapter(adapter);
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
        settabtitle("音乐列表");
        setrightvisible(View.GONE);
        music_listviw = (ListView) findViewById(R.id.music_listviw);
        noresources_tv = (TextView) findViewById(R.id.noresources_tv);
        util = new StringTimeUtil();
        getAllMusic();

        music_listviw.setOnItemClickListener(this);
    }

    private void getAllMusic() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                ContentResolver resolver = getContentResolver();
                Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                //查找的条件
                String[] projection = {MediaStore.Audio.Media.TITLE,   //标题
                        MediaStore.Audio.Media.SIZE,        //大小
                        MediaStore.Audio.Media.DURATION,    //时长
                        MediaStore.Audio.Media.DATA,        //绝对路径
                        MediaStore.Audio.Media.ARTIST};     //作者

                Cursor cursor = resolver.query(uri, projection, null, null, null);
                while (cursor.moveToNext()) {
                    //判断音乐大小>1MB的才添加进来。
                    if (cursor.getLong(1)> 1 * 1024 * 1024) {
                        beans =new MusicBenas();
                        beans.setTitle(cursor.getString(0));
                        beans.setSize(cursor.getLong(1));
                        beans.setLongtime(cursor.getString(2));
                        beans.setPath(cursor.getString(3));
                        beans.setAuthor(cursor.getString(4));
                        data.add(beans);
                    }
                }
                handler.sendEmptyMessage(0);
                Log.e("TAG---",data.size()+"");
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
        return View.inflate(MusicActivity.this, R.layout.activity_music, null);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        MusicBenas item = data.get(position);
        Intent intent = new Intent(MusicActivity.this, MusicPlayerActivity.class);
        intent.setData(Uri.parse(item.getPath()));
        intent.putExtra("authotitle",item.getTitle());
        startActivity(intent);
    }
}
