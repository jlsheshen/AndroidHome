package com.eteng.moblieplayer.video;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.eteng.moblieplayer.BaseActivity;
import com.eteng.moblieplayer.R;
import com.eteng.moblieplayer.utils.StringTimeUtil;
import com.eteng.moblieplayer.view.VideoView;

/**
 * Created by gch on 2016/7/6.
 * 播放页面
 */
public class VideoPlayerActivity extends BaseActivity implements MediaPlayer.OnPreparedListener, MediaPlayer.OnInfoListener {
    private VideoView videoView;
    //--播放的地址uri
    private Uri uri;
    private ImageView play_imag, fullview_imag, back_imag, battery_imag;
    private SeekBar mseekBar;
    private TextView videoTitle, SystemTime;
    private TextView videocurrent_time, videototal_time;
    private StringTimeUtil utils;
    private MyBrodCastReceiver receiver;
    //设置电量
    private int leave;
    //手势识别器
    private GestureDetector detector;
    //顶部布局
    private RelativeLayout top_layout;
    //底部布局
    private LinearLayout bottom_layout;
    /**
     * 判断是否播放
     */
    private boolean isPlayer = false;
    /***
     * 判断Activity是否被销毁
     * --true-->销毁
     * --false -->没有销毁
     */
    private boolean isDestory = false;
    //
    private static final int PROGRESS = 1;
    //隐藏顶部和底部的变量
    private static final int HIDE_TOPBOTTOMLAYOUT = 2;

    /**
     * 网络加载等待布局
     */
    private LinearLayout ll_loading;

    /***
     * 出现卡的缓冲加载
     */
    private ProgressBar pg_buff;

    private Handler mhandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case PROGRESS:
                    //设置进度
                    int currenttime = videoView.getCurrentPosition();
                    videocurrent_time.setText(utils.stringfortime(currenttime));
                    //更新seekbar
                    mseekBar.setProgress(currenttime);
                    //更新电量          //电量显示和系统时间可自行设置多久时间更新一次
                    setBattery();
                    //设置系统时间
                    setsystemTime();

                    /***
                     * 得到视频缓存度
                     * --缓存比例值-0~100
                     */
                    int Percentage = videoView.getBufferPercentage();
                    int total = Percentage * mseekBar.getMax();
                    int secondaryprogress = total / 100;
                    mseekBar.setSecondaryProgress(secondaryprogress);

