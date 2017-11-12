package ggaworowski.justkotlinapplication.screens.city_weather

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.hannesdorfmann.mosby3.mvp.MvpActivity
import dagger.android.AndroidInjection
import es.dmoral.toasty.Toasty

import javax.inject.Inject

import ggaworowski.justkotlinapplication.R
import ggaworowski.justkotlinapplication.data.model.SingleWeatherModel
import ggaworowski.justkotlinapplication.data.model.WeatherList
import ggaworowski.justkotlinapplication.data.model.WeatherModel
import ggaworowski.justkotlinapplication.screens.city_weather.view_model.HourWeatherRow
import kotlinx.android.synthetic.main.weather_city_activity.*
import net.idik.lib.slimadapter.SlimAdapter


class CityWeatherActivity : MvpActivity<CityWeatherView, CityWeatherPresenter>(), CityWeatherView {


    companion object {
        val CITY_ID_KEY = "city_id"
    }

    @Inject
    lateinit var cityweatherPresenter: CityWeatherPresenter

    var slimAdapter: SlimAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        setContentView(R.layout.weather_city_activity)
        super.onCreate(savedInstanceState)
        initAdapter()
    }


    fun initAdapter() {
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        slimAdapter = SlimAdapter.create()
                .register<WeatherModel>(R.layout.simple_hour_weather, HourWeatherRow())
                .attachTo(recyclerView)
    }

    override fun createPresenter(): CityWeatherPresenter {
        val id = intent.extras.getInt(CITY_ID_KEY, 0)
        cityweatherPresenter.setCityId(id)
        return cityweatherPresenter
    }

    override fun showData(data: WeatherList?) {
        data?.list?.let {
            slimAdapter?.updateData(it)
            if (it.size > 0) showContent()
        }
    }

    override fun showError(connection_error: Int) {
        Toasty.error(this, getString(connection_error)).show()
    }

    override fun showLoading() {
        progressBar.visibility = View.VISIBLE
        recyclerView.visibility = View.GONE
    }

    override fun showContent() {
        progressBar.visibility = View.GONE
        recyclerView.visibility = View.VISIBLE
    }
}
