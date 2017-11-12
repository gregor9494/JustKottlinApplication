package ggaworowski.justkotlinapplication.screens.weather_list

import android.annotation.SuppressLint
import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter
import com.patloew.rxlocation.RxLocation
import ggaworowski.justkotlinapplication.R
import ggaworowski.justkotlinapplication.data.model.SingleWeatherModel
import ggaworowski.justkotlinapplication.data.repository.WeatherRepository
import io.reactivex.disposables.Disposable
import com.google.android.gms.location.LocationRequest

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class WeatherListPresenter(var weatherRepository: WeatherRepository, var rxLocation: RxLocation) : MvpBasePresenter<WeatherListView>() {

    var disposable: Disposable? = null
    var locationDis: Disposable? = null

    override fun attachView(view: WeatherListView?) {
        super.attachView(view)
        disposable = weatherRepository.getWeatherList()
                ?.subscribe({ view?.setData(it) }, {})
        getActualLocation()
    }


    @SuppressLint("MissingPermission")
    fun getActualLocation() {
        view.checkPermision({
            val locationRequest = LocationRequest.create()
                    .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                    .setInterval(1000)
                    .setNumUpdates(1)

            locationDis = rxLocation.location()
                    .updates(locationRequest)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        weatherRepository
                                .changeActualCity(it.latitude, it.longitude)
                                ?.subscribe()
                    }, { it.printStackTrace() })
        })
    }

    fun addCity(name: String) {
        view.showSearchLoading()
        weatherRepository.addCity(name)?.subscribe({
            if (isViewAttached && it.cod == 200) {
                view.clearSearch()
            }
        }, {
            if (isViewAttached) {
                view.showSearchContent()
                view.showError(R.string.cannot_add_city)
            }
        })
    }

    fun refreshCity(singleWeatherModel: SingleWeatherModel?) {
        weatherRepository.updateCity(singleWeatherModel?.id ?: -1)
                ?.subscribe({
                }, {
                    if (isViewAttached) {
                        view.showError(R.string.cannot_add_city)
                    }
                })
    }

    override fun detachView(retainInstance: Boolean) {
        disposable?.dispose()
        locationDis?.dispose()
        super.detachView(retainInstance)
    }

    fun citySelected(it: SingleWeatherModel?) {
        view.openCityWeatherList(it)
    }

}
