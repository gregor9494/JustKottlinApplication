package ggaworowski.justkotlinapplication.rest


import ggaworowski.justkotlinapplication.data.model.WeatherList
import ggaworowski.justkotlinapplication.data.model.SingleWeatherModel
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface RestService {

    @GET("forecast")
    fun getForecast(@Query("id") cityID: Int): Single<WeatherList>

    @GET("weather")
    fun getWeather(@Query("q") cityName: String): Single<SingleWeatherModel>

    @GET("weather")
    fun getWeatherByID(@Query("id") cityID: Int): Single<SingleWeatherModel>

    @GET("weather")
    fun getWeatherByCoords(@Query("lat") lat: Double,@Query("lon") lon: Double): Single<SingleWeatherModel>
}
