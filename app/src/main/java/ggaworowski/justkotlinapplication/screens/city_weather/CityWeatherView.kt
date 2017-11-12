package ggaworowski.justkotlinapplication.screens.city_weather

import com.hannesdorfmann.mosby3.mvp.MvpView
import ggaworowski.justkotlinapplication.data.model.WeatherList
import io.realm.RealmResults


interface CityWeatherView : MvpView {
    fun showData(it: WeatherList?)
    fun showError(connection_error: Int)
    fun showLoading()
    fun showContent()
}
