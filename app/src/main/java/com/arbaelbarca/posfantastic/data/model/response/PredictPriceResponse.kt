package com.arbaelbarca.posfantastic.data.model.response

data class PredictPriceResponse(
    val buyQuantity: Int = 0,
    val buyingPrice: Double = 0.0,
    val optimalPrice: Double  = 0.0,
    val productCategory: String = "",
    val productType: String = "",
    val userId: Int = 0
)
