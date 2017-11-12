package ggaworowski.justkotlinapplication.data.model

import io.realm.RealmObject

open class Wind() : RealmObject() {
    var speed: Double = 0.0
    var deg: Double = 0.0
}
