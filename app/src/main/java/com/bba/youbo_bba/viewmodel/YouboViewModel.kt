package com.bba.youbo_bba.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bba.youbo_bba.base.BaseViewModel

/**
 * 有播 Tab ViewModel
 */
class YouboViewModel : BaseViewModel() {

    private val _uiState = MutableLiveData<YouboUiState>(YouboUiState(content = "有播"))
    val uiState: LiveData<YouboUiState> = _uiState

    fun loadData() {
        // 后续接入 Repository 加载数据
    }
}

data class YouboUiState(
    val content: String = "有播"
)
