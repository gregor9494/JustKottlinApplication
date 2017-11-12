package ggaworowski.justkotlinapplication.di

import dagger.Module
import dagger.Provides
import ggaworowski.justkotlinapplication.data.repository.WeatherRepository
import ggaworowski.justkotlinapplication.rest.RestClient
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Provides
    @Singleton
    fun provideWeather(restClient: RestClient): WeatherRepository {
        return WeatherRepository(restClient)
    }
}
