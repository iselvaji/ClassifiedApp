package com.dubizzle.classifiedapp.model

import com.dubizzle.classifiedapp.utils.NetworkApiResult
import retrofit2.Response

open class BaseApiResponse {

    suspend fun <T> safeApiCall(apiCall: suspend () -> Response<T>): NetworkApiResult<T> {
        try {
            val response = apiCall()
            if (response.isSuccessful) {
                val body = response.body()
                body?.let {
                    return NetworkApiResult.Success(body)
                }
            }
            return error("${response.code()} ${response.message()}")
        } catch (e: Exception) {
            return error(e.message ?: e.toString())
        }
    }

    private fun <T> error(errorMessage: String): NetworkApiResult<T> =
        NetworkApiResult.Error("Api call failed $errorMessage")
}