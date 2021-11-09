package com.lazuardy.weatherapp.utils

import android.widget.ImageView
import com.lazuardy.weatherapp.R
import java.text.SimpleDateFormat
import java.util.*

class General {
    companion object{

        // Example output: Sunday
        fun getDayNameFromDate(dateFormatInput: String, dateStringInput: String?): String {
            val inputFormat = SimpleDateFormat(dateFormatInput)
            val outputFormat = SimpleDateFormat("EEEE")
            val input: Date = inputFormat.parse(dateStringInput.toString())
            return outputFormat.format(input)
        }

        // Example output: 13 PM
        fun getHourFromDate(dateFormatInput: String, dateStringInput: String?): String {
            val inputFormat = SimpleDateFormat(dateFormatInput)
            val outputFormat = SimpleDateFormat("HH aa")
            val input: Date = inputFormat.parse(dateStringInput.toString())
            return outputFormat.format(input)
        }

        // Example output: 18 June 2021
        fun getFormattedDate(dateFormatInput: String, dateStringInput: String?): String {
            val inputFormat = SimpleDateFormat(dateFormatInput)
            val outputFormat = SimpleDateFormat("d MMMM yyyy")
            val input: Date = inputFormat.parse(dateStringInput.toString())
            return outputFormat.format(input)
        }

        // Example output: 18 June 2021 13:14
        fun getFormattedDateWithTime(dateFormatInput: String, dateStringInput: String?): String {
            val inputFormat = SimpleDateFormat(dateFormatInput)
            val outputFormat = SimpleDateFormat("d MMMM yyyy HH:mm")
            val input: Date = inputFormat.parse(dateStringInput.toString())
            return outputFormat.format(input)
        }

        fun getWeatherImageId(condition: String?): Int{
            when(condition){
                "Sunny" -> return R.drawable.ic_sunny
                "Partly cloudy" -> return R.drawable.ic_partly_cloudy
                "Cloudy" -> return R.drawable.ic_cloudy
                "Overcast" -> return R.drawable.ic_windy
                "Mist", "Fog", "Freezing fog" -> return R.drawable.ic_haze
                "Patchy rain possible", "Patchy light drizzle", "Light drizzle", "Patchy light rain", "Light rain", "Moderate rain at times", "Moderate rain", "Light rain shower", "Patchy light rain with thunder"  -> return R.drawable.ic_rain
                "Patchy snow possible", "Blowing snow", "Blizzard", "Patchy light snow", "Light snow", "Patchy moderate snow", "Moderate snow", "Patchy heavy snow", "Heavy snow", "Ice pellets", "Light snow showers", "Moderate or heavy snow showers", "Light showers of ice pellets", "Moderate or heavy showers of ice pellets", "Patchy light snow with thunder", "Moderate or heavy snow with thunder" -> return R.drawable.ic_snow
                "Patchy sleet possible", "Freezing drizzle", "Heavy freezing drizzle", "Light freezing rain", "Moderate or heavy freezing rain", "Light sleet", "Moderate or heavy sleet", "Light sleet showers", "Moderate or heavy sleet showers" -> return R.drawable.ic_sleet
                "Thundery outbreaks possible" -> return R.drawable.ic_thunderstorm
                "Heavy rain at times", "Heavy rain", "Moderate or heavy rain shower", "Torrential rain shower", "Moderate or heavy rain with thunder" -> return R.drawable.ic_heavy_rain
            }
            return R.drawable.ic_partly_cloudy
        }
    }
}