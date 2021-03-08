package com.test.kecipirtest.network.remote

import com.test.kecipirtest.data.model.banner.BannerResponse
import com.test.kecipirtest.data.model.category.CategoryResponse
import com.test.kecipirtest.data.model.product.ProductResponse

class Api(val apiService: ApiService) : ApiService {
    override suspend fun getProducts(): ProductResponse {
        return apiService.getProducts()
    }

    override suspend fun getBanner(): BannerResponse {
        return apiService.getBanner()
    }

    override suspend fun getCategory(): CategoryResponse {
        return apiService.getCategory()
    }
}