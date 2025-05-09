package com.arbaelbarca.posfantastic.data.remote.repository.product

import com.arbaelbarca.posfantastic.data.model.request.PredictPrice
import com.arbaelbarca.posfantastic.data.model.response.PredictPriceResponse
import com.arbaelbarca.posfantastic.data.model.response.ProductsResponse
import com.arbaelbarca.posfantastic.data.model.request.AddProductRequest
import com.arbaelbarca.posfantastic.ui.presentation.state.UiState
import kotlinx.coroutines.flow.Flow
import org.json.JSONObject

interface ProductIRepository {
    suspend fun callProductList(): Flow<UiState<List<ProductsResponse>>>
    suspend fun getPredictPrice(product: PredictPrice): Flow<UiState<PredictPriceResponse>>
    suspend fun callAddProduct(addProductRequest: AddProductRequest): Flow<UiState<JSONObject>>
    suspend fun callCategoriesList(): Flow<UiState<List<CategoriesResponseModel>>>
}