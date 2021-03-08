package com.test.kecipirtest.data.model.product

import com.google.gson.annotations.SerializedName

class ProductResponse(
    @SerializedName("code")
    val code: Int,
    @SerializedName("data")
    val data: List<Product>,
    @SerializedName("message")
    val message: String
)