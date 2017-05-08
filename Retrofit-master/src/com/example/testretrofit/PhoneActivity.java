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

public class PhoneActivity extends Activity {
	
	private TextView tv;
	private EditText edit;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		tv = (TextView) findViewById(R.id.tv);
		edit = (EditText) findViewById(R.id.edit);
	}
	public void query(View v){
		String phone = edit.getText().toString();
		Call<PhoneResult> call = PhoneModel.getModel().getService()
				.getResult(PhoneModel.API_KEY, phone);
		call.enqueue(new Callback<PhoneResult>() {
			
			@Override
			public void onResponse(Response<PhoneResult> res, Retrofit arg1) {
				if(res.isSuccess()){
					PhoneResult result = res.body();
					if(result != null && result.getErrNum() == 0){
						PhoneResult.RetDataEntity entity = result.getRetData();
						tv.append("手机归属地为："+entity.getCity());
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
