package com.arbaelbarca.posfantastic.ui.domain.repository.product

import com.arbaelbarca.posfantastic.ui.model.response.ProductResponseModel
import com.arbaelbarca.posfantastic.ui.presentation.state.UiState
import com.arbaelbarca.posfantastic.ui.remote.network.ApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ProductRepository @Inject constructor(
    val apiService: ApiService
) : ProductIRepository {
    override suspend fun callProductList(): Flow<UiState<List<ProductResponseModel>>> = flow {
        emit(UiState.Loading)
        runCatching {
            val getResponse = apiService.callApiProductList()
            emit(UiState.Success(getResponse))
        }.onFailure {
            emit(UiState.Error(it))
            it.printStackTrace()
        }
    }

}