package ggaworowski.justkotlinapplication.screens.city_weather.view_model

import android.view.View
import android.widget.ImageView
import com.squareup.picasso.Picasso
import ggaworowski.justkotlinapplication.R
import ggaworowski.justkotlinapplication.data.model.SingleWeatherModel
import ggaworowski.justkotlinapplication.data.model.WeatherModel
import ggaworowski.justkotlinapplication.rest.RestClient
import net.idik.lib.slimadapter.SlimInjector
import net.idik.lib.slimadapter.viewinjector.IViewInjector
import java.text.SimpleDateFormat
import java.util.*

class HourWeatherRow() : SlimInjector<WeatherModel> {

    val dateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
    val dayFormat = SimpleDateFormat("EEEE dd.MM", Locale.getDefault())

    override fun onInject(data: WeatherModel?, injector: IViewInjector<out IViewInjector<*>>?) {
        injector?.text(R.id.tvTemperature, data?.main?.temp_max?.toInt().toString() + "°")

        setupDate(data, injector)
        loadImage(injector, data)
    }

    private fun loadImage(injector: IViewInjector<out IViewInjector<*>>?, data: WeatherModel?) {
        injector?.with<ImageView>(R.id.ivWeather, {
            Picasso.with(it.context)
                    .load(RestClient.IMG_PATH + data?.weather?.get(0)?.icon + ".png")
                    .into(it)
        })
    }

    private fun setupDate(actual: WeatherModel?, injector: IViewInjector<out IViewInjector<*>>?) {
        val date = actual?.dt?.toLong()?.times(1000)?.let { Date(it) }
        if (date != null) {
            injector?.text(R.id.tvTime, dateFormat.format(date))
            injector?.text(R.id.tvDay, dayFormat.format(date))
        } else {
            injector?.text(R.id.tvTime, "")
            injector?.text(R.id.tvDay, "")
        }
    }
}