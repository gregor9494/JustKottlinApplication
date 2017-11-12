package ggaworowski.justkotlinapplication

import com.patloew.rxlocation.RxLocation
import ggaworowski.justkotlinapplication.data.model.SingleWeatherModel
import ggaworowski.justkotlinapplication.data.repository.WeatherRepository
import ggaworowski.justkotlinapplication.screens.weather_list.WeatherListActivity
import ggaworowski.justkotlinapplication.screens.weather_list.WeatherListPresenter
import ggaworowski.justkotlinapplication.screens.weather_list.WeatherListView
import io.reactivex.Flowable
import io.reactivex.Single
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import java.util.*


/**
 * Created by GG on 28.10.2017.
 */

class WeatherListTest() {

    var weatherListView: WeatherListView = Mockito.mock(WeatherListView::class.java)
    var rxLocation: RxLocation = Mockito.mock(RxLocation::class.java)
    var weatherRepository: WeatherRepository = Mockito.mock(WeatherRepository::class.java)

    lateinit var weatherListPresenter: WeatherListPresenter


    @Before
    fun setUp() {
        weatherListPresenter = WeatherListPresenter(weatherRepository, rxLocation)

        Mockito.`when`(weatherRepository.getWeatherList()).thenReturn(Flowable.just(Arrays.asList()))
        weatherListPresenter.attachView(weatherListView)
        Mockito.verify(weatherListView, Mockito.times(1)).setData(Mockito.anyList())
        Assert.assertTrue(weatherListPresenter.isViewAttached)
    }

//    @Test
//    fun testAttachView() {
//        Mockito.`when`(weatherRepository.getWeatherList()).thenReturn(Flowable.just(Arrays.asList()))
//        weatherListPresenter.attachView(weatherListView)
//        Mockito.verify(weatherListView, Mockito.times(1)).setData(Mockito.anyList())
//        Assert.assertTrue(weatherListPresenter.isViewAttached)
//    }

    @Test
    fun testAddCity() {
        val weatherModel = SingleWeatherModel()
        weatherModel.cod = 200
        Mockito.`when`(weatherRepository.addCity("Rzeszów")).thenReturn(Single.just(weatherModel))
        weatherListPresenter.addCity("Rzeszów")
        Mockito.verify(weatherListView, Mockito.times(1)).showSearchLoading()
        Mockito.verify(weatherListView, Mockito.times(1)).clearSearch()
    }


    @Test
    fun testErrorAddCity() {
        val weatherModel = SingleWeatherModel()
        weatherModel.cod = 200
        Mockito.`when`(weatherRepository.addCity("Rzeszów")).thenReturn(Single.error(Exception()))
        weatherListPresenter.addCity("Rzeszów")
        Mockito.verify(weatherListView, Mockito.times(1)).showSearchContent()
        Mockito.verify(weatherListView, Mockito.times(1)).showError(Mockito.anyInt())
    }

}