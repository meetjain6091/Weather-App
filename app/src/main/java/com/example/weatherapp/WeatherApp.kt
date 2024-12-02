package com.example.weatherapp

//MY MEET JAIN

//        If you need my service or you have any question then you can contact with me.

//        LinkedIn account :  https://www.linkedin.com/in/meet-jain-19b9b0285/


data class WeatherApp(
    val base: String,
    val clouds: Clouds,
    val cod: Int,
    val coord: Coord,
    val dt: Int,
    val id: Int,
    val main: Main,
    val name: String,
    val sys: Sys,
    val timezone: Int,
    val visibility: Int,
    val weather: List<Weather>,
    val wind: Wind
)