package com.arbaelbarca.posfantastic.data.remote.repository.users

import com.arbaelbarca.posfantastic.data.model.response.UsersResponse
import com.arbaelbarca.posfantastic.ui.presentation.state.UiState
import kotlinx.coroutines.flow.Flow

interface UsersIRepository {
    suspend fun callDataUsers(): Flow<UiState<List<UsersResponse.UsersResponseItem>>>
}