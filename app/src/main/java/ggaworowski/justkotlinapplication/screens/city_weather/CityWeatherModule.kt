package ggaworowski.justkotlinapplication.screens.city_weather

import dagger.Module
import dagger.Provides
import ggaworowski.justkotlinapplication.data.repository.WeatherRepository

@Module
class CityWeatherModule {

    @Provides
    fun provideCityWeatherView(weatherListActivity: CityWeatherActivity): CityWeatherView {
        return weatherListActivity
    }

    @Provides
    fun provideCityWeatherPresenter(weatherRepository : WeatherRepository): CityWeatherPresenter {
        return CityWeatherPresenter(weatherRepository)
    }

}
