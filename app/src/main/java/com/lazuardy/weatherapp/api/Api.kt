package com.lazuardy.weatherapp.api

import com.lazuardy.weatherapp.datafix.ForecastResponse
import com.lazuardy.weatherapp.utils.Constants
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Api {
    @GET("forecast.json?key="+Constants.API_KEY+"&q="+Constants.DEFAULT_CITY+"&days=5&aqi=yes&alerts=no")
    fun getForecast(): Call<ForecastResponse>

    @GET("history.json?key="+Constants.API_KEY+"&q="+Constants.DEFAULT_CITY)
    fun getHistoryForecast(@Query("date") date: String): Call<ForecastResponse>

    @GET("forecast.json?key="+Constants.API_KEY+"&days=5&aqi=yes&alerts=no")
    fun getForecastByCity(@Query("q") city: String?): Call<ForecastResponse>
}