package com.bba.youbo_bba.repository

import com.bba.youbo_bba.network.ApiResult
import com.bba.youbo_bba.network.NetworkModule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * 主界面数据仓库，负责数据获取与缓存
 * Repository 是 ViewModel 的唯一数据源
 */
class MainRepository {

    private val apiService = NetworkModule.apiService

    /**
     * 示例：从网络获取数据
     * 可根据实际 ApiService 接口替换
     */
    suspend fun fetchData(): ApiResult<String> = withContext(Dispatchers.IO) {
        // 示例：模拟或调用实际接口
        // apiService.getExample("param").toApiResult()
        ApiResult.Success("数据加载成功")
    }
}
