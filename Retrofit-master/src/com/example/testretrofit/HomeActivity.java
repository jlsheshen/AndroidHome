package com.example.testretrofit;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class HomeActivity extends Activity{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home);
	}
	public void queryPhone(View v){
		Intent intent  = new Intent(HomeActivity.this,PhoneActivity.class);
		startActivity(intent);
	}
	public void queryWeather(View v){
		Intent intent  = new Intent(HomeActivity.this,WeatherActivity.class);
		startActivity(intent);
	}
}
