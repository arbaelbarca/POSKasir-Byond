package com.arbaelbarca.posfantastic.ui.remote.network

import com.arbaelbarca.posfantastic.ui.model.response.UsersResponse
import retrofit2.http.GET

interface ApiService {

    @GET("users")
    suspend fun callApiUsers(): List<UsersResponse.UsersResponseItem>
}