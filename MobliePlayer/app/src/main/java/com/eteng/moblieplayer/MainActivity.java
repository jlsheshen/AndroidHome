package com.eteng.moblieplayer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.eteng.moblieplayer.adapter.GridMainAdapter;
import com.eteng.moblieplayer.music.MusicActivity;
import com.eteng.moblieplayer.video.VideoActivity;

import org.xutils.view.annotation.ViewInject;


public class MainActivity extends BaseActivity implements AdapterView.OnItemClickListener {

    @ViewInject(R.id.main_gridview)
    private GridView ll_gridview;

    private GridMainAdapter adapter;
    private int[] imgs_data = {R.mipmap.main_video, R.mipmap.main_music};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        settabtitle("功能列表");
        setleftvisible(View.GONE);

        ll_gridview = (GridView) findViewById(R.id.main_gridview);
        adapter = new GridMainAdapter(imgs_data, MainActivity.this);
        ll_gridview.setAdapter(adapter);
        ll_gridview.setOnItemClickListener(this);
    }


    @Override
    public void leftonclick() {

    }

    @Override
    public void rightonclick() {
        Toast.makeText(MainActivity.this, "右边", Toast.LENGTH_SHORT).show();
    }

    @Override
    public View setcontent() {
        return View.inflate(this, R.layout.activity_main, null);
    }


    private void jump(Class cls){
        Intent intent=new Intent(MainActivity.this,cls);
        startActivity(intent);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case 0:
                jump(VideoActivity.class);
                break;
            case 1:
                jump(MusicActivity.class);
                break;
        }
    }
}
