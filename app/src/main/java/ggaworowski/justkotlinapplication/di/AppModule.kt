package ggaworowski.justkotlinapplication.di

import android.app.Application
import android.content.Context
import javax.inject.Singleton
import dagger.Module
import dagger.Provides
import ggaworowski.justkotlinapplication.screens.city_weather.CityWeatherComponent
import ggaworowski.justkotlinapplication.screens.weather_list.WeatherListComponent

@Module(subcomponents = arrayOf(
        WeatherListComponent::class,
        CityWeatherComponent::class
))
class AppModule {

    @Provides
    @Singleton
    fun provideContext(application: Application): Context {
        return application
    }


}
