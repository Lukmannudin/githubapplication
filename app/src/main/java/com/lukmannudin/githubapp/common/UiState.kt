package com.lukmannudin.githubapp.common

sealed class UiState<out T> {
    data class Loading<out R>(val value: R? = null) : UiState<R>()
    data class Success<out R>(val value: R? = null) : UiState<R>()
    data class Failure(val exception: Exception?) : UiState<Nothing>()
}