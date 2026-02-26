package com.bba.youbo_bba.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.bba.youbo_bba.base.BaseViewModel
import com.bba.youbo_bba.network.ApiResult
import com.bba.youbo_bba.repository.MainRepository
import kotlinx.coroutines.launch

/**
 * MainActivity 对应的 ViewModel
 * 持有 UI 状态，通过 LiveData 暴露给 View 观察
 */
class MainViewModel : BaseViewModel() {

    private val repository = MainRepository()

    private val _uiState = MutableLiveData<MainUiState>(MainUiState.Loading)
    val uiState: LiveData<MainUiState> = _uiState

    init {
        loadData()
    }

    /**
     * 加载数据
     */
    fun loadData() {
        viewModelScope.launch {
            _uiState.value = MainUiState.Loading
            when (val result = repository.fetchData()) {
                is ApiResult.Success -> {
                    _uiState.value = MainUiState.Success(result.data)
                }
                is ApiResult.Error -> {
                    _uiState.value = MainUiState.Error(result.message)
                }
                is ApiResult.RequestException -> {
                    _uiState.value = MainUiState.Error(result.e.message ?: "未知错误")
                }
            }
        }
    }
}

/**
 * Main 界面 UI 状态
 */
sealed class MainUiState {
    data object Loading : MainUiState()
    data class Success(val message: String) : MainUiState()
    data class Error(val message: String) : MainUiState()
}
