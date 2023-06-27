package com.example.openweather

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import org.koin.android.BuildConfig
import org.koin.dsl.module
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create


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
                .baseUrl(com.example.openweather.BuildConfig.baseUrl)
                .addConverterFactory(get())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
    }

    single<Api>{
        get<Retrofit>().create(Api::class.java)
    }
}