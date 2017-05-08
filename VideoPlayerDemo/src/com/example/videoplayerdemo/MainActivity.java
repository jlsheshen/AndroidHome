package com.example.videoplayerdemo;

import android.app.Activity;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.widget.VideoView;

public class MainActivity extends Activity implements OnPreparedListener {
	private VideoView mVideoView;

	private MediaController mMediaController;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mVideoView = (VideoView) findViewById(R.id.video_view);
		mVideoView.setVideoURI(Uri.parse(""));
		mVideoView.setOnPreparedListener(this);
		mMediaController = new MediaController(this);
		mVideoView.setMediaController(mMediaController);
		mVideoView.setFocusableInTouchMode(false);
		mVideoView.setFocusable(false);
		mVideoView.setEnabled(false);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onPrepared(MediaPlayer mp) {
		// TODO Auto-generated method stub
		mVideoView.start();
	}

}
