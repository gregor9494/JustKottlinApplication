package ggaworowski.justkotlinapplication.data.model

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class SingleCityModel() : RealmObject() {

    var id: Long = 0
    var name: String = ""
    var watherList: WeatherList? = null
}