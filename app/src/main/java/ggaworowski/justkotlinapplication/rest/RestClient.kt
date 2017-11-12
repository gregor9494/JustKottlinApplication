package ggaworowski.justkotlinapplication.rest

import com.google.gson.GsonBuilder
import ggaworowski.justkotlinapplication.BuildConfig
import okhttp3.Interceptor
import java.util.concurrent.TimeUnit
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.HttpUrl


class RestClient {

    val service: RestService

    init {

        val client = OkHttpClient.Builder()

        client.connectTimeout(20, TimeUnit.SECONDS)
        client.readTimeout(20, TimeUnit.SECONDS)
        client.writeTimeout(20, TimeUnit.SECONDS)

        client.addInterceptor({
            val original = it.request()
            val originalHttpUrl = original.url()

            val url = originalHttpUrl.newBuilder()
                    .addQueryParameter("APPID", APIKEY)
                    .addQueryParameter("units", "metric")
                    .build()

            val requestBuilder = original.newBuilder()
                    .url(url)

            val request = requestBuilder.build()
            return@addInterceptor it.proceed(request)
        })

        if (BuildConfig.DEBUG) {
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            client.addInterceptor(loggingInterceptor)
        }

        val gson = GsonBuilder()
                .create()

        val retrofit = Retrofit.Builder()
                .baseUrl(PATH)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client.build())
                .build()

        val sscRestService = retrofit.create<RestService>(RestService::class.java)
        service = sscRestService
    }

    companion object {
        val PATH = "http://api.openweathermap.org/data/2.5/"
        val APIKEY = "79a04a94bfa78d4e0d5071a5b2b3db84"
        val IMG_PATH = "http://openweathermap.org/img/w/"
    }
}
