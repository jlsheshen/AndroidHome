package com.example.testretrofit;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Query;

public interface WeatherService {
	
	@GET("/apistore/weatherservice/cityname")
	Call<Weather> getWeatherResult(
		@Header("apikey") String apikey,
		@Query("cityname") String cityname);
}
