package com.arbaelbarca.posfantastic.ui.remote.network

import com.arbaelbarca.posfantastic.ui.model.response.ProductsResponse
import com.arbaelbarca.posfantastic.ui.model.response.UsersResponse
import retrofit2.http.GET
import retrofit2.http.Headers

interface ApiService {


    @GET("users")
    suspend fun callApiUsers(): List<UsersResponse.UsersResponseItem>

    @Headers("User-ID: 1")
    @GET("main-service/api/v1/products")
    suspend fun callApiProductList(
    ): List<ProductsResponse>
}