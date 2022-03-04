package com.example.gerenciadordeeventos.helper

sealed class ResultX<out T> {
    open class Success<out T>(val value: T) : ResultX<T>()
    open class Failure<out T>(val error: Throwable) : ResultX<T>()

    open fun getOrNull() : T?{
        return if(this is Success) {
            value
        } else{
            null
        }
    }

    open fun exceptionOrNull() : Throwable?{
        return if(this is Failure) {
            error
        } else{
            null
        }
    }
}
