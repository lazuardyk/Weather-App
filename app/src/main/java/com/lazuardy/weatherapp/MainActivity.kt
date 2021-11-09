package com.lazuardy.weatherapp

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.lazuardy.weatherapp.api.RetroInstance
import com.lazuardy.weatherapp.databinding.ActivityMainBinding
import com.lazuardy.weatherapp.datafix.ForecastResponse
import com.lazuardy.weatherapp.utils.Constants
import com.lazuardy.weatherapp.utils.General.Companion.getDayNameFromDate
import com.lazuardy.weatherapp.utils.General.Companion.getFormattedDateWithTime
import com.lazuardy.weatherapp.utils.General.Companion.getWeatherImageId
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.Serializable
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.search_menu, menu)

        val search = menu?.findItem(R.id.action_search)
        val searchView = search?.actionView as SearchView
        searchView.queryHint = "London"
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                RetroInstance.api.getForecastByCity(query).enqueue(object: Callback<ForecastResponse> {
                    override fun onResponse(
                        call: Call<ForecastResponse>,
                        response: Response<ForecastResponse>
                    ) {
                        if (response.isSuccessful) {
                            val forecastResponse = response.body()
                            val forecast = forecastResponse?.forecast?.forecastday?.get(0)
                            val location = forecastResponse?.location
                            val intent = Intent(this@MainActivity, DetailForecastActivity::class.java)
                            intent.putExtra("forecast", forecast as Serializable)
                            intent.putExtra("location", location as Serializable)
                            startActivity(intent)
                        } else {
                            Toast.makeText(this@MainActivity, "Not found, please try again", Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: Call<ForecastResponse>, t: Throwable) {
                        println(t.message)
                    }
                })
                return false
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })

        return true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.city.text = Constants.DEFAULT_CITY

        RetroInstance.api.getForecast().enqueue(object: Callback<ForecastResponse> {
            override fun onResponse(
                call: Call<ForecastResponse>,
                response: Response<ForecastResponse>
            ) {
                if (response.isSuccessful) {
                    val forecastResponse = response.body()
                    val current = forecastResponse?.current
                    binding.currentConditionImage.setImageResource(getWeatherImageId(current?.condition?.text))
                    binding.currentLastUpdated.text = getFormattedDateWithTime("yyyy-MM-dd HH:mm", current?.lastUpdated)
                    binding.currentConditionText.text = current?.condition?.text
                    binding.currentWindKph.text = current?.windKph.toString() + " kph"
                    binding.currentCloud.text = current?.cloud.toString() + " %"
                    binding.currentGustKph.text = current?.gustKph.toString() + " kph"
                    binding.currentHumidity.text = current?.humidity.toString() + " %"
                    binding.currentPrecipMm.text = current?.precipMm.toString() + " %"
                    binding.currentPressureMb.text = current?.pressureMb.toString() + " mb"
                    binding.currentTempC.text = current?.tempC?.toInt().toString() + "\u2103"
                    binding.currentDayName.text = getDayNameFromDate("yyyy-MM-dd HH:mm", current?.lastUpdated)
                    binding.currentAirQualityUsEpaIndex.text = getStringUsEpaIndex(current?.airQuality?.usEpaIndex)
                    binding.currentAirQualityCo.text = String.format("%.3f", current?.airQuality?.co) + " μg/m3 Carbon Monoxide"
                    binding.currentAirQualityO3.text = String.format("%.3f", current?.airQuality?.o3) + " μg/m3 Ozone"
                    binding.currentAirQualityNo2.text = String.format("%.3f", current?.airQuality?.no2) + " μg/m3 Nitrogen dioxide"
                }
            }

            override fun onFailure(call: Call<ForecastResponse>, t: Throwable) {
                println(t.message)
            }
        })

        val bottom_navigation = findViewById<BottomNavigationView>(R.id.bottom_navigation_main)
        bottom_navigation.selectedItemId = R.id.action_home
        bottom_navigation.setOnItemSelectedListener {
            when(it.itemId){
                R.id.action_forecast -> launchForecastActivity()
            }
            true
        }
    }

    fun getStringUsEpaIndex(index: Int?): String {
        when(index){
            1 -> return "Good"
            2 -> return "Moderate"
            3 -> return "Unhealthy for Sensitive Group"
            4 -> return "Unhealthy"
            5 -> return "Very Unhealthy"
            6 -> return "Hazardous"
        }
        return ""
    }

    fun launchForecastActivity(): Boolean {
        val intent: Intent = Intent(this, ForecastActivity::class.java)
        startActivity(intent)
        return true
    }

    private fun getYesterdayDate(day: Int): Date? {
        val cal = Calendar.getInstance()
        cal.add(Calendar.DATE, day)
        return cal.time
    }

    fun getYesterdayDetail(view: View) {
        val sdf = SimpleDateFormat("yyyy-MM-dd")
        val dateString = sdf.format(getYesterdayDate(-1))

        RetroInstance.api.getHistoryForecast(dateString).enqueue(object: Callback<ForecastResponse> {
            override fun onResponse(
                call: Call<ForecastResponse>,
                response: Response<ForecastResponse>
            ) {
                if (response.isSuccessful) {
                    val forecastResponse = response.body()
                    val forecast = forecastResponse?.forecast?.forecastday?.get(0)
                    val location = forecastResponse?.location
                    val intent = Intent(this@MainActivity, DetailForecastActivity::class.java)
                    intent.putExtra("forecast", forecast as Serializable)
                    intent.putExtra("location", location as Serializable)
                    startActivity(intent)
                }
            }

            override fun onFailure(call: Call<ForecastResponse>, t: Throwable) {
                println(t.message)
            }
        })
    }

    fun get2DaysAgoDetail(view: View){
        val sdf = SimpleDateFormat("yyyy-MM-dd")
        val dateString = sdf.format(getYesterdayDate(-2))

        RetroInstance.api.getHistoryForecast(dateString).enqueue(object: Callback<ForecastResponse> {
            override fun onResponse(
                call: Call<ForecastResponse>,
                response: Response<ForecastResponse>
            ) {
                if (response.isSuccessful) {
                    val forecastResponse = response.body()
                    val forecast = forecastResponse?.forecast?.forecastday?.get(0)
                    val location = forecastResponse?.location
                    val intent = Intent(this@MainActivity, DetailForecastActivity::class.java)
                    intent.putExtra("forecast", forecast as Serializable)
                    intent.putExtra("location", location as Serializable)
                    startActivity(intent)
                }
            }

            override fun onFailure(call: Call<ForecastResponse>, t: Throwable) {
                println(t.message)
            }
        })
    }
}