package com.arbaelbarca.posfantastic.ui.remote.network

import com.arbaelbarca.posfantastic.ui.model.request.AddProductRequest
import com.arbaelbarca.posfantastic.ui.model.response.CategoriesResponseModel
import com.arbaelbarca.posfantastic.ui.model.response.ProductsResponse
import com.arbaelbarca.posfantastic.ui.model.response.UsersResponse
import org.json.JSONObject
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST

interface ApiService {


    @GET("users")
    suspend fun callApiUsers(): List<UsersResponse.UsersResponseItem>

    @Headers("User-ID: 1")
    @GET("main-service/api/v1/products")
    suspend fun callApiProductList(
    ): List<ProductsResponse>

    @Headers("User-ID: 1")
    @POST("main-service/api/v1/products")
    suspend fun callApiAddProductList(
        @Body addProductRequest: AddProductRequest
    ): JSONObject

    @Headers("User-ID: 1")
    @GET("main-service/api/v1/categories")
    suspend fun callApiCategoryList(
    ): List<CategoriesResponseModel>
}