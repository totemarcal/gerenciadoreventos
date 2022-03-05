package com.example.gerenciadordeeventos.helper.domain

import com.example.gerenciadordeeventos.helper.ResultX

interface UseCase <T> {
    suspend operator fun invoke(): ResultX<T>
}
