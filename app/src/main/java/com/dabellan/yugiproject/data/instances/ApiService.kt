package com.dabellan.yugiproject.data.instances

import com.dabellan.yugiproject.data.services.RetrofitService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val apiService: RetrofitService by lazy {
    Retrofit.Builder()
        .baseUrl("http://yugi.navelsystems.es")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(RetrofitService::class.java)
}