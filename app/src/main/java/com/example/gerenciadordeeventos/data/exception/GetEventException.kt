package com.example.gerenciadordeeventos.data.exception

import com.example.gerenciadordeeventos.helper.exception.DomainException

class GetEventException : DomainException(){
    override val message: String
        get() = "Erro de regra de negócio"
}