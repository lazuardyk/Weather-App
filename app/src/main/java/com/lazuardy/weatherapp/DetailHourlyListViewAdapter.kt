package com.lazuardy.weatherapp

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.lazuardy.weatherapp.datafix.ForecastdayItem
import com.lazuardy.weatherapp.datafix.HourItem
import com.lazuardy.weatherapp.utils.General
import com.lazuardy.weatherapp.utils.General.Companion.getHourFromDate
import com.lazuardy.weatherapp.utils.General.Companion.getWeatherImageId

class DetailHourlyListViewAdapter(private val context: Activity, private val arrayList: ArrayList<HourItem>) : ArrayAdapter<HourItem>(context, R.layout.list_hourly_forecast, arrayList) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater: LayoutInflater = LayoutInflater.from(context)
        val view: View = inflater.inflate(R.layout.list_hourly_forecast, null)

        val hourlyImage: ImageView = view.findViewById(R.id.hourly_image)
        val hourlyCondition: TextView = view.findViewById(R.id.hourly_condition)
        val hourlyTempC: TextView = view.findViewById(R.id.hourly_temp_c)
        val hourlyTime: TextView = view.findViewById(R.id.hourly_time)

        hourlyImage.setImageResource(getWeatherImageId(arrayList[position].condition?.text))
        hourlyTime.text = getHourFromDate("yyyy-MM-dd HH:mm", arrayList[position].time)
        hourlyTempC.text = arrayList[position].tempC?.toInt().toString() + "\u2103"
        hourlyCondition.text = arrayList[position].condition?.text

        return view
    }
}