package com.example.cleanmvvmapp.data.exception

import com.example.gerenciadordeeventos.helper.exception.DomainException


class ServerException : DomainException() {
    override val message: String
        get() = "Erro interno de servidor"
}