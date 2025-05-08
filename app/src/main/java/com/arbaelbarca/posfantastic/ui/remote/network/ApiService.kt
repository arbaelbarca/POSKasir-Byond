package com.arbaelbarca.posfantastic.ui.remote.network

import com.arbaelbarca.posfantastic.ui.model.response.ProductResponseModel
import com.arbaelbarca.posfantastic.ui.model.response.UsersResponse
import org.json.JSONObject
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

interface ApiService {


    @GET("users")
    suspend fun callApiUsers(): List<UsersResponse.UsersResponseItem>

    @Headers("User-ID: 1")
    @GET("main-service/api/v1/products")
    suspend fun callApiProductList(
    ): List<ProductResponseModel>

    @Headers("User-ID: 1")
    @POST("main-service/api/v1/products")
    suspend fun callApiAddProductList(
    ): JSONObject
}