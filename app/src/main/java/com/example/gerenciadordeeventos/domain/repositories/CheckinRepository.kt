package com.example.gerenciadordeeventos.domain.repositories

import com.example.gerenciadordeeventos.domain.model.Checkin
import com.example.gerenciadordeeventos.domain.model.Event
import com.example.gerenciadordeeventos.helper.ResultX


interface CheckinRepository {
    suspend fun setCheckin(checkin : Checkin): ResultX<Boolean>
}