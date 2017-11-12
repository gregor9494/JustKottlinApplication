package ggaworowski.justkotlinapplication.screens.weather_list

import com.patloew.rxlocation.RxLocation
import dagger.Module
import dagger.Provides
import ggaworowski.justkotlinapplication.data.repository.WeatherRepository

@Module
class WeatherListModule {

    @Provides
    fun provideWeatherListView(weatherListActivity: WeatherListActivity): WeatherListView {
        return weatherListActivity
    }

    @Provides
    fun provideWeatherListPresenter(weatherRepository : WeatherRepository,rxLocation: RxLocation): WeatherListPresenter {
        return WeatherListPresenter(weatherRepository,rxLocation)
    }

}
