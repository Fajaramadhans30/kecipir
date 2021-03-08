package com.test.kecipirtest.groupie

import android.content.Context
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.kennyc.view.MultiStateView
import com.test.kecipirtest.R
import com.test.kecipirtest.data.model.category.Category
import com.test.kecipirtest.ui.adapter.CategoryAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.category_layout.*

class CategoryGroupItem (
    private val context: Context,
    private var datas: List<Category>,
    private val onCategoryClicked: ((category: Category) -> Unit)? = null
) : Item() {

    var viewState = MultiStateView.ViewState.LOADING

    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        with(viewHolder) {
            msvCategory.viewState = viewState
            val categoryAdapter = CategoryAdapter(context, datas, onCategoryClicked)

            rvCategory.apply {
                val gridLayoutManager = GridLayoutManager(context, 2)
                gridLayoutManager.orientation =
                    LinearLayoutManager.HORIZONTAL
                layoutManager = gridLayoutManager
                adapter = categoryAdapter
            }
        }
    }


    override fun getLayout(): Int = R.layout.category_layout

    fun add(datas: List<Category>) {
        this.datas = datas
    }
}