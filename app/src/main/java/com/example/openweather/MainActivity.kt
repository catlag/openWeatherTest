package com.example.openweather

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.openweather.core.Status
import com.example.openweather.ui.main.MainViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


const val REQUEST_CODE = 100
const val LAST_LAT_LON = "lastLatLon"
const val LAT = "lat"
const val LON = "lon"

class MainActivity : AppCompatActivity() {
    private val viewModel by viewModel<MainViewModel>()

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navHost: NavHostFragment
    private lateinit var navController: NavController
    private lateinit var locationManager: LocationManager
    private  var networkLocation: Location? = null
    private  var gpsLocation: Location? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navHost = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_content_main) as NavHostFragment
        navController = navHost.navController
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        checkPermission()
        observeLocalPreferences()
        viewModel.getLocalCoordinates()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                navController.popBackStack()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun checkPermission(){
        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
         if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
             ActivityCompat.requestPermissions(
                 this,
                 arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION),
                 REQUEST_CODE
             )
             return
         }
        networkLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
        gpsLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
        setFromSavedLocation()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE) {
            if (grantResults.isNotEmpty() && !grantResults.any { it != PackageManager.PERMISSION_GRANTED }) {
                try {
                    gpsLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
                    networkLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
                    setFromSavedLocation()
                } catch (e: SecurityException){

                }
            } else {
                setFromSavedLocation()
            }
            return
        }
    }

    fun setFromSavedLocation(){
        // checking user location &shared preferences for saved lat lon of last search
        var lat = ""
        var lon = ""
//        if (gpsLocation != null){
//            lat = gpsLocation!!.latitude.toString()
//            lon = gpsLocation!!.longitude.toString()
//        }else if (networkLocation != null){
//            lat = networkLocation!!.latitude.toString()
//            lon = networkLocation!!.longitude.toString()
//        } else {
//            val prefs = getSharedPreferences(LAST_LAT_LON, MODE_PRIVATE)
//            lat = prefs.getString(LAT, "").toString()
//            lon = prefs.getString(LON, "").toString()
//        }

        if (gpsLocation != null){
            lat = gpsLocation!!.latitude.toString()
            lon = gpsLocation!!.longitude.toString()
        }
        if (networkLocation != null){
            lat = networkLocation!!.latitude.toString()
            lon = networkLocation!!.longitude.toString()
        }
        if (lat.isNotEmpty() && lon.isNotEmpty()){
            viewModel.getWeatherByCoordinates(lat, lon)
            navController.navigate(R.id.action_mainFragment_to_ResultsFragmet)
        }
    }

    private fun observeLocalPreferences() {
        viewModel.localCoordinates.observe(this) {
            when (it.status) {
                Status.SUCCESS -> {
                    val lat = it.data?.get(LAT)
                    val lon = it.data?.get(LON)
                    if (lat?.isNotEmpty() == true && lon?.isNotEmpty() == true) {
                        viewModel.getWeatherByCoordinates(lat, lon)
                        navController.navigate(R.id.action_mainFragment_to_ResultsFragmet)
                    }
                }
                Status.LOADING -> {
//                    do nothing
                }
                Status.ERROR -> {
//                do nothing
                }
            }
        }
    }
}