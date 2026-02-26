package com.bba.youbo_bba.network

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * API 接口定义
 * 在此添加你的接口方法
 */
interface ApiService {

    /**
     * 示例：GET 请求
     * 使用时替换为实际接口路径和参数
     */
    @GET("example")
    suspend fun getExample(@Query("param") param: String): Response<ExampleResponse>

    // 在此添加更多接口方法...
}

/**
 * 示例响应数据类，请根据实际 API 结构修改
 */
data class ExampleResponse(
    val code: Int,
    val message: String?,
    val data: Any? = null
)
