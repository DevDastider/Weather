package com.self.practice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ForecastActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forecast)

        getWeather()
    }

    private fun getWeather() {
        val searchTerm= intent.extras?.getString("searchTerm")

        val callback= object : Callback<WeatherResponse>{
            override fun onResponse(call: Call<WeatherResponse>, response: Response<WeatherResponse>) {
                println("Response received")
                //setting the title of the action bar from json response
                title= response.body()?.city?.name + ", " + response.body()?.city?.country

                //extracting the list of WeatherList from response
                val forecasts= response.body()?.list

                //To store data from list, so that we can later add it in our adapter
                val forecastString= mutableListOf<String>()

                if (forecasts!=null){
                    for (forecast in forecasts){
                        val newString= "${forecast.dt_txt}- High: ${forecast.main.temp_max}- Low: ${forecast.main.temp_min}- ${forecast.weather.first().description}"
                        forecastString.add(newString)
                    }
                }

                val listView= findViewById<ListView>(R.id.forecastListView)
                val adapter= ArrayAdapter(this@ForecastActivity, android.R.layout.simple_expandable_list_item_1, forecastString)
                listView.adapter= adapter
            }

            override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
                println("No response")
            }
        }
        //Sending the callback object & the city name
        WeatherService.getForecasts(callback, searchTerm!!)
    }
}