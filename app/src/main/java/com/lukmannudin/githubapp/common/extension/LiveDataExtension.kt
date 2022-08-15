package com.lukmannudin.githubapp.common.extension

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.lukmannudin.githubapp.common.UiState

fun <T> MutableLiveData<UiState<T>>.postLoadingState(data: T? = null) {
    postValue(UiState.Loading(data))
}

fun <T> MutableLiveData<UiState<T>>.postFailureState(exception: Exception) {
    postValue(UiState.Failure(exception))
}

fun <T> MutableLiveData<UiState<T>>.postSuccessState(result: T? = null) {
    postValue(UiState.Success(result))
}

fun <T> LiveData<UiState<T>>.value(): T? {
    return if (value is UiState.Success) {
        (value as UiState.Success<T>).value
    } else {
        null
    }
}