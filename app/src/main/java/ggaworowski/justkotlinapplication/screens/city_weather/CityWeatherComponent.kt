package ggaworowski.justkotlinapplication.screens.city_weather

import dagger.Subcomponent
import dagger.android.AndroidInjector


@Subcomponent(modules = arrayOf(CityWeatherModule::class))
interface CityWeatherComponent : AndroidInjector<CityWeatherActivity> {
    @Subcomponent.Builder
    abstract class Builder : AndroidInjector.Builder<CityWeatherActivity>()
}
