package com.example.openweather

import android.app.AlertDialog
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.openweather.ui.main.MainFragment
import com.example.openweather.ui.main.MainViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

const val REQUEST_CODE = 100

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

        checkPermission()
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

}