package com.mkiperszmid.parkitcourse.home.data.remote

import com.mkiperszmid.parkitcourse.home.data.remote.dto.DirectionsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface DirectionsApi {
    companion object {
        const val BASE_URL = "https://maps.googleapis.com/"
    }

    @GET("maps/api/directions/json")
    suspend fun getDirections(
        @Query("origin") origin: String,
        @Query("destination") destination: String,
        @Query("key") key: String,
        @Query("mode") mode: String = "walking"
    ): DirectionsResponse
}