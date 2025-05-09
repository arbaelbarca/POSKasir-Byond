package com.arbaelbarca.posfantastic.data.model.request

data class PredictPrice(
    val productCategory: String,
    val buyingPrice: Double,
    val buyQuantity: Int,
    val productType: String
)
