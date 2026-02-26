package com.bba.youbo_bba.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bba.youbo_bba.base.BaseViewModel

/**
 * 消息 Tab ViewModel
 */
class MessageViewModel : BaseViewModel() {

    private val _uiState = MutableLiveData<MessageUiState>(MessageUiState(content = "消息"))
    val uiState: LiveData<MessageUiState> = _uiState

    fun loadData() {
        // 后续接入 Repository 加载数据
    }
}

data class MessageUiState(
    val content: String = "消息"
)
