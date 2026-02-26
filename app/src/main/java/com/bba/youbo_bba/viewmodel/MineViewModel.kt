package com.bba.youbo_bba.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bba.youbo_bba.base.BaseViewModel

/**
 * 我的 Tab ViewModel
 */
class MineViewModel : BaseViewModel() {

    private val _uiState = MutableLiveData<MineUiState>(MineUiState(content = "我的"))
    val uiState: LiveData<MineUiState> = _uiState

    fun loadData() {
        // 后续接入 Repository 加载数据
    }
}

data class MineUiState(
    val content: String = "我的"
)
