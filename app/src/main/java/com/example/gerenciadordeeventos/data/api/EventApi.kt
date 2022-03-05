package com.example.gerenciadordeeventos.data.api

import com.example.gerenciadordeeventos.data.model.EventResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface EventApi {
    @GET("api/events")
    suspend fun getEvent(): Response<List<EventResponse>>

    @GET("api/events/{id}")
    suspend fun getEventID(@Path("id") id: String): Response<EventResponse>

}