package ggaworowski.justkotlinapplication.data.model

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class SingleWeatherModel() : RealmObject() {
    @PrimaryKey
    var id: Int? = null
    var coord: Coord? = null
    var weather: RealmList<Weather>? = null
    var base: String? = null
    var main: Main? = null
    var visibility: Int? = null
    var wind: Wind? = null
    var clouds: Clouds? = null
    var dt: Int? = null
    var sys: Sys? = null
    var name: String? = null
    var cod: Int? = 200
    var fromLocation: Boolean = false
}