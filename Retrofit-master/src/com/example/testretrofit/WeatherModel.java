package com.example.testretrofit;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

public class WeatherModel {
	
	public static final String BASE_URL = "http://apis.baidu.com";
	public static final String API_KEY = "957eb10d3a3b2544a602b602015fcdc2";
	private WeatherService service;
	
	public static WeatherModel getModel(){
		return WeatherHolder.model;
	}
	private static class WeatherHolder{
		private static WeatherModel model = new WeatherModel();
	}
	
	private WeatherModel(){
		Retrofit ret = new Retrofit.Builder()
		.baseUrl(BASE_URL)
		.addConverterFactory(GsonConverterFactory.create())
		.build();
		
		service = ret.create(WeatherService.class);
	}
	public WeatherService getService(){
		return service;
	}

}
