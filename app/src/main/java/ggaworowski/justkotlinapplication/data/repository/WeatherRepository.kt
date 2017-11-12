package ggaworowski.justkotlinapplication.data.repository

import ggaworowski.justkotlinapplication.data.model.WeatherList
import ggaworowski.justkotlinapplication.data.model.SingleWeatherModel
import ggaworowski.justkotlinapplication.rest.RestClient
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.realm.Realm

class WeatherRepository(var restClient: RestClient) {

    fun updateCity(cityId: Int): Single<SingleWeatherModel>? {
        return restClient.service.getWeatherByID(cityId)
                .subscribeOn(Schedulers.io())
                .map {
                    saveCityWeather(it)
                    return@map it
                }
                .observeOn(AndroidSchedulers.mainThread())

    }

    fun changeActualCity(lat: Double, lon: Double): Single<SingleWeatherModel>? {
        return restClient.service.getWeatherByCoords(lat, lon)
                .subscribeOn(Schedulers.io())
                .map {
                    if (it.cod == 200) {
                        saveActualCity(it)
                    }
                    return@map it
                }
                .observeOn(AndroidSchedulers.mainThread())

    }

    fun addCity(name: String): Single<SingleWeatherModel>? {
        return restClient.service.getWeather(name)
                .subscribeOn(Schedulers.io())
                .map {
                    if (it.cod == 200) {
                        saveCityWeather(it)
                    }
                    return@map it
                }
                .observeOn(AndroidSchedulers.mainThread())

    }

    fun getForecast(id: Int): Single<WeatherList>? {
        return restClient.service.getForecast(id)
                .subscribeOn(Schedulers.io())
                .map {
                    if (it.cod == 200) {
                        saveCityForecast(id, it)
                    }
                    return@map it
                }
                .observeOn(AndroidSchedulers.mainThread())

    }

    fun getForecastCache(id: Int): Flowable<WeatherList>? {
        val realm: Realm = Realm.getDefaultInstance()
        return realm.where(WeatherList::class.java)
                .equalTo("id", id)
                .findFirstAsync()
                .asFlowable<WeatherList>()
                .filter { it.isLoaded }
                .map { t -> realm.copyFromRealm(t) }
    }

    fun getWeatherList(): Flowable<List<SingleWeatherModel>>? {
        val realm: Realm = Realm.getDefaultInstance()
        return realm.where(SingleWeatherModel::class.java)
                .findAllAsync()
                .asFlowable()
                .filter { it.isLoaded }
                .map { t -> realm.copyFromRealm(t) }

    }

    fun saveActualCity(singleWeatherModel: SingleWeatherModel) {
        val realm: Realm = Realm.getDefaultInstance()
        try {
            realm.executeTransaction {
                realm.where(SingleWeatherModel::class.java)
                        .equalTo("fromLocation", true)
                        .findAll().deleteAllFromRealm()

                singleWeatherModel.fromLocation = true
                realm.copyToRealmOrUpdate(singleWeatherModel)
            }
        } finally {
            realm.close()
        }
    }

    fun saveCityWeather(singleWeatherModel: SingleWeatherModel) {
        val realm: Realm = Realm.getDefaultInstance()
        try {
            realm.executeTransaction {
                realm.copyToRealmOrUpdate(singleWeatherModel)
            }
        } finally {
            realm.close()
        }
    }

    fun saveCityForecast(id: Int, watherList: WeatherList) {
        val realm: Realm = Realm.getDefaultInstance()
        try {
            realm.executeTransaction {
                realm.where(WeatherList::class.java)
                        .equalTo("id", id)
                        .findAll().deleteAllFromRealm()
                watherList.id = id
                realm.copyToRealmOrUpdate(watherList)
            }
        } finally {
            realm.close()
        }
    }

}