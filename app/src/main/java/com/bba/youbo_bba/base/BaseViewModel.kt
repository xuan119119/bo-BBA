package com.bba.youbo_bba.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch

/**
 * ViewModel 基类，提供统一的异常处理和协程作用域
 */
abstract class BaseViewModel : ViewModel() {

    /**
     * 协程异常处理器，子类可重写以自定义错误处理
     */
    protected val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        handleError(throwable)
    }

    /**
     * 处理异常，子类可重写
     */
    protected open fun handleError(throwable: Throwable) {
        // 可在此添加统一日志、上报等
    }

    /**
     * 在 ViewModel 作用域内启动协程，自动使用异常处理器
     */
    protected fun launchWithErrorHandling(block: suspend () -> Unit) {
        viewModelScope.launch(exceptionHandler) {
            block()
        }
    }
}
