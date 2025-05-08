package com.arbaelbarca.posfantastic.ui.domain.repository.product

import com.arbaelbarca.posfantastic.ui.model.request.AddProductRequest
import com.arbaelbarca.posfantastic.ui.model.response.CategoriesResponseModel
import com.arbaelbarca.posfantastic.ui.model.response.ProductsResponse
import com.arbaelbarca.posfantastic.ui.presentation.state.UiState
import com.arbaelbarca.posfantastic.ui.remote.network.ApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.json.JSONObject
import javax.inject.Inject

class ProductRepository @Inject constructor(
    val apiService: ApiService
) : ProductIRepository {
    override suspend fun callProductList(): Flow<UiState<List<ProductsResponse>>> = flow {
        emit(UiState.Loading)
        runCatching {
            val getResponse = apiService.callApiProductList()
            emit(UiState.Success(getResponse))
        }.onFailure {
            emit(UiState.Error(it))
            it.printStackTrace()
        }
    }

    override suspend fun callAddProduct(addProductRequest: AddProductRequest): Flow<UiState<JSONObject>> = flow {
        emit(UiState.Loading)
        runCatching {
            val getResponse = apiService.callApiAddProductList(addProductRequest)
            emit(UiState.Success(getResponse))
        }.onFailure {
            emit(UiState.Error(it))
            it.printStackTrace()
        }
    }

    override suspend fun callCategoriesList(): Flow<UiState<List<CategoriesResponseModel>>> = flow {
        emit(UiState.Loading)
        runCatching {
            val getResponse = apiService.callApiCategoryList()
            emit(UiState.Success(getResponse))
        }.onFailure {
            emit(UiState.Error(it))
            it.printStackTrace()
        }
    }

}