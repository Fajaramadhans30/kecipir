package com.test.kecipirtest.data.model.banner

import com.google.gson.annotations.SerializedName

class BannerResponse(
    @SerializedName("code")
    val code: Int,
    @SerializedName("data")
    val dataBanner: List<Banner>,
    @SerializedName("message")
    val message: String
)