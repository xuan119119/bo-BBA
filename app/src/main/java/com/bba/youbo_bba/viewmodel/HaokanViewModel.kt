package com.bba.youbo_bba.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bba.youbo_bba.base.BaseViewModel

/**
 * 好看 Tab ViewModel
 */
class HaokanViewModel : BaseViewModel() {

    private val _uiState = MutableLiveData<HaokanUiState>(HaokanUiState(content = "好看"))
    val uiState: LiveData<HaokanUiState> = _uiState

    fun loadData() {
        // 后续接入 Repository 加载数据
    }
}

data class HaokanUiState(
    val content: String = "好看"
)
