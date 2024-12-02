package com.example.weatherapp


import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


//MY MEET JAIN

//        If you need my service or you have any question then you can contact with me.

//        LinkedIn account :  https://www.linkedin.com/in/meet-jain-19b9b0285/


interface ApiInterface {
    @GET("weather")
    fun getWeatherData(
        @Query("q") city: String,
        @Query("appid") appid: String,
        @Query("units") units: String
    ):Call<WeatherApp>
}

//MY MEET JAIN

//        If you need my service or you have any question then you can contact with me.

//        LinkedIn account :  https://www.linkedin.com/in/meet-jain-19b9b0285/