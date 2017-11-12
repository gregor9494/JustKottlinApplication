package ggaworowski.justkotlinapplication.data.model

import io.realm.RealmObject

open class Main() : RealmObject() {
    var temp: Double = 0.0
    var pressure: Double = 0.0
    var humidity: Double = 0.0
    var temp_min: Double = 0.0
    var temp_max: Double = 0.0
}