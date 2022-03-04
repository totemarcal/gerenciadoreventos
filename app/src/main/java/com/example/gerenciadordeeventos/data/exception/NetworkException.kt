package com.example.cleanmvvmapp.data.exception

import com.example.gerenciadordeeventos.helper.exception.DomainException

class NetworkException : DomainException(){
    override val message: String
        get() = "Internet indisponível"
}