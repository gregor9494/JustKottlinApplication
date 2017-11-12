package ggaworowski.justkotlinapplication.data.model

import io.realm.RealmObject

open class Weather() : RealmObject(){
        var id: Int=0
        var main: String = ""
        var description: String =""
        var icon: String = ""
}