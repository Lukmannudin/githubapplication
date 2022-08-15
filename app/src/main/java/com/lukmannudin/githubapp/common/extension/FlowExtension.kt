package com.lukmannudin.githubapp.common.extension

import com.lukmannudin.githubapp.data.model.Result

inline fun <reified T> Result<T>.onLoading(callback: () -> Unit) {
    if (this is Result.Loading) {
        callback.invoke()
    }
}

inline fun <reified T> Result<T>.onFailure(callback: (Exception) -> Unit) {
    if (this is Result.Error) {
        callback.invoke(this.exception)
    }
}

inline fun <reified T> Result<T>.onSuccess(callback: (T) -> Unit) {
    if (this is Result.Success) {
        callback.invoke(this.data)
    }
}