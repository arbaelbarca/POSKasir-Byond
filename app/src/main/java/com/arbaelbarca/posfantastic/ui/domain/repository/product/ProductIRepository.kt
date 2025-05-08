package com.arbaelbarca.posfantastic.ui.domain.repository.product

import com.arbaelbarca.posfantastic.ui.model.request.AddProductRequest
import com.arbaelbarca.posfantastic.ui.model.response.CategoriesResponseModel
import com.arbaelbarca.posfantastic.ui.model.response.ProductResponseModel
import com.arbaelbarca.posfantastic.ui.presentation.state.UiState
import kotlinx.coroutines.flow.Flow
import org.json.JSONObject

interface ProductIRepository {
    suspend fun callProductList(): Flow<UiState<List<ProductResponseModel>>>
    suspend fun callAddProduct(addProductRequest: AddProductRequest): Flow<UiState<JSONObject>>
    suspend fun callCategoriesList(): Flow<UiState<List<CategoriesResponseModel>>>

}