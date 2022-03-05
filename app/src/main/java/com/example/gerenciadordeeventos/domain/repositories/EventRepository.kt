package com.example.gerenciadordeeventos.domain.repositories

import com.example.gerenciadordeeventos.domain.model.Event
import com.example.gerenciadordeeventos.helper.ResultX


interface EventRepository {
    suspend fun getEvent(): ResultX<List<Event>>
    suspend fun getEventId(id: String): ResultX<Event>
}