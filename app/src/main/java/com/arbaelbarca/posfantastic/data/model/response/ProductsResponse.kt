package com.arbaelbarca.posfantastic.data.model.response


import com.google.gson.annotations.SerializedName

data class ProductsResponse(
    @SerializedName("applicationStatus")
    val applicationStatus: String?,
    @SerializedName("data")
    val dataList: List<ProductItem?>?,
    @SerializedName("status")
    val status: Int?
) {
    data class ProductItem(
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
        val updatedAt: String?,
        @SerializedName("imageUrl")
        val imageUrl: String?,
        var quantity: Int = 0
    )
}