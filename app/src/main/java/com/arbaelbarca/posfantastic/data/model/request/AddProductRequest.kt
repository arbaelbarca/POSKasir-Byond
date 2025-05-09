package com.arbaelbarca.posfantastic.data.model.request


import com.google.gson.annotations.SerializedName

data class AddProductRequest(
    @SerializedName("categoryId")
    val categoryId: Int?,
    @SerializedName("imageUrl")
    val imageUrl: String?,
    @SerializedName("initialPrice")
    val initialPrice: Double?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("sellingPrice")
    val sellingPrice: Double?,
    @SerializedName("stock")
    val stock: Int?
)