                    if (!isDestory) {
                        mhandler.removeMessages(PROGRESS);
                        //消息的死循环--用于更新播放进度
                        mhandler.sendEmptyMessageDelayed(PROGRESS, 1000);
                    }
                    break;
                case HIDE_TOPBOTTOMLAYOUT:
                    hidelayout();
                    break;
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        settabvisible(View.GONE);
        initView();
        initData();
        setonclick();
        //得到播放地址
        uri = getIntent().getData();
        videoView.setVideoURI(uri); //设置路径
        videoView.setOnPreparedListener(this);  //准备好的监听事件
    }

    private void setsystemTime() {
        SystemTime.setText(utils.getSystemTime());
    }


    private void setBattery() {
        if (leave <= 5) {
            battery_imag.setImageResource(R.mipmap.icv_power_00);
        } else if (leave <= 20) {
            battery_imag.setImageResource(R.mipmap.icv_power_01);
        } else if (leave <= 40) {
            battery_imag.setImageResource(R.mipmap.icv_power_02);
        } else if (leave <= 60) {
            battery_imag.setImageResource(R.mipmap.icv_power_03);
        } else if (leave <= 80) {
            battery_imag.setImageResource(R.mipmap.icv_power_04);
        } else if (leave <= 100) {
            battery_imag.setImageResource(R.mipmap.icv_power_05);
        }
    }

    /***
     * true--显示--false--隐藏
     */
    private boolean isShowLayout = false;


    private void initData() {
        videoTitle.setText(getIntent().getStringExtra("videotitle"));
        isDestory = false;
        utils = new StringTimeUtil();
        //注册电量监听的广播
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_BATTERY_CHANGED);
        receiver = new MyBrodCastReceiver();
        registerReceiver(receiver, intentFilter);
        //实例化
        detector = new GestureDetector(VideoPlayerActivity.this, new GestureDetector.OnGestureListener() {
            //单击
            @Override
            public boolean onDown(MotionEvent e) {
                Log.e("TAG", "onDown");
                if (isShowLayout) {
                    removeHideTopBottomLayoutMessage();
                    hidelayout();
                } else {
                    sendDelayedHideTopBottomLayout();
                    showLayout();
                }
                return true;
            }

            //长按执行的顺序---1.onDown-->2.onShowPress-->onLongPress
            @Override
            public void onShowPress(MotionEvent e) {
                Log.e("TAG", "onShowPress");
            }

            //单击后执行onDown接着执行的方法
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                Log.e("TAG", "onSingleTapUp");
                return false;
            }

            //滑动
            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                Log.e("TAG", "onScroll");
                return false;
            }

            //长按
            @Override
            public void onLongPress(MotionEvent e) {
                Log.e("TAG", "onLongPress");
            }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                return false;
            }
        });
    }


    /***
     * 发送延迟隐藏
     */
    private void sendDelayedHideTopBottomLayout() {
        mhandler.sendEmptyMessageDelayed(HIDE_TOPBOTTOMLAYOUT, 5000);
    }

    /***
     * 移除延迟隐藏的Handler消息
     */
    private void removeHideTopBottomLayoutMessage() {
        mhandler.removeMessages(HIDE_TOPBOTTOMLAYOUT);
    }

    /***
     * 显示的方法
     */
    private void showLayout() {
        isShowLayout = true;
        top_layout.setVisibility(View.VISIBLE);
        bottom_layout.setVisibility(View.VISIBLE);

    }

    /***
     * 隐藏的方法
     */
    private void hidelayout() {
        isShowLayout = false;
        top_layout.setVisibility(View.GONE);
        bottom_layout.setVisibility(View.GONE);
    }

    /***
     * 使用手势适配器
     *
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        detector.onTouchEvent(event);
        return true;    //返回true视交由人为处理
    }

    /***
     * 卡或者拖动卡时候出现的回调
     *
     * @param mp
     * @param what
     * @param extra
     * @return
     */
    @Override
    public boolean onInfo(MediaPlayer mp, int what, int extra) {
        switch (what) {
            case MediaPlayer.MEDIA_INFO_BUFFERING_START:    //开始出现卡或者拖动卡
                pg_buff.setVisibility(View.VISIBLE);
                break;
            case MediaPlayer.MEDIA_INFO_BUFFERING_END:      //结束卡或者拖动卡情况
                pg_buff.setVisibility(View.GONE);
                break;
        }
        return true;
    }

    private class MyBrodCastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            //返回一个电量的范围值
            leave = intent.getIntExtra("level", 0);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        isDestory = true;
        unregisterReceiver(receiver);
        receiver = null;
        videoView.stopPlayback();
    }

    public void initView() {
        pg_buff = (ProgressBar) findViewById(R.id.pg_buff);
        videoView = (VideoView) findViewById(R.id.videoview);
        ll_loading = (LinearLayout) findViewById(R.id.ll_loading);
        top_layout = (RelativeLayout) findViewById(R.id.topLayout);
        bottom_layout = (LinearLayout) findViewById(R.id.bottomLayout);
        SystemTime = (TextView) findViewById(R.id.videosystemTime);
        battery_imag = (ImageView) findViewById(R.id.videoPower);
        back_imag = (ImageView) findViewById(R.id.videoBack);
        play_imag = (ImageView) findViewById(R.id.videoPlay);
        fullview_imag = (ImageView) findViewById(R.id.videoFullScreen);
        mseekBar = (SeekBar) findViewById(R.id.videoPlayProgress);
        videoTitle = (TextView) findViewById(R.id.videoTitle);
        videocurrent_time = (TextView) findViewById(R.id.videoCurrentProgress);
        videototal_time = (TextView) findViewById(R.id.videototalTime);

    }

    public void setonclick() {
        back_imag.setOnClickListener(onclicklistener);
        play_imag.setOnClickListener(onclicklistener);
        fullview_imag.setOnClickListener(onclicklistener);
        videoView.setOnInfoListener(this);
        mseekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            /***
             *
             * @param seekBar-->自身的seekba
             * @param progress-->seekbar的位置，视频的长度和seekbar的位置一一对应
             * @param fromUser-->如果是人为的拖动值为--true，如seekbar自身在改变的值--false
             */
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    videoView.seekTo(progress);
                }
            }

            /**
             * 手指开始拖动时进行回调
             * @param seekBar
             */
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                removeHideTopBottomLayoutMessage();
            }

            /**
             * 手指离开回调
             * @param seekBar
             */
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                sendDelayedHideTopBottomLayout();
            }
        });


        /***
         * 视频加载出错时进行回掉
         * --true-->人为处理出错的结果
         */
        videoView.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                Toast.makeText(VideoPlayerActivity.this, "视频出错了", Toast.LENGTH_LONG).show();
                finish();
                return true;
            }
        });
    }


    View.OnClickListener onclicklistener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.videoPlay:
                    if (isPlayer) {
                        videoView.pause();
                        play_imag.setImageResource(R.mipmap.icv_play);
                    } else {
                        videoView.start();
                        play_imag.setImageResource(R.mipmap.icv_pause);
                    }
                    isPlayer = !isPlayer;
                    break;
                case R.id.videoBack:
                    finish();
                    videoView.stopPlayback();
                    break;
            }
        }
    };


    @Override
    public void leftonclick() {

    }

    @Override
    public void rightonclick() {

    }

    //设置子布局
    @Override
    public View setcontent() {
        return View.inflate(VideoPlayerActivity.this, R.layout.activity_videoplayer, null);
    }

    /***
     * 监听是否准备好了
     *
     * @param mp
     */
    @Override
    public void onPrepared(MediaPlayer mp) {
        videoView.start();
        play_imag.setImageResource(R.mipmap.icv_pause);
        isPlayer = true;
        //获取总时长
        int duration = videoView.getDuration();
        videototal_time.setText("/" + utils.stringfortime(duration));
        //设置seekbar的最大值
        mseekBar.setMax(duration);
        setsystemTime();
        mhandler.sendEmptyMessageDelayed(PROGRESS, 1000);
        sendDelayedHideTopBottomLayout();
        ll_loading.setVisibility(View.GONE);
    }
}
