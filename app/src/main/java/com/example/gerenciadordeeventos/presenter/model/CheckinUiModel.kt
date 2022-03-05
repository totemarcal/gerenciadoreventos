package com.example.gerenciadordeeventos.presenter.model


import com.example.gerenciadordeeventos.domain.model.Checkin
import com.example.gerenciadordeeventos.domain.model.Event
import java.io.Serializable

data class CheckinUiModel(
    val eventId : String,
    val name : String,
    val email : String
) : Serializable

fun CheckinUiModel.toCheckin() = Checkin(
    eventId = this.eventId,
    name = this.name,
    email = this.email
)
fun Checkin.toUiModel() = CheckinUiModel(
    eventId = this.eventId,
    name = this.name,
    email = this.email
)