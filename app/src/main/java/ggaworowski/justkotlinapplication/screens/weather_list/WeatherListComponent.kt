package ggaworowski.justkotlinapplication.screens.weather_list

import dagger.Subcomponent
import dagger.android.AndroidInjector

@Subcomponent(modules = arrayOf(WeatherListModule::class))
interface WeatherListComponent : AndroidInjector<WeatherListActivity> {
    @Subcomponent.Builder
    abstract class Builder : AndroidInjector.Builder<WeatherListActivity>()
}
