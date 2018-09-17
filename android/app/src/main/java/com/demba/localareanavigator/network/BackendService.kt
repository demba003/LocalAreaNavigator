package com.demba.localareanavigator.network

import com.demba.localareanavigator.network.models.Place

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface BackendService {
    @GET("/places")
    fun getPlaces(): Single<List<Place>>

    @GET("/places/{name}")
    fun getPlace(@Path("name") name: String): Single<Place>
}
