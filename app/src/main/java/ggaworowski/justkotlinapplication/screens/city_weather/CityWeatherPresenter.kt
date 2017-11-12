package ggaworowski.justkotlinapplication.screens.city_weather

import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter
import ggaworowski.justkotlinapplication.R
import ggaworowski.justkotlinapplication.data.repository.WeatherRepository


class CityWeatherPresenter(var weatherRepository: WeatherRepository) : MvpBasePresenter<CityWeatherView>() {

    var selectedCityId = 0

    override fun attachView(view: CityWeatherView?) {
        super.attachView(view)
        getCacheData()
        downloadData()
    }

    fun getCacheData() {
        weatherRepository
                .getForecastCache(selectedCityId)
                ?.subscribe({
                    if (isViewAttached) {
                        if(it.list?.size ?: 0 > 0) {
                            view.showData(it)
                        }else{
                            view.showLoading()
                        }
                    }
                }, {})
    }


    fun downloadData() {
        weatherRepository
                .getForecast(selectedCityId)
                ?.subscribe({
                    if (isViewAttached) {
                        view.showData(it)
                    }
                }, {
                    if (isViewAttached) {
                        it.printStackTrace()
                        view.showError(R.string.connection_error)
                    }
                })
    }

    fun setCityId(id: Int) {
        selectedCityId = id
    }


}
