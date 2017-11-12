package ggaworowski.justkotlinapplication.di

import android.app.Activity
import dagger.Binds
import dagger.Module
import dagger.android.ActivityKey
import dagger.android.AndroidInjector
import dagger.multibindings.IntoMap
import ggaworowski.justkotlinapplication.screens.city_weather.CityWeatherActivity
import ggaworowski.justkotlinapplication.screens.city_weather.CityWeatherComponent
import ggaworowski.justkotlinapplication.screens.weather_list.WeatherListActivity
import ggaworowski.justkotlinapplication.screens.weather_list.WeatherListComponent


@Module
abstract class ActivityBuilder {
    @Binds
    @IntoMap
    @ActivityKey(CityWeatherActivity::class)
    abstract fun bindCityWeatherActivity(builder: CityWeatherComponent.Builder): AndroidInjector.Factory<out Activity>

    @Binds
    @IntoMap
    @ActivityKey(WeatherListActivity::class)
    abstract fun bindWeatherListActivity(builder: WeatherListComponent.Builder): AndroidInjector.Factory<out Activity>
}