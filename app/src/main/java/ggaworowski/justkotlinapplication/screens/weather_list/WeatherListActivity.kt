package ggaworowski.justkotlinapplication.screens.weather_list

import android.Manifest
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View

import com.hannesdorfmann.mosby3.mvp.MvpActivity
import com.kcode.permissionslib.main.OnRequestPermissionsCallBack
import com.kcode.permissionslib.main.PermissionCompat
import dagger.android.AndroidInjection
import es.dmoral.toasty.Toasty

import javax.inject.Inject

import ggaworowski.justkotlinapplication.R
import ggaworowski.justkotlinapplication.data.model.SingleWeatherModel
import ggaworowski.justkotlinapplication.screens.city_weather.CityWeatherActivity
import ggaworowski.justkotlinapplication.screens.weather_list.view_model.WeatherCityRow
import kotlinx.android.synthetic.main.weather_list_activity.*
import net.idik.lib.slimadapter.SlimAdapter

import java.util.*


class WeatherListActivity : MvpActivity<WeatherListView, WeatherListPresenter>(), WeatherListView {
    companion object {
        val PERM_CODE = 1
    }


    override fun checkPermision(function: () -> Unit) {

        val builder = PermissionCompat.Builder(this)
        builder.addPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION))
        builder.addRequestPermissionsCallBack(object : OnRequestPermissionsCallBack {
            override fun onDenied(p0: String?) {
            }

            override fun onGrant() {
                function.invoke()
            }
        })
        builder.build().request()
    }

    override fun openCityWeatherList(it: SingleWeatherModel?) {
        val intent = Intent(this, CityWeatherActivity::class.java)
        intent.putExtra(CityWeatherActivity.CITY_ID_KEY, it?.id)
        startActivity(intent)
    }

    @Inject
    lateinit var weatherListPresenter: WeatherListPresenter

    var slimAdapter: SlimAdapter? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        setContentView(R.layout.weather_list_activity)
        super.onCreate(savedInstanceState)
        init()
        initAdapter()
    }

    fun init() {
        ivSearch.setOnClickListener({ presenter?.addCity(etSearch.text.toString()) })
    }

    fun initAdapter() {
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        slimAdapter = SlimAdapter.create()
                .register<SingleWeatherModel>(R.layout.simple_city_weather_row,
                        WeatherCityRow({ presenter.refreshCity(it) }, { presenter.citySelected(it) }))
                .attachTo(recyclerView)
    }


    override fun createPresenter(): WeatherListPresenter {
        return weatherListPresenter
    }

    override fun clearSearch() {
        etSearch.setText("")
        showSearchContent()
    }

    override fun showSearchLoading() {
        pbSearch.visibility = View.VISIBLE
        ivSearch.visibility = View.GONE
        etSearch.isEnabled = false
    }


    override fun setData(data: List<SingleWeatherModel>) {
        slimAdapter?.updateData(data)
    }

    override fun showSearchContent() {
        pbSearch.visibility = View.GONE
        ivSearch.visibility = View.VISIBLE
        etSearch.isEnabled = true
    }

    override fun showError(resID: Int) {
        Toasty.error(this, getString(resID)).show()
    }
}
