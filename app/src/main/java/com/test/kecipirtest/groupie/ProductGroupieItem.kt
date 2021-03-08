package com.test.kecipirtest.groupie

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import com.kennyc.view.MultiStateView
import com.test.kecipirtest.R
import com.test.kecipirtest.data.model.product.Product
import com.test.kecipirtest.ui.adapter.ProductAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.product_layout.*

class ProductGroupieItem (
    private val context: Context,
    private var datas: List<Product>,
    private val onProductClicked: ((product: Product) -> Unit)? = null
) : Item() {

    var viewState = MultiStateView.ViewState.LOADING

    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        with(viewHolder) {
            msvProduct.viewState = viewState
            val productAdapter = ProductAdapter(context, datas, onProductClicked, ProductAdapter.FEW_PRODUCT)
            rvProduct.apply {
                val layoutManagers: LinearLayoutManager? =
                    LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                layoutManager = layoutManagers
                adapter = productAdapter
            }
        }
    }


    override fun getLayout(): Int = R.layout.product_layout

    fun add(datas: List<Product>) {
        this.datas = datas
    }
}