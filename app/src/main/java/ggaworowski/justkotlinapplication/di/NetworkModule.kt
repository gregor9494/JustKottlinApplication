package ggaworowski.justkotlinapplication.di

import javax.inject.Singleton

import dagger.Module
import dagger.Provides
import ggaworowski.justkotlinapplication.rest.RestClient

@Module
class NetworkModule {
    @Provides
    @Singleton
    internal fun provideRest(): RestClient {
        return RestClient()
    }
}
