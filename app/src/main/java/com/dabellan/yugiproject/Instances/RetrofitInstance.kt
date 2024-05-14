package com.dabellan.yugiproject.Instances

import com.dabellan.yugiproject.Services.RetrofitService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitInstance {
    private const val BASE_URL = "http://yugi.navelsystems.es"
    val api: RetrofitService by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        retrofit.create(RetrofitService::class.java)
    }
}