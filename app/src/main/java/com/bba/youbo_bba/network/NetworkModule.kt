package com.bba.youbo_bba.network

/**
 * 网络模块入口
 *
 * 使用示例：
 * ```kotlin
 * // 1. 获取 ApiService
 * val apiService = RetrofitClient.createService(ApiService::class.java)
 *
 * // 2. 在协程中调用（需在 ViewModel 或 Repository 中）
 * viewModelScope.launch {
 *     when (val result = apiService.getExample("param").toApiResult()) {
 *         is ApiResult.Success -> { /* 处理成功 */ }
 *         is ApiResult.Error -> { /* 处理错误 */ }
 *         is ApiResult.RequestException -> { /* 处理异常 */ }
 *     }
 * }
 * ```
 */
object NetworkModule {

    /**
     * 获取默认的 ApiService 实例
     */
    val apiService: ApiService by lazy {
        RetrofitClient.createService(ApiService::class.java)
    }
}
