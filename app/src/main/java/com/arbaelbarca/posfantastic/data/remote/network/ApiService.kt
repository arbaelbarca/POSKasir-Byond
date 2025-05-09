package com.arbaelbarca.posfantastic.data.remote.network

import com.arbaelbarca.posfantastic.data.model.request.PredictPrice
import com.arbaelbarca.posfantastic.data.model.response.PredictPriceResponse
import com.arbaelbarca.posfantastic.data.model.response.ProductsResponse
import com.arbaelbarca.posfantastic.data.model.response.UsersResponse
import com.arbaelbarca.posfantastic.data.model.request.AddProductRequest
import CategoriesResponseModel
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

    @Headers("User-ID: 1") //FOR TESTING
    @POST("machine-learning-service/api/v1/optimal-price/predict")
    suspend fun getPredictOptimalPrice(
        @Body predictPriceResponse: PredictPrice
    ): PredictPriceResponse

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