package com.example.gerenciadordeeventos.domain.model

import com.example.gerenciadordeeventos.data.model.CheckinRequest

data class Checkin(
    val eventId : String,
    val name : String,
    val email : String
)
fun Checkin.toCheckinRequest() = CheckinRequest(
    eventId = this.eventId,
    name = this.name,
    email = this.email
)