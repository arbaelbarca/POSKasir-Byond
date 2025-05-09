package com.arbaelbarca.posfantastic.data.remote.repository.product

import CategoriesResponseModel
import com.arbaelbarca.posfantastic.data.model.request.PredictPrice
import com.arbaelbarca.posfantastic.data.model.response.PredictPriceResponse
import com.arbaelbarca.posfantastic.data.model.response.ProductsResponse
import com.arbaelbarca.posfantastic.ui.presentation.state.UiState
import com.arbaelbarca.posfantastic.data.remote.network.ApiService
import com.arbaelbarca.posfantastic.data.model.request.AddProductRequest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.json.JSONObject
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProductRepository @Inject constructor(
    private val apiService: ApiService
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

    override suspend fun getPredictPrice(product: PredictPrice): Flow<UiState<PredictPriceResponse>> = flow {
        emit(UiState.Loading)
        runCatching {
            val getResponse = apiService.getPredictOptimalPrice(product)
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