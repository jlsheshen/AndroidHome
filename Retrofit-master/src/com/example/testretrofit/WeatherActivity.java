package com.example.testretrofit;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class WeatherActivity extends Activity{
	private EditText edit;
	private TextView text;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.weather);
		edit = (EditText) findViewById(R.id.editCity);
		text = (TextView) findViewById(R.id.tvWea);
	}
	public void query2(View v){
		String city = edit.getText().toString();
		Call<Weather> call = WeatherModel.getModel().getService().getWeatherResult(WeatherModel.API_KEY, city);
		call.enqueue(new Callback<Weather>() {
			
			@Override
			public void onResponse(Response<Weather> res, Retrofit arg1) {
				if(res.isSuccess()){
					Weather result = res.body();
					if(result != null && result.getErrNum() == 0){
						Weather.RetDataBean entity = result.getRetData();
						text.setText("城市:"+entity.getCity()+"/n"
								+"日期:"+entity.getDate()+"/n"
								+"最高温度"+entity.getH_tmp()+"/n"
								+"最低温度"+entity.getL_tmp()
						);
					}
				}
			}
			
			@Override
			public void onFailure(Throwable arg0) {
				// TODO Auto-generated method stub
				
			}
		});
	}

}
