package com.example.passforwork.core.base.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitProvider {
    private val retrofit = Retrofit.Builder()
        .baseUrl("http://37.1.216.212:8080/api/")//TODO пофиксить http
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    //TODO Добавить таймауты
    //TODO Добавить token в хедер

    val service: ApiService = retrofit.create(ApiService::class.java)
}