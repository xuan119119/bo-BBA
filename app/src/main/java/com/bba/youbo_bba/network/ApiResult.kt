package com.bba.youbo_bba.network

import retrofit2.Response

/**
 * API 调用结果封装，便于 UI 层统一处理成功/失败
 */
sealed class ApiResult<out T> {
    data class Success<T>(val data: T) : ApiResult<T>()
    data class Error(val code: Int, val message: String) : ApiResult<Nothing>()
    data class RequestException(val e: Throwable) : ApiResult<Nothing>()
}

/**
 * 将 Retrofit Response 转换为 ApiResult
 */
fun <T> Response<T>.toApiResult(): ApiResult<T> {
    return try {
        if (isSuccessful) {
            body()?.let { ApiResult.Success(it) }
                ?: ApiResult.Error(code(), "Response body is null")
        } else {
            ApiResult.Error(code(), message())
        }
    } catch (e: Exception) {
        ApiResult.RequestException(e)
    }
}
