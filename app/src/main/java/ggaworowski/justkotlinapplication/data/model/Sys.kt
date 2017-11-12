package ggaworowski.justkotlinapplication.data.model

import io.realm.RealmObject

open class Sys() : RealmObject() {
    var type: Int = 0
    var id: Int = 0
    var message: Double = 0.0
    var country: String = ""
    var sunrise: Int = 0
    var sunset: Int = 0
}