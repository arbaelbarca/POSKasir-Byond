package com.arbaelbarca.posfantastic.ui.model.response


import com.google.gson.annotations.SerializedName

data class ProductResponseModel(
    @SerializedName("applicationStatus")
    val applicationStatus: String?,
    @SerializedName("data")
    val dataList: List<Data?>?,
    @SerializedName("status")
    val status: Int?
) {
    data class Data(
        @SerializedName("categoryName")
        val categoryName: String?,
        @SerializedName("createdAt")
        val createdAt: String?,
        @SerializedName("id")
        val id: Int?,
        @SerializedName("initialPrice")
        val initialPrice: Double?,
        @SerializedName("name")
        val name: String?,
        @SerializedName("sellingPrice")
        val sellingPrice: Double?,
        @SerializedName("stock")
        val stock: Int?,
        @SerializedName("updatedAt")
        val updatedAt: String?
    )
}