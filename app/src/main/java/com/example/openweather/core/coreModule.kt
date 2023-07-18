package com.example.openweather

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import org.koin.android.BuildConfig
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create

//const val BASE_URL = com.example.openweather.BuildConfig.baseUrl
const val BASE_URL = "https://api.openweathermap.org"
var coreModule = module {

    factory<Moshi> {
        Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

    }

    factory<Converter.Factory> {
        MoshiConverterFactory.create(get()).asLenient()
    }


    factory<Retrofit> {
            Retrofit
                .Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(get())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
    }

    single<Api>{
        get<Retrofit>().create(Api::class.java)
    }

    single{
        getSharedPrefs(androidApplication())
    }

    single <SharedPreferences.Editor> {
        getSharedPrefs(androidApplication()).edit()
    }
}

fun getSharedPrefs(androidApplication: Application): SharedPreferences{
    return  androidApplication.getSharedPreferences(LAST_LAT_LON, Context.MODE_PRIVATE)
}