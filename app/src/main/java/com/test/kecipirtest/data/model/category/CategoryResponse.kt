package com.test.kecipirtest.data.model.category

import com.google.gson.annotations.SerializedName


class CategoryResponse(
    @SerializedName("code")
    val code: Int,
    @SerializedName("data")
    val dataCategory: List<Category>,
    @SerializedName("message")
    val message: String
)