package com.arbaelbarca.posfantastic.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arbaelbarca.posfantastic.ui.domain.repository.product.ProductRepository
import com.arbaelbarca.posfantastic.ui.model.response.ProductsResponse
import com.arbaelbarca.posfantastic.ui.model.request.AddProductRequest
import com.arbaelbarca.posfantastic.ui.model.response.CategoriesResponseModel
import com.arbaelbarca.posfantastic.ui.model.response.ProductResponseModel
import com.arbaelbarca.posfantastic.ui.model.response.UsersResponse
import com.arbaelbarca.posfantastic.ui.presentation.state.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.json.JSONObject
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    val productRepository: ProductRepository
) : ViewModel() {

    val mutableStateProduct = MutableStateFlow<UiState<List<ProductsResponse>>>(UiState.Loading)
    val stateProduct: StateFlow<UiState<List<ProductsResponse>>> = mutableStateProduct

    val mutableStateAddProduct = MutableStateFlow<UiState<JSONObject>>(UiState.Loading)
    val stateAddProduct: StateFlow<UiState<JSONObject>> = mutableStateAddProduct


    val mutableStateCategories = MutableStateFlow<UiState<List<CategoriesResponseModel>>>(UiState.Loading)
    val stateCategories: StateFlow<UiState<List<CategoriesResponseModel>>> = mutableStateCategories

    fun fetchDataProductList() {
        viewModelScope.launch {
            productRepository.callProductList().collect { state ->
                mutableStateProduct.value = state
            }
        }
    }

    fun fetchAddProduct(addProductRequest: AddProductRequest) {
        viewModelScope.launch {
            productRepository.callAddProduct(addProductRequest).collect { state ->
                mutableStateAddProduct.value = state
            }
        }
    }

    fun fetchCategoriesList() {
        viewModelScope.launch {
            productRepository.callCategoriesList().collect { state ->
                mutableStateCategories.value = state
            }
        }
    }
}