package com.demba.localareanavigator.network

import com.demba.localareanavigator.network.models.Place

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface BackendService {
    @GET("/place")
    fun getPlaces(): Single<List<Place>>

    @GET("/place/{name}")
    fun getPlace(@Path("name") name: String): Single<Place>
}
