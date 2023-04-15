package com.example.passforwork.core.base.network

import com.example.passforwork.features.registration.data.RegData
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {

    @POST("create_reg")
    suspend fun createReg(@Body regData: RegData)
}