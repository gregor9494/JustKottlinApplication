package ggaworowski.justkotlinapplication.data.model

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.Ignore
import io.realm.annotations.PrimaryKey


open class WeatherList() : RealmObject() {
    @Ignore
    var cod: Int = 0

    @PrimaryKey
    var id: Int = 0

    var list: RealmList<WeatherModel>? = null
}