package com.test.kecipirtest.network.remote

import com.test.kecipirtest.data.model.banner.Banner
import com.test.kecipirtest.data.model.category.Category
import com.test.kecipirtest.data.model.product.Product


class DataSource(val api: Api) : Repository {

    override suspend fun getProducts(): List<Product> {
        return api.getProducts().data
    }

    override suspend fun getBanner(): List<Banner> {
        return api.getBanner().dataBanner
    }

    override suspend fun getCategory(): List<Category> {
        return api.getCategory().dataCategory
    }

}