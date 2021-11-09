package com.lazuardy.weatherapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.lazuardy.weatherapp.databinding.ActivityDetailForecastBinding
import com.lazuardy.weatherapp.datafix.ForecastdayItem
import com.lazuardy.weatherapp.datafix.HourItem
import com.lazuardy.weatherapp.datafix.Location
import com.lazuardy.weatherapp.utils.Constants
import com.lazuardy.weatherapp.utils.General.Companion.getDayNameFromDate
import com.lazuardy.weatherapp.utils.General.Companion.getFormattedDate

class DetailForecastActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailForecastBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailForecastBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val forecast = intent.extras?.get("forecast") as ForecastdayItem
        val location = intent.extras?.get("location") as Location

        binding.detailTitleDate.text = getDayNameFromDate("yyyy-MM-dd", forecast.date) + ", " + getFormattedDate("yyyy-MM-dd", forecast.date)
        binding.detailCity.text = location.name + ", " + location.country
        binding.detailAvgTemp.text = forecast.day?.avgtempC?.toInt().toString() + "\u2103"
        binding.detailMaxTemp.text = forecast.day?.maxtempC?.toInt().toString() + "\u2103"
        binding.detailMinTemp.text = forecast.day?.mintempC?.toInt().toString() + "\u2103"
        binding.detailConditionText.text = forecast.day?.condition?.text
        binding.detailMaxWind.text = forecast.day?.maxwindKph.toString() + " kph"
        binding.detailTotalPrecip.text = forecast.day?.totalprecipMm.toString() + " %"
        binding.listHourlyForecast.adapter = DetailHourlyListViewAdapter(this, forecast.hour as ArrayList<HourItem>)

        val bottom_navigation = findViewById<BottomNavigationView>(R.id.bottom_navigation_detail)
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