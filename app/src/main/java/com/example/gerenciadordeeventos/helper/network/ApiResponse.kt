package com.example.gerenciadordeeventos.helper.network
import retrofit2.Response
import java.net.HttpURLConnection

sealed class ApiResponse<out Response> {
    data class Success<Response> (val value : Response): ApiResponse<Response>()
    data class Failure(val statusCode: Int): ApiResponse<Nothing>()
}

fun <R : Any> Response<R>.parse(): ApiResponse<R> {
    if (isSuccessful) {
        val body = body()

        if (body != null) {
            return ApiResponse.Success(body)
        }
    } else {
        return ApiResponse.Failure(code())

    }
    return ApiResponse.Failure(HttpURLConnection.HTTP_INTERNAL_ERROR)
}