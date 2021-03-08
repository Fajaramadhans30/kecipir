package com.test.kecipirtest.network.remote

import com.test.kecipirtest.data.model.banner.Banner
import com.test.kecipirtest.data.model.category.Category
import com.test.kecipirtest.data.model.product.Product

interface Repository {
    suspend fun getProducts():List<Product>

    suspend fun getBanner():List<Banner>

    suspend fun getCategory():List<Category>
}