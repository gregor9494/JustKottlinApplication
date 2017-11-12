package ggaworowski.justkotlinapplication.di

import android.app.Application

import com.patloew.rxlocation.RxLocation

import javax.inject.Singleton
import dagger.Module
import dagger.Provides

@Module
class LocationModule {

    @Provides
    @Singleton
    internal fun provideRxLocation(application: Application): RxLocation {
        return RxLocation(application)
    }

}
