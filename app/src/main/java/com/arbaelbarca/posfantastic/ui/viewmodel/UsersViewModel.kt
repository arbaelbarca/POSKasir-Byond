package com.arbaelbarca.posfantastic.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arbaelbarca.posfantastic.ui.domain.repository.users.UsersIRepository
import com.arbaelbarca.posfantastic.ui.model.response.UsersResponse
import com.arbaelbarca.posfantastic.ui.presentation.state.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UsersViewModel @Inject constructor(
    val usersIRepository: UsersIRepository
) : ViewModel() {

    val mutableStateUsers = MutableStateFlow<UiState<List<UsersResponse.UsersResponseItem>>>(UiState.Loading)
    val stateUsers: StateFlow<UiState<List<UsersResponse.UsersResponseItem>>> = mutableStateUsers

    fun fetchDataUsers() {
        viewModelScope.launch {
            // Panggil data dari repository dan update UI State
            usersIRepository.callDataUsers()
                .collect { state ->
                    mutableStateUsers.value = state
                }
        }
    }
}