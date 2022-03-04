package com.example.gerenciadordeeventos.data.api

import com.example.gerenciadordeeventos.data.model.EventResponse
import retrofit2.Response
import retrofit2.http.GET

interface EventApi {
    @GET("api/events")
    suspend fun getEvent(): Response<List<EventResponse>>
}