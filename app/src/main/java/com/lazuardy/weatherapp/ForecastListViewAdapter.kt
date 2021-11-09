package com.lazuardy.weatherapp

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.lazuardy.weatherapp.datafix.ForecastdayItem
import com.lazuardy.weatherapp.utils.General
import com.lazuardy.weatherapp.utils.General.Companion.getDayNameFromDate
import com.lazuardy.weatherapp.utils.General.Companion.getFormattedDate
import com.lazuardy.weatherapp.utils.General.Companion.getWeatherImageId

class ForecastListViewAdapter (private val context: Activity, private val arrayList: ArrayList<ForecastdayItem>) : ArrayAdapter<ForecastdayItem>(context, R.layout.list_forecast, arrayList){
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater: LayoutInflater = LayoutInflater.from(context)
        val view: View = inflater.inflate(R.layout.list_forecast, null)

        val forecastImage: ImageView = view.findViewById(R.id.forecast_image)
        val forecastDay: TextView = view.findViewById(R.id.forecast_day)
        val forecastConditionText: TextView = view.findViewById(R.id.forecast_condition_text)
        val forecastAvgTempC: TextView = view.findViewById(R.id.forecast_avg_temp_c)

        forecastImage.setImageResource(getWeatherImageId(arrayList[position].day?.condition?.text))
        forecastDay.text = getDayNameFromDate("yyyy-MM-dd", arrayList[position].date) + ", " + getFormattedDate("yyyy-MM-dd", arrayList[position].date)
        forecastAvgTempC.text = arrayList[position].day?.avgtempC?.toInt().toString() + "\u2103"
        forecastConditionText.text = arrayList[position].day?.condition?.text

        return view
    }
}