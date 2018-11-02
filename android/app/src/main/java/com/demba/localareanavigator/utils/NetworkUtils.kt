package com.demba.localareanavigator.utils

import com.demba.localareanavigator.network.BackendService
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object NetworkUtils {
    fun getBackendService(): BackendService {
        val retrofit = Retrofit.Builder()
                .baseUrl("https://local-area-navigator.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()

        return retrofit.create(BackendService::class.java)
    }
}