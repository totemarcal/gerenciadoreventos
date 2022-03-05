package com.example.gerenciadordeeventos.data.repositories

import com.example.cleanmvvmapp.data.exception.NotFoundException
import com.example.cleanmvvmapp.data.exception.ServerException
import com.example.gerenciadordeeventos.data.api.EventApi
import com.example.gerenciadordeeventos.data.exception.GetEventException
import com.example.gerenciadordeeventos.data.exception.SetChekingException
import com.example.gerenciadordeeventos.data.model.toCheckin
import com.example.gerenciadordeeventos.data.model.toEvent
import com.example.gerenciadordeeventos.domain.model.Checkin
import com.example.gerenciadordeeventos.domain.model.Event
import com.example.gerenciadordeeventos.domain.model.toCheckinRequest
import com.example.gerenciadordeeventos.domain.repositories.CheckinRepository
import com.example.gerenciadordeeventos.domain.repositories.EventRepository
import com.example.gerenciadordeeventos.helper.ResultX
import com.example.gerenciadordeeventos.helper.network.ApiResponse
import com.example.gerenciadordeeventos.helper.network.parse

class CheckinRepositoryImpl (private val api: EventApi) : CheckinRepository {

    override suspend fun setCheckin(checkin: Checkin): ResultX<Boolean> {
        val response = api.setCheckin(checkin.toCheckinRequest()).parse()

        return when (response) {
            is ApiResponse.Success -> {
                ResultX.Success(true)
            }
            is ApiResponse.Failure -> {
                return when (response.statusCode) {
                    404 -> ResultX.Failure(NotFoundException())
                    500 -> ResultX.Failure(ServerException())
                    else -> ResultX.Failure(SetChekingException())
                }
            }
        }
    }
}