package com.dubizzle.classifiedapp.utils

sealed class NetworkApiResult<T>(
    val data: T? = null,
    val message: String? = null
) {
    class Success<T>(data: T) : NetworkApiResult<T>(data)
    class Error<T>(message: String, data: T? = null) : NetworkApiResult<T>(data, message)
    class Loading<T> : NetworkApiResult<T>()
}