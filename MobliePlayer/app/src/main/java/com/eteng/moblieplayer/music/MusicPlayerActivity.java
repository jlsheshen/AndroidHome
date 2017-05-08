package com.eteng.moblieplayer.music;

import android.os.Bundle;
import android.view.View;

import com.eteng.moblieplayer.BaseActivity;
import com.eteng.moblieplayer.R;

/**
 * Created by gch on 2016/7/9.
 *
 */
public class MusicPlayerActivity extends BaseActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setrightvisible(View.GONE);
        settabtitle("音乐播放");
    }

    @Override
    public void leftonclick() {
        finish();
    }

    @Override
    public void rightonclick() {

    }

    @Override
    public View setcontent() {
        return View.inflate(MusicPlayerActivity.this, R.layout.activity_musicplayer,null);
    }
}
