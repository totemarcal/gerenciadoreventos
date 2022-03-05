package com.example.gerenciadordeeventos.domain.usecases

import com.example.gerenciadordeeventos.domain.model.Event
import com.example.gerenciadordeeventos.domain.repositories.EventRepository
import com.example.gerenciadordeeventos.helper.domain.UseCase


class GetEvent(private val rep: EventRepository) : UseCase<List<Event>> {
    override suspend fun invoke() = rep.getEvent()
}