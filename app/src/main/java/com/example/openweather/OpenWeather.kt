package com.example.openweather
import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin
import org.koin.core.logger.Level


class OpenWeather: Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin()
    }

    private fun initKoin(){
        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@OpenWeather)
            modules(listOf(
                coreModule,
                weatherModule,
            ))
        }
    }
}