package com.example.testretrofit;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;



public class PhoneModel {
	
	public static final String BASE_URL = "http://apis.baidu.com"; // HOST地址
    public static final String API_KEY = "957eb10d3a3b2544a602b602015fcdc2";//开发者Key
    private PhoneService service;
    public static PhoneModel getModel(){
    	return PhoneHolder.phoneModel;
    }

    
    private static class PhoneHolder{
        private static PhoneModel phoneModel = new PhoneModel();//单例对象实例
    }
    
    private PhoneModel(){
    	Retrofit ret = new Retrofit.Builder()
    	.baseUrl(BASE_URL)
    	.addConverterFactory(GsonConverterFactory.create()).build();
    	service = ret.create(PhoneService.class);
    }
    public PhoneService getService(){
    	return service;
    }


}
