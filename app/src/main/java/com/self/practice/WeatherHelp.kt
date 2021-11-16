package com.self.practice

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

const val BASE_URL= "https://api.openweathermap.org/"
const val API_KEY= "YOUR API KEY GOES HERE"

interface WeatherAPI{
    @GET("data/2.5/forecast?appid=$API_KEY&units=metric")
    fun getForecast(@Query("q") q: String): Call<WeatherResponse>
}

data class WeatherResponse(val list: List<WeatherList>, val city: WeatherCity)
data class WeatherCity(val name: String, val country: String)
data class WeatherList(val main: WeatherMain, val weather: List<Weather>, val dt_txt: String)
data class WeatherMain(val temp: Double, val temp_min: Double, val temp_max: Double)
data class Weather(val description: String)

object WeatherService{
    private val service: WeatherAPI

    init {
        val retrofit= Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        service= retrofit.create(WeatherAPI::class.java)
    }

    fun getForecasts(callback: Callback<WeatherResponse>, searchTerm: String) {
        var searchT= searchTerm
        if(searchT=="") {
            searchT = "Kolkata"
        }
        val weather: Call<WeatherResponse> = service.getForecast(searchT)
        weather.enqueue(callback)
    }
}