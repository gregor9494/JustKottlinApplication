package ggaworowski.justkotlinapplication.screens.weather_list.view_model

import android.util.Log
import android.view.View
import android.widget.ImageView
import com.squareup.picasso.Picasso
import ggaworowski.justkotlinapplication.R
import ggaworowski.justkotlinapplication.data.model.SingleWeatherModel
import ggaworowski.justkotlinapplication.rest.RestClient
import net.idik.lib.slimadapter.SlimInjector
import net.idik.lib.slimadapter.viewinjector.IViewInjector
import java.text.SimpleDateFormat
import java.util.*

const val REFRESH_TIME = 3 * 60 * 60 * 1000

class WeatherCityRow(var refreshListener: (SingleWeatherModel?) -> Unit,
                     var clickListener: (SingleWeatherModel?) -> Unit) : SlimInjector<SingleWeatherModel> {

    val dateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())

    override fun onInject(data: SingleWeatherModel?, injector: IViewInjector<out IViewInjector<*>>?) {
        setupRefresh(data, injector)
        setupData(injector, data)
        setupDate(data, injector)
        injector?.clicked(R.id.cardView, { clickListener.invoke(data) })
        injector?.clicked(R.id.ivRefresh, { refreshListener.invoke(data) })
        setupLocationState(data, injector)
        loadImage(injector, data)
    }

    private fun setupLocationState(data: SingleWeatherModel?, injector: IViewInjector<out IViewInjector<*>>?) {
        if (data?.fromLocation == true) {
            injector?.visibility(R.id.ivLocation, View.VISIBLE)
        } else {
            injector?.visibility(R.id.ivLocation, View.GONE)
        }
    }

    private fun loadImage(injector: IViewInjector<out IViewInjector<*>>?, data: SingleWeatherModel?) {
        injector?.with<ImageView>(R.id.ivWeather, {
            Picasso.with(it.context)
                    .load(RestClient.IMG_PATH + data?.weather?.get(0)?.icon + ".png")
                    .into(it)
        })
    }

    private fun setupRefresh(data: SingleWeatherModel?, injector: IViewInjector<out IViewInjector<*>>?) {
        val now = Date()
        val actual = data
        if (now.time - (actual?.dt?.toLong()?.times(1000) ?: 0) > REFRESH_TIME) {
            injector?.visibility(R.id.ivRefresh, View.VISIBLE)
        } else {
            injector?.visibility(R.id.ivRefresh, View.GONE)
        }
    }

    private fun setupData(injector: IViewInjector<out IViewInjector<*>>?, data: SingleWeatherModel?) {
        injector?.text(R.id.tvCityName, data?.name)
        injector?.text(R.id.tvTemperature, data?.main?.temp_max?.toInt().toString() + "°")
    }

    private fun setupDate(actual: SingleWeatherModel?, injector: IViewInjector<out IViewInjector<*>>?) {
        val date = actual?.dt?.toLong()?.times(1000)?.let { Date(it) }
        if (date != null) {
            injector?.text(R.id.tvTime, dateFormat.format(date))
        } else {
            injector?.text(R.id.tvTime, "")
        }
    }
}