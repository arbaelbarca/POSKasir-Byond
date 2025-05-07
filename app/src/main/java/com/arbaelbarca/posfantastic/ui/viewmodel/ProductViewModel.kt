package com.arbaelbarca.posfantastic.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arbaelbarca.posfantastic.ui.domain.repository.product.ProductRepository
import com.arbaelbarca.posfantastic.ui.model.response.ProductResponseModel
import com.arbaelbarca.posfantastic.ui.model.response.UsersResponse
import com.arbaelbarca.posfantastic.ui.presentation.state.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    val productRepository: ProductRepository
) : ViewModel() {

    val mutableStateProduct = MutableStateFlow<UiState<List<ProductResponseModel>>>(UiState.Loading)
    val stateProduct: StateFlow<UiState<List<ProductResponseModel>>> = mutableStateProduct

    fun fetchDataProductList() {
        viewModelScope.launch {
            productRepository.callProductList().collect { state ->
                mutableStateProduct.value = state
            }
        }
    }
}