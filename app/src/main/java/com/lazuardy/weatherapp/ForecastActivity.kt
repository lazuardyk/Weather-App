package com.lazuardy.weatherapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.lazuardy.weatherapp.api.RetroInstance
import com.lazuardy.weatherapp.databinding.ActivityForecastBinding
import com.lazuardy.weatherapp.databinding.ActivityMainBinding
import com.lazuardy.weatherapp.datafix.ForecastResponse
import com.lazuardy.weatherapp.datafix.ForecastdayItem
import com.lazuardy.weatherapp.utils.General
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.Serializable

class ForecastActivity : AppCompatActivity() {
    private lateinit var binding: ActivityForecastBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForecastBinding.inflate(layoutInflater)
        setContentView(binding.root)

        RetroInstance.api.getForecast().enqueue(object: Callback<ForecastResponse> {
            override fun onResponse(
                call: Call<ForecastResponse>,
                response: Response<ForecastResponse>
            ) {
                if (response.isSuccessful) {
                    val forecastResponse = response.body()
                    binding.listForecast.adapter = ForecastListViewAdapter(this@ForecastActivity,
                        forecastResponse?.forecast?.forecastday as ArrayList<ForecastdayItem>
                    )
                    binding.listForecast.isClickable = true
                    binding.listForecast.setOnItemClickListener{parent, view, position, id ->
                        val forecast = forecastResponse?.forecast?.forecastday[position]
                        val location = forecastResponse?.location
                        val intent = Intent(this@ForecastActivity, DetailForecastActivity::class.java)
                        intent.putExtra("forecast", forecast as Serializable)
                        intent.putExtra("location", location as Serializable)
                        startActivity(intent)
                    }
                }
            }

            override fun onFailure(call: Call<ForecastResponse>, t: Throwable) {
                println(t.message)
            }
        })

        val bottom_navigation = findViewById<BottomNavigationView>(R.id.bottom_navigation_forecast)
        bottom_navigation.selectedItemId = R.id.action_forecast
        bottom_navigation.setOnItemSelectedListener {
            when(it.itemId){
                R.id.action_home -> launchHomeActivity()
            }
            true
        }
    }

    fun launchHomeActivity(): Boolean {
        val intent: Intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        return true
    }
}