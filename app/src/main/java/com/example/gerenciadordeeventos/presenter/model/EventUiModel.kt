package com.example.gerenciadordeeventos.presenter.model


import com.example.gerenciadordeeventos.domain.model.Event
import java.io.Serializable

data class EventUiModel(
    val id : String,
    val image : String,
    val title : String,
    val price : String,
    val description : String = ""
) : Serializable

fun Event.toUiModel() = EventUiModel(
    id = this.id,
    image = this.image,
    title = this.title,
    price = "R$ " + this.price,
    description = this.description
)