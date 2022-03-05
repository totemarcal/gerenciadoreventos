package com.example.gerenciadordeeventos.data.repositories

import com.example.cleanmvvmapp.data.exception.NotFoundException
import com.example.cleanmvvmapp.data.exception.ServerException
import com.example.gerenciadordeeventos.data.api.EventApi
import com.example.gerenciadordeeventos.data.exception.GetEventException
import com.example.gerenciadordeeventos.data.model.toEvent
import com.example.gerenciadordeeventos.domain.model.Event
import com.example.gerenciadordeeventos.domain.repositories.EventRepository
import com.example.gerenciadordeeventos.helper.ResultX
import com.example.gerenciadordeeventos.helper.network.ApiResponse
import com.example.gerenciadordeeventos.helper.network.parse

class EventRepositoryImpl (private val api: EventApi) : EventRepository {

    override suspend fun getEvent(): ResultX<List<Event>> {
        val response = api.getEvent().parse()

        return when (response) {
            is ApiResponse.Success -> {
                var listEvent = response.value.map {
                    it.toEvent()
                }
                ResultX.Success(listEvent)
            }
            is ApiResponse.Failure -> {
                return when (response.statusCode) {
                    404 -> ResultX.Failure(NotFoundException())
                    500 -> ResultX.Failure(ServerException())
                    else -> ResultX.Failure(GetEventException())
                }
            }
        }
    }

    override suspend fun getEventId(id: String): ResultX<Event> {
        val response = api.getEventID(id).parse()

        return when (response) {
            is ApiResponse.Success -> {
                ResultX.Success(response.value.toEvent())
            }
            is ApiResponse.Failure -> {
                return when (response.statusCode) {
                    404 -> ResultX.Failure(NotFoundException())
                    500 -> ResultX.Failure(ServerException())
                    else -> ResultX.Failure(GetEventException())
                }
            }
        }
    }
}