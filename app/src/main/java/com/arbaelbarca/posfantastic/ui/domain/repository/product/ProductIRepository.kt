package com.arbaelbarca.posfantastic.ui.domain.repository.product

import com.arbaelbarca.posfantastic.ui.model.response.ProductResponseModel
import com.arbaelbarca.posfantastic.ui.presentation.state.UiState
import kotlinx.coroutines.flow.Flow

interface ProductIRepository {
    suspend fun callProductList(): Flow<UiState<List<ProductResponseModel>>>
}