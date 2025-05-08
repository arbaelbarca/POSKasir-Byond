package com.arbaelbarca.posfantastic.ui.model.response


import com.google.gson.annotations.SerializedName

data class CategoriesResponseModel(
    @SerializedName("applicationStatus")
    val applicationStatus: String?,
    @SerializedName("data")
    val data: List<DataCategories?>?,
    @SerializedName("status")
    val status: Int?
) {
    data class DataCategories(
        @SerializedName("gender")
        val gender: String?,
        @SerializedName("id")
        val id: Int?,
        @SerializedName("name")
        val name: String?
    )
}