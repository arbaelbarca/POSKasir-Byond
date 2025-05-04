package com.arbaelbarca.posfantastic.ui.presentation.state

sealed class UiState<out R> {
    data class Success<out T>(val data: T) : UiState<T>()
    data class Error(val exception: Throwable) : UiState<Nothing>()
    data object Loading : UiState<Nothing>()
}