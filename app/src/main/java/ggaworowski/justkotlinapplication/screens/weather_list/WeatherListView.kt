package ggaworowski.justkotlinapplication.screens.weather_list

import com.hannesdorfmann.mosby3.mvp.MvpView
import ggaworowski.justkotlinapplication.data.model.SingleWeatherModel


interface WeatherListView : MvpView {
    fun clearSearch()
    fun showSearchLoading()
    fun showSearchContent()
    fun setData(data: List<SingleWeatherModel>)
    fun showError(resID: Int)
    fun openCityWeatherList(it: SingleWeatherModel?)
    fun checkPermision(function: () -> Unit)
}
