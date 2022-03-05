package com.example.gerenciadordeeventos.domain.usecases

import com.example.gerenciadordeeventos.domain.model.Event
import com.example.gerenciadordeeventos.domain.repositories.EventRepository
import com.example.gerenciadordeeventos.helper.domain.UseCase


class GetEventId(private val rep: EventRepository) : UseCase<Event> {

    private lateinit var id: String
    fun setId(id: String){
        this.id=id
    }

    override suspend fun invoke() = rep.getEventId(id)
}