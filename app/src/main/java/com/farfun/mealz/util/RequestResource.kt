package com.farfun.mealz.util

sealed class RequestResource<T> {
    class Progress<T> : RequestResource<T>()
    data class Success<T>(var data: T) : RequestResource<T>()
    data class Failure<T>(val message: String) : RequestResource<T>()

    companion object {
        fun <T> loading(): RequestResource<T> = Progress()

        fun <T> success(data: T): RequestResource<T> = Success(data)

        fun <T> failure(e: String): RequestResource<T> = Failure(e)
    }
}