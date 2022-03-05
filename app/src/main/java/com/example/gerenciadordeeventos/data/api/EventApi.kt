package com.example.gerenciadordeeventos.data.api

import com.example.gerenciadordeeventos.data.model.CheckinRequest
import com.example.gerenciadordeeventos.data.model.EventResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface EventApi {
    @GET("api/events")
    suspend fun getEvent(): Response<List<EventResponse>>

    @GET("api/events/{id}")
    suspend fun getEventID(@Path("id") id: String): Response<EventResponse>

    @POST("api/checkin")
    suspend fun setCheckin(@Body rquest: CheckinRequest): Response<CheckinRequest>

}