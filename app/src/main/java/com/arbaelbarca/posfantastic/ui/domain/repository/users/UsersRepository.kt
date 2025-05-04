package com.arbaelbarca.posfantastic.ui.domain.repository.users

import com.arbaelbarca.posfantastic.ui.model.response.UsersResponse
import com.arbaelbarca.posfantastic.ui.presentation.state.UiState
import com.arbaelbarca.posfantastic.ui.remote.network.ApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UsersRepository @Inject constructor(
    val apiService: ApiService
) : UsersIRepository {
    override suspend fun callDataUsers(): Flow<UiState<List<UsersResponse.UsersResponseItem>>> = flow {
        emit(UiState.Loading)
        runCatching {
            val getResponse = apiService.callApiUsers()
            emit(UiState.Success(getResponse))
        }.onFailure {
            emit(UiState.Error(it))
            it.printStackTrace()
        }
    }
}