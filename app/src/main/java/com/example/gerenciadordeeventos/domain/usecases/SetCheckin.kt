package com.example.gerenciadordeeventos.domain.usecases

import com.example.gerenciadordeeventos.domain.model.Checkin
import com.example.gerenciadordeeventos.domain.model.Event
import com.example.gerenciadordeeventos.domain.repositories.CheckinRepository
import com.example.gerenciadordeeventos.domain.repositories.EventRepository
import com.example.gerenciadordeeventos.helper.domain.UseCase


class SetCheckin(private val rep: CheckinRepository) : UseCase<Boolean> {
    private lateinit var checkin: Checkin
    fun setCheckin(checkin: Checkin){
        this.checkin=checkin
    }

    override suspend fun invoke() = rep.setCheckin(checkin)
}