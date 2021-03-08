package com.test.kecipirtest.ui.home

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.test.kecipirtest.data.model.banner.Banner
import com.test.kecipirtest.data.model.category.Category
import com.test.kecipirtest.data.model.product.Product
import com.test.kecipirtest.network.remote.Repository
import com.test.kecipirtest.network.remote.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.lang.Exception

class HomeViewModel(
    private val repository: Repository
) : ViewModel() {

    private val job = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + job)

    val bannners = MutableLiveData<Resource<List<Banner>>>()
    val categorys = MutableLiveData<Resource<List<Category>>>()
    val products = MutableLiveData<Resource<List<Product>>>()

    fun getBanner() {
        products.value = Resource.loading()
        uiScope.launch {
            try {
                val datas = repository.getBanner()
                bannners.value = Resource.success(datas)
            } catch (e: Exception) {
                products.value = Resource.error(e.message)
                Log.d("Story Exception", e.message.toString())
            }
        }
    }

    fun getCategory() {
        products.value = Resource.loading()
        uiScope.launch {
            try {
                val datas = repository.getCategory()
                categorys.value = Resource.success(datas)
            } catch (e: Exception) {
                categorys.value = Resource.error(e.message)
                Log.d("Story Exception", e.message.toString())
            }
        }
    }

    fun getProducts() {
        products.value = Resource.loading()
        uiScope.launch {
            try {
                val datas = repository.getProducts()
                products.value = Resource.success(datas)
            } catch (e: Exception) {
                products.value = Resource.error(e.message)
                Log.d("Story Exception", e.message.toString())
            }
        }
    }
}