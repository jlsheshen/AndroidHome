package com.edu.mediaplayerdemo;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.SeekBar;
import android.widget.VideoView;

public class MainActivity extends AppCompatActivity {
    public VideoView videoView;
    SeekBar seekBar;
    EduMediaController mController;
    private boolean isPlaying;
    // private ClassicCase classicCase;
//    private String url = BASE_URL.BASE_URL + "/interface/filedown/down/";
    //视频播放地址



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        videoView = (VideoView) findViewById(R.id.media_vv);
        mController = new EduMediaController(this);

        // presenter.start();
        // 设置播放视频源的路径
        //videoView.setVideoPath(UriConstant.VIDEO_PATH + "aaa.mp4");
        //videoView.setVideoURI(Uri.parse("http://192.168.1.159/interface/filedown/down/439"));
        videoView.setVideoURI(Uri.parse("http://gslb.miaopai.com/stream/ed5HCfnhovu3tyIQAiv60Q__.mp4"));

        // 为VideoView指定MediaController
        videoView.setMediaController(mController);
        videoView.start();
        // 为MediaController指定控制的VideoView
        mController.setMediaPlayer(videoView);
        // 增加监听上一个和下一个的切换事件，默认这两个按钮是不显示的
        mController.setPrevNextListeners(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

            }
        }, new View.OnClickListener() {

            @Override
            public void onClick(View v) {

            }
        });


    }
}
