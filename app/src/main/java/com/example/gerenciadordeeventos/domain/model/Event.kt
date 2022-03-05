package com.example.gerenciadordeeventos.domain.model

data class Event(
    val id : String,
    val image : String,
    val title : String,
    val price : String,
    val description : String = ""
)