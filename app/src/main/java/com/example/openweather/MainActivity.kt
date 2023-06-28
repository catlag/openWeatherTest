package com.example.openweather

import android.content.pm.PackageManager
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navHost = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_content_main) as NavHostFragment
        navController = navHost.navController
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        checkPermission()
        checkLastLatLon()
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
        if ( ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {

            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    android.Manifest.permission.ACCESS_COARSE_LOCATION,
                ),
                REQUEST_CODE
            );
        }

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
//            if (ActivityCompat.checkSelfPermission(
//                    this,
//                    android.Manifest.permission.ACCESS_COARSE_LOCATION
//                ) == PackageManager.PERMISSION_GRANTED
//            ) {
//                if (ActivityCompat.checkSelfPermission(
//                        this,
//                        android.Manifest.permission.ACCESS_COARSE_LOCATION
//                    ) != PackageManager.PERMISSION_GRANTED
//                ) {
//                    ActivityCompat.requestPermissions(
//                        this,
//                        arrayOf(android.Manifest.permission.ACCESS_BACKGROUND_LOCATION),
//                        REQUEST_CODE
//                    );
//                }
//            }
//        }
    }

    private fun checkLastLatLon(){
//        checking shared preferences for saved lat lon of last search
        val prefs = getSharedPreferences(LAST_LAT_LON, MODE_PRIVATE)
        val lat = prefs.getString(LAT, null)
        val lon = prefs.getString(LON, null)
        if (!lat.isNullOrEmpty() && !lon.isNullOrEmpty()){
            viewModel.getWeatherByCoordinates(lat, lon)
            navController.navigate(R.id.action_mainFragment_to_ResultsFragmet)
        }
    }

}