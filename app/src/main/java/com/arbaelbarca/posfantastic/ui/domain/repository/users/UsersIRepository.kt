package com.arbaelbarca.posfantastic.ui.domain.repository.users

import com.arbaelbarca.posfantastic.ui.model.response.UsersResponse
import com.arbaelbarca.posfantastic.ui.presentation.state.UiState
import kotlinx.coroutines.flow.Flow

interface UsersIRepository {
    suspend fun callDataUsers(): Flow<UiState<List<UsersResponse.UsersResponseItem>>>
}