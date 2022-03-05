package com.example.gerenciadordeeventos.data.model

import com.example.gerenciadordeeventos.domain.model.Checkin

data class CheckinRequest (
    val eventId : String,
    val name : String,
    val email : String
)
fun CheckinRequest.toCheckin() = Checkin(
    eventId = this.eventId,
    name = this.name,
    email = this.email
)