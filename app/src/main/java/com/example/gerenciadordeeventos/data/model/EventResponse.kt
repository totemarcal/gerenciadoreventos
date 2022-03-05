package com.example.gerenciadordeeventos.data.model

import com.example.gerenciadordeeventos.domain.model.Event

data class EventResponse (
    val id : String,
    val image : String,
    val title : String,
    val price : String,
    val description : String
)
fun EventResponse.toEvent() = Event(
    id = this.id,
    image = this.image,
    title = this.title,
    price = this.price,
    description = this.description
)