package com.demba.localareanavigator.network

import com.demba.localareanavigator.network.models.Place
import io.reactivex.Completable

import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path

interface BackendService {
    @GET("/place")
    fun getPlaces(): Single<List<Place>>

    @GET("/place/{name}")
    fun getPlace(@Path("name") name: String): Single<Place>

    @PUT("/place")
    fun putPlace(@Body place: Place): Completable
}
