package ggaworowski.justkotlinapplication.data.model

import io.realm.RealmObject


open class Coord() : RealmObject() {
    var lon: Double = 0.0
    var lat: Double = 0.0
}
