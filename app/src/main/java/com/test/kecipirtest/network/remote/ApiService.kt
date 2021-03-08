package com.test.kecipirtest.network.remote

import com.test.kecipirtest.data.model.banner.BannerResponse
import com.test.kecipirtest.data.model.category.CategoryResponse
import com.test.kecipirtest.data.model.product.ProductResponse
import retrofit2.http.GET

interface ApiService{
    @GET("test/products.json")
    suspend fun getProducts(): ProductResponse

    @GET("test/banner.json")
    suspend fun getBanner(): BannerResponse

    @GET("test/category.json")
    suspend fun getCategory(): CategoryResponse
}