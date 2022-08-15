package com.lukmannudin.githubapp.common.extension

import com.lukmannudin.githubapp.common.UiState

inline fun <reified T> UiState<T>.onLoading(callback: (value: T?) -> Unit) {
    if (this is UiState.Loading) {
        callback(value)
    }
}

inline fun <reified T> UiState<T>.onSuccess(callback: (value: T?) -> Unit) {
    if (this is UiState.Success) {
        callback(value)
    }
}

inline fun <reified T> UiState<T>.onFailure(callback: (Exception?) -> Unit) {
    if (this is UiState.Failure) {
        callback(exception)
    }
}

inline fun <reified T> UiState<T>.onComplete(callback: (value: T?) -> Unit) {
    if (this is UiState.Success) {
        callback(value)
    } else if (this is UiState.Failure) {
        callback(null)
    }
}